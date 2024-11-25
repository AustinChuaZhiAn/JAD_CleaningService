<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Categories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Your existing CSS styles */
    </style>
</head>
<body class="bg-light">
    <%@ include file="AdminHeader.jsp" %>

    <div class="container my-5">
        <!-- Header Section -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="mb-0"><i class="fas fa-th-large me-2"></i>Categories</h2>
                <p class="text-muted">Manage your service categories</p>
            </div>
            <a href="${pageContext.request.contextPath}/CategoryController?action=create" 
               class="btn btn-primary btn-action">
                <i class="fas fa-plus me-2"></i>Add New Category
            </a>
        </div>

        <!-- Error Messages -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i>
            <%= request.getAttribute("error") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- Success Messages -->
        <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>
            <%= request.getAttribute("success") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- Categories Grid -->
        <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (categories != null && !categories.isEmpty()) {
        %>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <% for (Category category : categories) { %>
            <div class="col">
                <div class="card h-100">
                    <% if (category.getImg_url() != null && !category.getImg_url().isEmpty()) { %>
                        <img src="<%= category.getImg_url() %>" class="card-img-top category-image" alt="<%= category.getCategory_name() %>">
                    <% } %>
                    <div class="card-body">
                        <h5 class="card-title"><%= category.getCategory_name() %></h5>
                        <p class="card-text text-muted"><%= category.getDescription() %></p>
                        <div class="d-flex justify-content-end mt-3">
                            <a href="${pageContext.request.contextPath}/CategoryController?action=edit&id=<%= category.getCategory_id() %>" 
                               class="btn btn-outline-primary btn-action me-2">
                                <i class="fas fa-edit me-1"></i>Edit
                            </a>
                            <button type="button" class="btn btn-outline-danger btn-action" 
                                    onclick="confirmDelete(<%= category.getCategory_id() %>)">
                                <i class="fas fa-trash-alt me-1"></i>Delete
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <div class="text-center py-5">
            <i class="fas fa-folder-open fa-3x text-muted mb-3"></i>
            <p class="lead text-muted">No categories found</p>
            <p class="text-muted">Start by adding a new category</p>
        </div>
        <% } %>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Confirm Delete</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete this category?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <a href="#" id="deleteLink" class="btn btn-danger">Delete</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmDelete(categoryId) {
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            document.getElementById('deleteLink').href = 
                '${pageContext.request.contextPath}/CategoryController?action=delete&id=' + categoryId;
            modal.show();
        }
    </script>
</body>
</html>