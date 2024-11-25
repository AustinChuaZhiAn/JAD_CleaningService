package Model;

public class Image {
    private int img_id;
    private String img_url;
    private String deletehash;

    public Image() {}

    public Image(int img_id, String img_url, String deletehash) {
        this.img_id = img_id;
        this.img_url = img_url;
        this.deletehash = deletehash;
    }

    // Getters and Setters
    public int getImg_id() { return img_id; }
    public void setImg_id(int img_id) { this.img_id = img_id; }

    public String getImg_url() { return img_url; }
    public void setImg_url(String img_url) { this.img_url = img_url; }

    public String getDeletehash() { return deletehash; }
    public void setDeletehash(String deletehash) { this.deletehash = deletehash; }
}