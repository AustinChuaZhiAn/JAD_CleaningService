<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.*, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Clean And Clear | Services</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Footer.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/ServiceCategories.css">
</head>
<body>
    <%@ include file="Header.jsp" %>

    <div class="categories-section">
        <div class="container">
            <div class="section-header">
                <h1 class="section-title">Our Services</h1>
                <p class="section-subtitle">Choose from our wide range of professional cleaning services</p>
            </div>

            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                <% 
                List<Category> categories = (List<Category>) request.getAttribute("Categories");
                if (categories != null && !categories.isEmpty()) {
                    for (Category category : categories) { 
                %>
                    <div class="col">
                        <a href="<%=request.getContextPath()%>/ServiceTypeController?categoryName=<%= category.getCategory_name()%>&category_id=<%= category.getCategory_id() %>" 
                           class="card-link">
                            <div class="card category-card">
                                <img src="<%= category.getImg_url() %>" 
                                     alt="<%= category.getCategory_name() %>" 
                                     class="category-image">
                                <div class="card-body">
                                    <h3 class="card-title">
                                        <%= category.getCategory_name() %>
                                    </h3>
                                    <p class="card-text">
                                        <%= category.getDescription() %>
                                    </p>
                                    <span class="btn btn-outline-primary">Learn More</span>
                                </div>
                            </div>
                        </a>
                    </div>
                <% 
                    }
                } else { 
                %>
                    <div class="col-12 text-center">
                        <div class="py-5">
                            <i class="fas fa-broom fa-3x text-muted mb-3"></i>
                            <h3 class="text-muted">No Services Available</h3>
                            <p class="text-muted">Please check back later for our cleaning services.</p>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <%@ include file="Footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>