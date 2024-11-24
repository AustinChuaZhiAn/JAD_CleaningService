<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Service" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Services</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .card {
            transition: transform 0.3s ease;
            border: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .service-card:hover {
            transform: translateY(-5px);
        }
        .price-tag {
            background-color: #e9ecef;
            padding: 0.25rem 0.5rem;
            border-radius: 0.25rem;
            font-weight: bold;
        }
        .frequency-badge {
            font-size: 0.8rem;
            padding: 0.25rem 0.5rem;
        }
        .btn-action {
            transition: all 0.3s ease;
        }
        .btn-action:hover {
            transform: translateY(-2px);
        }
    </style>
</head>
<body class="bg-light">
    <%@ include file="AdminHeader.jsp" %>
    
    <div class="container my-5">
        <!-- Header Section -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="mb-0"><i class="fas fa-cogs me-2"></i>Services</h2>
                <p class="text-muted">Manage your cleaning services</p>
            </div>
            <a href="${pageContext.request.contextPath}/ServiceController?action=create" 
               class="btn btn-primary btn-action">
                <i class="fas fa-plus me-2"></i>Add New Service
            </a>
        </div>

        <!-- Error Messages -->
        <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i>
            <%= request.getParameter("error") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- Success Messages -->
        <% if (request.getParameter("success") != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>
            <%= request.getParameter("success") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- Services Grid -->
        <%
        ArrayList<Service> services = (ArrayList<Service>) request.getAttribute("services");
        if (services != null && !services.isEmpty()) {
        %>
        <div class="row g-4">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th>Category</th>
                                        <th>Service Type</th>
                                        <th>Frequency</th>
                                        <th class="text-end">Price</th>
                                        <th class="text-center">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Service service : services) { %>
                                    <tr>
                                        <td>
                                            <span class="fw-medium"><%= service.getCategory_name() %></span>
                                        </td>
                                        <td><%= service.getService_type() %></td>
                                        <td>
                                            <span class="badge bg-light text-dark">
                                                <%= service.getFrequency() %>
                                            </span>
                                        </td>
                                        <td class="text-end">
                                            <span class="price-tag">$<%= service.getPrice() %></span>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/ServiceController?action=edit&id=<%= service.getService_id() %>" 
                                               class="btn btn-sm btn-outline-primary me-2">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <button type="button" 
                                                    class="btn btn-sm btn-outline-danger"
                                                    onclick="confirmDelete(<%= service.getService_id() %>)">
                                                <i class="fas fa-trash-alt"></i>
                                            </button>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } else { %>
        <div class="text-center py-5">
            <i class="fas fa-cogs fa-3x text-muted mb-3"></i>
            <p class="lead text-muted">No services found</p>
            <p class="text-muted">Start by adding a new service</p>
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
                    Are you sure you want to delete this service?
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
        function confirmDelete(serviceId) {
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            document.getElementById('deleteLink').href = 
                '${pageContext.request.contextPath}/ServiceController?action=delete&id=' + serviceId;
            modal.show();
        }
    </script>
</body>
</html>