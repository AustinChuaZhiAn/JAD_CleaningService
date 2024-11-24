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
        .card {
            transition: transform 0.3s ease;
            border: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .category-image {
            height: 200px;
            object-fit: cover;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }
        .btn-action {
            transition: all 0.3s ease;
        }
        .btn-action:hover {
            transform: translateY(-2px);
        }
        .back-to-dashboard {
            text-decoration: none;
            color: #6c757d;
            transition: color 0.3s ease;
        }
        .back-to-dashboard:hover {
            color: #007bff;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/AdminController">
                <i class="fas fa-spray-can me-2"></i>Clean and Clear
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/CategoryController">
                            <i class="fas fa-th-large me-1"></i>Categories
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/ServiceController">
                            <i class="fas fa-cogs me-1"></i>Services
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/UserController">
                            <i class="fas fa-users me-1"></i>Users
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

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