<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.UserAccount" %>
<%@ page import="Model.UserDetails" %>
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
    
    <%
        String userId = request.getParameter("id");
        boolean isEdit = userId != null && !userId.isEmpty();
        String formTitle = isEdit ? "Edit User" : "Create New User";
        String buttonText = isEdit ? "Update User" : "Create User";
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
                            <a href="Users.jsp" class="btn btn-sm btn-light">
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
                        
                        <form action="${pageContext.request.contextPath}/userController" method="POST">
                            <input type="hidden" name="action" value="<%= isEdit ? "edit" : "create" %>">
                            <% if (isEdit) { %>
                                <input type="hidden" name="user_id" value="<%= userId %>">
                            <% } %>
                            
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" 
                                       name="username" required
                                       value="${userAccount.username}"
                                       <%= isEdit ? "readonly" : "" %>
                                       placeholder="Enter username">
                            </div>
                            
                            <% if (!isEdit) { %>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" 
                                       name="password" required
                                       placeholder="Enter password">
                            </div>
                            <% } %>
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" 
                                       name="email" required
                                       value="${userDetails.email}"
                                       placeholder="Enter email">
                                <div class="form-text">Email must be unique</div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="phone_number" class="form-label">Phone Number</label>
                                <input type="text" class="form-control" id="phone_number" 
                                       name="phone_number"
                                       value="${userDetails.phone_number}"
                                       pattern="[0-9]+"
                                       placeholder="Enter phone number">
                                <div class="form-text">Phone number must contain only digits</div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="role_id" class="form-label">Role</label>
                                <select class="form-select" id="role_id" name="role_id" required>
                                    <option value="">Select role</option>
                                    <option value="1" ${userAccount.role_id == 1 ? 'selected' : ''}>Admin</option>
                                    <option value="2" ${userAccount.role_id == 2 ? 'selected' : ''}>Member</option>
                                </select>
                            </div>
                            
                            <div class="d-flex justify-content-end gap-2">
                                <a href="Users.jsp" class="btn btn-secondary btn-action">
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