package utils;

import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.Properties;
import jakarta.servlet.http.Part;

public class ImgurUtil {
    private static final Properties props = DatabaseConnection.getProperties();
    private static final String IMGUR_ACCESS_TOKEN = props.getProperty("imgur.access.token");
    private static final String IMGUR_API_URL = "https://api.imgur.com/3/image";
    
    private static final int MAX_RETRIES = 3;
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final long RETRY_DELAY_MS = 5000; // 5 seconds

    public static class ImgurResponse {
        private final String url;
        private final String deleteHash;

        public ImgurResponse(String url, String deleteHash) {
            this.url = url;
            this.deleteHash = deleteHash;
        }

        public String getUrl() { return url; }
        public String getDeleteHash() { return deleteHash; }
    }

    public static ImgurResponse uploadImage(Part filePart) throws IOException {
        validateImageUpload(filePart);
        
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                return tryUploadImage(filePart);
            } catch (IOException e) {
                if (isRateLimitError(e) && retries < MAX_RETRIES - 1) {
                    retries++;
                    System.out.println("Rate limit hit, attempt " + retries + " of " + MAX_RETRIES);
                    try {
                        Thread.sleep(RETRY_DELAY_MS * retries);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Upload interrupted", ie);
                    }
                } else {
                    throw e;
                }
            }
        }
        throw new IOException("Failed to upload after " + MAX_RETRIES + " attempts");
    }

    private static void validateImageUpload(Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            throw new IOException("No file provided");
        }
        if (filePart.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds 10MB limit");
        }
        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("File must be an image");
        }
    }

    private static ImgurResponse tryUploadImage(Part filePart) throws IOException {
        byte[] fileBytes = readFileBytes(filePart);
        String base64Image = Base64.getEncoder().encodeToString(fileBytes);
        
        HttpURLConnection conn = null;
        try {
            conn = createImgurConnection("POST");
            
            // Send request
            String postData = String.format("image=%s", URLEncoder.encode(base64Image, "UTF-8"));
            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes("UTF-8"));
                os.flush();
            }

            // Get response
            String response = getResponseBody(conn);
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new IOException("Upload failed. Response code: " + responseCode + ", Response: " + response);
            }

            return parseImgurResponse(response);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void deleteImage(String deleteHash) throws IOException {
        if (deleteHash == null || deleteHash.isEmpty()) {
            return;
        }

        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                tryDeleteImage(deleteHash);
                return;
            } catch (IOException e) {
                if (isRateLimitError(e) && retries < MAX_RETRIES - 1) {
                    retries++;
                    try {
                        Thread.sleep(RETRY_DELAY_MS * retries);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Delete interrupted", ie);
                    }
                } else {
                    throw e;
                }
            }
        }
        throw new IOException("Failed to delete after " + MAX_RETRIES + " attempts");
    }

    private static void tryDeleteImage(String deleteHash) throws IOException {
        HttpURLConnection conn = null;
        try {
            conn = createImgurConnection("DELETE", deleteHash);
            
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                String errorResponse = getResponseBody(conn);
                throw new IOException("Failed to delete image from Imgur. Response code: " + 
                                    responseCode + ", Error: " + errorResponse);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static HttpURLConnection createImgurConnection(String method) throws IOException {
        return createImgurConnection(method, null);
    }

    private static HttpURLConnection createImgurConnection(String method, String deleteHash) throws IOException {
        String urlString = deleteHash == null ? IMGUR_API_URL : IMGUR_API_URL + "/" + deleteHash;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        
        // Set auth header using Bearer token for all requests
        conn.setRequestProperty("Authorization", "Bearer " + IMGUR_ACCESS_TOKEN);

        if (method.equals("POST")) {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }

        return conn;
    }

    private static byte[] readFileBytes(Part filePart) throws IOException {
        try (InputStream input = filePart.getInputStream();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray();
        }
    }

    private static String getResponseBody(HttpURLConnection conn) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getResponseCode() == 200 ? 
                    conn.getInputStream() : conn.getErrorStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static ImgurResponse parseImgurResponse(String responseStr) throws IOException {
        try {
            // Debug print to see what we're getting
            System.out.println("Raw response: " + responseStr);

            // Find data object first
            int dataStart = responseStr.indexOf("\"data\":{") + 7;
            int dataEnd = responseStr.lastIndexOf("}");
            String dataStr = responseStr.substring(dataStart, dataEnd);
            
            // Debug print the data section
            System.out.println("Data section: " + dataStr);
            
            // Get deletehash from data object - look for exact pattern
            String deleteHashKey = "\"deletehash\":\"";
            int deleteHashStart = dataStr.indexOf(deleteHashKey) + deleteHashKey.length();
            int deleteHashEnd = dataStr.indexOf("\"", deleteHashStart);
            String deleteHash = dataStr.substring(deleteHashStart, deleteHashEnd);
            
            // Get link from data object - look for exact pattern
            String linkKey = "\"link\":\"";
            int linkStart = dataStr.indexOf(linkKey) + linkKey.length();
            int linkEnd = dataStr.indexOf("\"", linkStart);
            String imgurLink = dataStr.substring(linkStart, linkEnd).replace("\\/", "/");
            
            // Debug print the extracted values
            System.out.println("Extracted URL: " + imgurLink);
            System.out.println("Extracted DeleteHash: " + deleteHash);

            return new ImgurResponse(imgurLink, deleteHash);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Failed to parse response: " + responseStr);
            e.printStackTrace();
            throw new IOException("Failed to parse Imgur response: " + responseStr);
        }
    }

    private static boolean isRateLimitError(IOException e) {
        return e.getMessage() != null && 
               (e.getMessage().contains("429") || 
                e.getMessage().contains("Too Many Requests"));
    }
}