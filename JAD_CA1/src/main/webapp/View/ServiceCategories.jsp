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
            <a href="<%=request.getContextPath()%>/Controller/ServiceTypeController.java?categoryName=<%= category.getCategoryName()%>&category_id=<%= category.getCategoryId() %>" class="category-card-link">
                <div class="category-card">
                    <h3 class="category-title"><%= category.getCategoryName() %></h3>
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
