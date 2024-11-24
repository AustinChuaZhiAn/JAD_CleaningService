<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Service" %>
<%@ page import="Model.Category" %>
<%@ page import="Model.ServiceType" %>
<%@ page import="Model.Frequency" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Service Form - Clean and Clear</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .btn-action {
            min-width: 100px;
        }
        .form-label {
            font-weight: 500;
        }
        .price-input {
            position: relative;
        }
        .price-input:before {
            content: "$";
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            z-index: 5;
        }
        .price-input input {
            padding-left: 25px;
        }
    </style>
</head>
<body class="bg-light">
    <%@ include file="AdminHeader.jsp" %>
    
    <%
        Service service = (Service) request.getAttribute("service");
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        List<ServiceType> serviceTypes = (List<ServiceType>) request.getAttribute("serviceTypes");
        List<Frequency> frequencies = (List<Frequency>) request.getAttribute("frequencies");
        boolean isEdit = service != null && service.getService_id() != 0;
        String formAction = isEdit ? "edit" : "create";
        String formTitle = isEdit ? "Edit Service" : "Create New Service";
        String buttonText = isEdit ? "Update Service" : "Create Service";
        String buttonIcon = isEdit ? "fa-save" : "fa-plus";
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
                            <a href="${pageContext.request.contextPath}/ServiceController" 
                               class="btn btn-sm btn-light">
                                <i class="fas fa-arrow-left me-1"></i>Back to List
                            </a>
                        </div>
                    </div>
                    
                    <div class="card-body">
                        <!-- Error Message -->
                        <% if (request.getParameter("error") != null) { %>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <%= request.getParameter("error") %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        <% } %>
                        
                        <form action="${pageContext.request.contextPath}/ServiceController" method="POST">
                            <input type="hidden" name="action" value="<%= formAction %>">
                            <% if (isEdit) { %>
                                <input type="hidden" name="service_id" value="<%= service.getService_id() %>">
                            <% } %>
                            
                            <!-- Category Selection -->
                            <div class="mb-3">
                                <label for="category_id" class="form-label">Category</label>
                                <select class="form-select" id="category_id" name="category_id" required>
                                    <option value="">Select a category</option>
                                    <% for (Category category : categories) { %>
                                        <option value="<%= category.getCategory_id() %>" 
                                                <%= isEdit && service.getCategory_id() == category.getCategory_id() ? "selected" : "" %>>
                                            <%= category.getCategory_name() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <!-- Service Type Selection -->
                            <div class="mb-3">
                                <label for="service_type_id" class="form-label">Service Type</label>
                                <select class="form-select" id="service_type_id" name="service_type_id" required>
                                    <option value="">Select a service type</option>
                                    <% for (ServiceType type : serviceTypes) { %>
                                        <option value="<%= type.getService_type_id() %>"
                                                <%= isEdit && service.getService_type_id() == type.getService_type_id() ? "selected" : "" %>>
                                            <%= type.getService_type() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <!-- Frequency Selection -->
                            <div class="mb-3">
                                <label for="frequency_id" class="form-label">Frequency</label>
                                <select class="form-select" id="frequency_id" name="frequency_id" required>
                                    <option value="">Select frequency</option>
                                    <% for (Frequency freq : frequencies) { %>
                                        <option value="<%= freq.getFrequencyId() %>"
                                                <%= isEdit && service.getFrequency_id() == freq.getFrequencyId() ? "selected" : "" %>>
                                            <%= freq.getFrequency() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <!-- Price Input -->
                            <div class="mb-3">
                                <label for="price" class="form-label">Price</label>
                                <div class="price-input">
                                    <input type="number" class="form-control" id="price" 
                                           name="price" step="0.01" min="0" required
                                           value="<%= isEdit ? service.getPrice() : "" %>"
                                           placeholder="Enter price">
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-end gap-2">
                                <a href="${pageContext.request.contextPath}/ServiceController" 
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
</body>
</html>