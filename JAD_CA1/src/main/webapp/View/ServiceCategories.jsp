<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.*, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Clean And Clear | Categories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Footer.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/ServiceCategories.css">
    
</head>
<body>
    <%@ include file="Header.jsp" %>

   <%
   List<Category> categories = (List<Category>) request.getAttribute("Categories");
   %>

   <div class="categories-section">
       <div class="container">
           <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-5">
               <% if (categories != null && !categories.isEmpty()) {
                   for (Category category : categories) { %>
                       <div class="col">
                           <a href="<%=request.getContextPath()%>/ServiceTypeController?categoryName=<%= category.getCategory_name()%>&category_id=<%= category.getCategory_id() %>" 
                              class="card-link">
                               <div class="card category-card h-100">
                                   <div class="card-body">
                                       <img src="<%= category.getImg_url() %>" 
                                            alt="<%= category.getCategory_name() %> image" 
                                            class="category-image mb-4">
                                       <h3 class="card-title"><%= category.getCategory_name() %></h3>
                                       <p class="card-text text-body"><%= category.getDescription() %></p>
                                   </div>
                               </div>
                           </a>
                       </div>
                   <% }
               } else { %>
                   <div class="col-12">
                       <p class="text-center">No categories available.</p>
                   </div>
               <% } %>
           </div>
       </div>
   </div>

   <%@ include file="Footer.jsp" %>
   
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>