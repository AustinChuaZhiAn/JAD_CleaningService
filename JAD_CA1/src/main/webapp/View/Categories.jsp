<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Category Details</h2>
        <%
        Category category = (Category) request.getAttribute("category");
        if (category != null) {
        %>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title"><%= category.getCategory_name() %></h5>
                <p class="card-text"><%= category.getDescription() %></p>
                <p class="card-text"><small class="text-muted">ID: <%= category.getCategory_id() %></small></p>
            </div>
        </div>
        <% } else { %>
        <p>Category not found.</p>
        <% } %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>