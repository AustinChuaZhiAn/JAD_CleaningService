<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.UserAccount" %>
<%@ page import="Model.UserDetails" %>
	<%
    if (session.getAttribute("role_id") == null || !session.getAttribute("role_id").equals(1)) {
        response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
        return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Form - Clean and Clear</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .btn-action {
            min-width: 100px;
        }
        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body class="bg-light">
    <%@ include file="AdminHeader.jsp" %>
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="card-title mb-0">
                                <i class="fas fa-plus me-2"></i>Create New User/Cleaner
                            </h5>
                            <a href="${pageContext.request.contextPath}/AdminController?action=list" class="btn btn-sm btn-light">
                                <i class="fas fa-arrow-left me-1"></i>Back to Users
                            </a>
                        </div>
                    </div>
                    
                    <div class="card-body">
                        <!-- Error Message -->
                        <% if (session.getAttribute("error") != null) { %>
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <%= session.getAttribute("error") %>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                            <% session.removeAttribute("error"); %>
                        <% } %>
                        
                        <form action="${pageContext.request.contextPath}/AdminController" method="POST">
                            <input type="hidden" name="action" value="create">
                            
                            <!-- Role Selection -->
                            <div class="mb-3">
                                <label for="role_id" class="form-label">Type</label>
                                <select class="form-select" id="role_id" name="role_id" required>
                                    <option value="">Select type</option>
                                    <option value="1">Admin</option>
                                    <option value="2" selected>Member</option>
                                    <option value="3">Cleaner</option>
                                </select>
                            </div>

                            <!-- User Fields -->
                            <div id="userFields">
                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" 
                                           name="username" required
                                           placeholder="Enter username">
                                </div>
                                
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="password" 
                                           name="password" required
                                           placeholder="Enter password">
                                </div>
                                
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" 
                                           name="email" required
                                           placeholder="Enter email">
                                </div>
                                
                                <div class="mb-3">
                                    <label for="phone_number" class="form-label">Phone Number</label>
                                    <input type="text" class="form-control" id="phone_number" 
                                           name="phone_number"
                                           pattern="[0-9]+"
                                           placeholder="Enter phone number">
                                </div>
                            </div>

                            <!-- Cleaner Fields -->
                            <div id="cleanerFields" style="display: none;">
                                <div class="mb-3">
                                    <label for="cleaner_name" class="form-label">Cleaner Name</label>
                                    <input type="text" class="form-control" id="cleaner_name" 
                                           name="cleaner_name"
                                           placeholder="Enter cleaner name">
                                </div>
                                
                                <div class="mb-3">
                                    <label for="contact" class="form-label">Contact Number</label>
                                    <input type="text" class="form-control" id="contact" 
                                           name="contact"
                                           pattern="[0-9]+"
                                           placeholder="Enter contact number">
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-end gap-2">
                                <a href="${pageContext.request.contextPath}/AdminController?action=list" class="btn btn-secondary btn-action">
                                    <i class="fas fa-times me-1"></i>Cancel
                                </a>
                                <button type="submit" class="btn btn-primary btn-action">
                                    <i class="fas fa-plus me-1"></i>Create
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('role_id').addEventListener('change', function() {
            const cleanerFields = document.getElementById('cleanerFields');
            const userFields = document.getElementById('userFields');
            
            if (this.value === '3') { // Cleaner
                cleanerFields.style.display = 'block';
                userFields.style.display = 'none';
                // Make cleaner fields required
                document.getElementById('cleaner_name').required = true;
                document.getElementById('contact').required = true;
                // Make user fields not required
                document.getElementById('username').required = false;
                document.getElementById('password').required = false;
                document.getElementById('email').required = false;
            } else {
                cleanerFields.style.display = 'none';
                userFields.style.display = 'block';
                // Make user fields required
                document.getElementById('username').required = true;
                document.getElementById('password').required = true;
                document.getElementById('email').required = true;
                // Make cleaner fields not required
                document.getElementById('cleaner_name').required = false;
                document.getElementById('contact').required = false;
            }
        });
    </script>
</body>
</html>