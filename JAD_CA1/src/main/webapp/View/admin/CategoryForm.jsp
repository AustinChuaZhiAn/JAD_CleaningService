<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Category" %>
	<%
    if (session.getAttribute("role_id") == null || !session.getAttribute("role_id").equals(1)) {
        response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
        return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category Form - Clean and Clear</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .preview-image {
            max-width: 100%;
            height: 200px;
            object-fit: contain;
            margin-top: 10px;
            border-radius: 5px;
        }
        .image-container {
            border: 2px dashed #ddd;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            margin-bottom: 10px;
        }
        .btn-action {
            min-width: 100px;
        }
    </style>
</head>
<body class="bg-light">
    <%
        Category category = (Category) request.getAttribute("category");
        boolean isEdit = category != null && category.getCategory_id() != 0;
        String formAction = isEdit ? "edit" : "create";
        String formTitle = isEdit ? "Edit Category" : "Create New Category";
        String buttonText = isEdit ? "Update Category" : "Create Category";
        String buttonIcon = isEdit ? "fa-save" : "fa-plus";
        String currentUrl = isEdit ? category.getImg_url() : "";
        
        // Get error and success messages from URL parameters
        String error = request.getParameter("error");
        String uploadedUrl = request.getParameter("uploadedUrl");
    %>
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="card-title mb-0">
                                <i class="fas <%= isEdit ? "fa-edit" : "fa-plus" %> me-2"></i><%= formTitle %>
                            </h5>
                            <a href="${pageContext.request.contextPath}/CategoryController" 
                               class="btn btn-sm btn-light">
                                <i class="fas fa-arrow-left me-1"></i>Back to List
                            </a>
                        </div>
                    </div>
                    
                    <div class="card-body">
                        <!-- Success Message -->
                        <% if (uploadedUrl != null) { %>
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <i class="fas fa-check-circle me-2"></i>
                                Upload Successful!
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        <% } %>
                        
                        <!-- Error Message -->
                        <% if (error != null) { %>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <%= error %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        <% } %>
                        
                        <form action="${pageContext.request.contextPath}/CategoryController" 
                              method="POST" enctype="multipart/form-data">
                              
                            <input type="hidden" name="action" value="<%= formAction %>">
                            <% if (isEdit) { %>
                                <input type="hidden" name="category_id" value="<%= category.getCategory_id() %>">
                            <% } %>
                            
                            <div class="mb-3">
                                <label for="category_name" class="form-label">Category Name</label>
                                <input type="text" class="form-control" id="category_name" 
                                       name="category_name" required
                                       value="<%= isEdit ? category.getCategory_name() : "" %>"
                                       placeholder="Enter category name">
                            </div>
                            
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" 
                                          name="description" rows="3" required
                                          placeholder="Enter category description"><%= isEdit ? category.getDescription() : "" %></textarea>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Category Image</label>
                                <div class="image-container">
                                    <img id="preview" class="preview-image" 
                                         src="<%= currentUrl %>" 
                                         alt="Preview"
                                         style="display: <%= currentUrl.isEmpty() ? "none" : "block" %>;">
                                    <div id="upload-text" 
                                         style="display: <%= currentUrl.isEmpty() ? "block" : "none" %>;">
                                        <i class="fas fa-cloud-upload-alt fa-2x text-muted mb-2"></i>
                                        <p class="text-muted mb-0">Click to upload or drag and drop</p>
                                    </div>
                                </div>
                                <input type="file" class="form-control" id="image" 
                                       name="image" accept="image/*" <%= isEdit ? "" : "required" %>
                                       onchange="previewImage(this);">
                            </div>
                            
                            <div class="d-flex justify-content-end gap-2">
                                <a href="${pageContext.request.contextPath}/CategoryController" 
                                   class="btn btn-secondary btn-action">
                                    <i class="fas fa-times me-1"></i>Cancel
                                </a>
                                <button type="submit" class="btn btn-primary btn-action">
                                    <i class="fas <%= buttonIcon %> me-1"></i><%= buttonText %>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function previewImage(input) {
            const preview = document.getElementById('preview');
            const uploadText = document.getElementById('upload-text');
            
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                    uploadText.style.display = 'none';
                }
                
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</body>
</html>