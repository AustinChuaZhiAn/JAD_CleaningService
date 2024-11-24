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
        List<ServiceType> serviceTypeList = (List<ServiceType>) request.getAttribute("serviceTypeList");
    %>

    <div class="category-container">
        <% 
            // Check if categories are available
            if (serviceTypeList != null && !serviceTypeList.isEmpty()) {
                // Loop through categories and display each
                for (ServiceType serviceType: serviceTypeList) {
        %>
            <a href="<%=request.getContextPath()%>/Controller/FrequencyController?serviceType=<%= serviceType.getService_type_id() %>" class="category-card-link">
                <div class="category-card">
                    <h3 class="category-title"><%= serviceType.getService_type() %></h3>
                </div>
            </a>
        <% 
                } // End of the for loop
            } else {
        %>
            <p>No Service Type available.</p>
        <% 
            }
        %>
    </div>

    <%@ include file="Footer.jsp" %>
</body>
</html>