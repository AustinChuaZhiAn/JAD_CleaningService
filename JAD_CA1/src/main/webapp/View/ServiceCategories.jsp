<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.*, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clean And Clear | Categories</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Header.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Footer.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/ServiceCategories.css">
</head>
<body>
    <%@ include file="Header.jsp" %>

    <%
    // Fetch the categories from the request attribute
            List<Service> categories = (List<Service>) request.getAttribute("categories");
    %>

    <div class="category-container">
        <%
        // Check if categories are available
                    if (categories != null && !categories.isEmpty()) {
                        // Loop through categories and display each
                        for (Service category : categories) {
        %>
            <a href="<%=request.getContextPath()%>/Controller/ServiceTypeController.java?categoryName=<%= category.getCategory_name()%>&category_id=<%= category.getCategory_id() %>" class="category-card-link">
                <div class="category-card">
                	<img src="<%= category.getCategory_img_url() %>" alt="<%= category.getCategory_name() %> image" class="category-image">
                    <h3 class="category-title"><%= category.getCategory_name() %></h3>
                    <p class="category-description"><%= category.getDescription() %></p>
                </div>
            </a>
        <% 
                } // End of the for loop
            } else {
        %>
            <p>No categories available.</p>
        <% 
            }
        %>
    </div>

    <%@ include file="Footer.jsp" %>
</body>
</html>
