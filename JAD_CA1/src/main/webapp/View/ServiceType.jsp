<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.*, java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clean And Clear | Categories</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/Footer.css">
    <style>
        .category-card {
            transition: transform 0.2s, box-shadow 0.2s;
            min-height: 200px; /* Make cards taller */
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .category-card:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .main-content {
            min-height: calc(100vh - 200px); /* Adjust the value based on your header and footer height */
        }
    </style>
</head>
<body>
    <%@ include file="Header.jsp" %>

    <div class="main-content">
        <div class="container py-5 my-5">
            <div class="row g-5 justify-content-center">
                <%
                List<ServiceType> serviceTypeList = (List<ServiceType>) request.getAttribute("serviceTypeList");
                if (serviceTypeList != null && !serviceTypeList.isEmpty()) {
                    for (ServiceType serviceType: serviceTypeList) {
                %>
                    <div class="col-12 col-sm-6 col-lg-5">
                        <a href="<%=request.getContextPath()%>/FrequencyController?serviceType=<%= serviceType.getService_type_id() %>" 
                           class="text-decoration-none">
                            <div class="category-card card h-100 text-center p-4">
                                <div class="card-body">
                                    <h3 class="card-title text-dark mb-0"><%= serviceType.getService_type() %></h3>
                                </div>
                            </div>
                        </a>
                    </div>
                <%
                    }
                } else {
                %>
                    <div class="col-12 text-center">
                        <p class="text-muted">No Service Type available.</p>
                    </div>
                <%
                }
                %>
            </div>
        </div>
    </div>

    <%@ include file="Footer.jsp" %>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>