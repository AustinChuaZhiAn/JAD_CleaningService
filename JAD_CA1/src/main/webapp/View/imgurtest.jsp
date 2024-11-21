<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Imgur Uploader</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .upload-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .drop-zone {
            border: 2px dashed #ccc;
            border-radius: 4px;
            padding: 20px;
            text-align: center;
            margin: 20px 0;
            cursor: pointer;
        }
        .drop-zone:hover {
            border-color: #666;
        }
        .drop-zone.dragover {
            background-color: #e1f5fe;
            border-color: #2196f3;
        }
        #preview {
            margin-top: 20px;
            display: none;
        }
        #preview img {
            max-width: 100%;
            margin: 10px 0;
            border-radius: 4px;
        }
        .link-container {
            margin: 10px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
        .link-container input {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .loading {
            display: none;
            margin: 20px 0;
            text-align: center;
            color: #666;
        }
        .button {
            background-color: #2196f3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .button:hover {
            background-color: #1976d2;
        }
        .error {
            color: #f44336;
            margin: 10px 0;
            display: none;
        }
    </style>
</head>
<body>
    <div class="upload-container">
        <h1>Imgur Image Uploader</h1>
        
        <div class="drop-zone" id="dropZone">
            <p>Drag and drop an image here or click to select</p>
            <input type="file" id="fileInput" accept="image/*" style="display: none">
        </div>

        <div class="loading" id="loading">
            Uploading image... Please wait...
        </div>

        <div class="error" id="error">
            Error uploading image. Please try again.
        </div>

        <div id="preview">
            <h2>Uploaded Image</h2>
            <img id="imagePreview" src="" alt="Uploaded image">
            <div class="link-container">
                <p>Direct Image Link:</p>
                <input type="text" id="directLink" readonly>
                <p>Imgur Post Link:</p>
                <input type="text" id="postLink" readonly>
            </div>
        </div>
    </div>

    <script>
        // Your Imgur Client ID
        const IMGUR_CLIENT_ID = 'YOUR_CLIENT_ID';

        // Elements
        const dropZone = document.getElementById('dropZone');
        const fileInput = document.getElementById('fileInput');
        const preview = document.getElementById('preview');
        const imagePreview = document.getElementById('imagePreview');
        const directLink = document.getElementById('directLink');
        const postLink = document.getElementById('postLink');
        const loading = document.getElementById('loading');
        const error = document.getElementById('error');

        // Event Listeners
        dropZone.addEventListener('click', () => fileInput.click());
        dropZone.addEventListener('dragover', handleDragOver);
        dropZone.addEventListener('dragleave', handleDragLeave);
        dropZone.addEventListener('drop', handleDrop);
        fileInput.addEventListener('change', handleFileSelect);

        function handleDragOver(e) {
            e.preventDefault();
            dropZone.classList.add('dragover');
        }

        function handleDragLeave(e) {
            e.preventDefault();
            dropZone.classList.remove('dragover');
        }

        function handleDrop(e) {
            e.preventDefault();
            dropZone.classList.remove('dragover');
            const file = e.dataTransfer.files[0];
            if (file && file.type.startsWith('image/')) {
                uploadImage(file);
            }
        }

        function handleFileSelect(e) {
            const file = e.target.files[0];
            if (file) {
                uploadImage(file);
            }
        }

        async function uploadImage(file) {
            // Show loading state
            loading.style.display = 'block';
            error.style.display = 'none';
            preview.style.display = 'none';

            // Create FormData
            const formData = new FormData();
            formData.append('image', file);

            try {
                // Upload to Imgur
                const response = await fetch('https://api.imgur.com/3/image', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Client-ID ${IMGUR_CLIENT_ID}`
                    },
                    body: formData
                });

                const data = await response.json();

                if (data.success) {
                    // Update preview
                    imagePreview.src = data.data.link;
                    directLink.value = data.data.link;
                    postLink.value = `https://imgur.com/${data.data.id}`;
                    preview.style.display = 'block';
                } else {
                    throw new Error('Upload failed');
                }
            } catch (err) {
                error.style.display = 'block';
                error.textContent = 'Error uploading image: ' + err.message;
            } finally {
                loading.style.display = 'none';
            }
        }
    </script>
</body>
</html>