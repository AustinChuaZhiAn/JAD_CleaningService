<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.UserAccount" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .card {
            transition: transform 0.3s ease;
            border: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .btn-action {
            transition: all 0.3s ease;
        }
        .btn-action:hover {
            transform: translateY(-2px);
        }
        .role-badge {
            font-size: 0.8rem;
            padding: 0.25rem 0.5rem;
        }
    </style>
</head>
<body class="bg-light">
    <%@ include file="AdminHeader.jsp" %>
    
    <div class="container my-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="mb-0"><i class="fas fa-users me-2"></i>Users</h2>
                <p class="text-muted">Manage user accounts</p>
            </div>
        </div>

        <%
        if (session.getAttribute("error") != null) {
        %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <%= session.getAttribute("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <%
            session.removeAttribute("error");
        }
        %>

        <%
        if (session.getAttribute("success") != null) {
        %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle me-2"></i>
                <%= session.getAttribute("success") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <%
            session.removeAttribute("success");
        }
        %>

        <%
        ArrayList<UserAccount> users = (ArrayList<UserAccount>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
        %>
            <div class="row g-4">
                <div class="col-12">
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Role</th>
                                            <th class="text-center">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                        for (UserAccount user : users) {
                                            boolean isCurrentUser = user.getUser_id() == (Integer)session.getAttribute("user_id");
                                        %>
                                            <tr>
                                                <td>
                                                    <span class="fw-medium"><%= user.getUsername() %></span>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.request.contextPath}/AdminController" method="POST" class="d-inline">
                                                        <input type="hidden" name="action" value="updateUser">
                                                        <input type="hidden" name="user_id" value="<%= user.getUser_id() %>">
                                                        <select name="role_id" class="form-select" 
                                                                onchange="this.form.submit()" 
                                                                <%= isCurrentUser ? "disabled" : "" %>>
                                                            <option value="1" <%= user.getRole_id() == 1 ? "selected" : "" %>>Admin</option>
                                                            <option value="2" <%= user.getRole_id() == 2 ? "selected" : "" %>>Member</option>
                                                        </select>
                                                    </form>
                                                </td>
                                                <td class="text-center">
                                                    <%
                                                    if (!isCurrentUser) {
                                                    %>
                                                        <button type="button" 
                                                                class="btn btn-sm btn-outline-danger"
                                                                onclick="confirmDelete(<%= user.getUser_id() %>)">
                                                            <i class="fas fa-trash-alt"></i>
                                                        </button>
                                                    <%
                                                    }
                                                    %>
                                                </td>
                                            </tr>
                                        <%
                                        }
                                        %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <%
        } else {
        %>
            <div class="text-center py-5">
                <i class="fas fa-users fa-3x text-muted mb-3"></i>
                <p class="lead text-muted">No users found</p>
            </div>
        <%
        }
        %>
    </div>

    <div class="modal fade" id="deleteModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Confirm Delete</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete this user?
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
        function confirmDelete(userId) {
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            document.getElementById('deleteLink').href = 
                '${pageContext.request.contextPath}/AdminController?action=deleteUser&id=' + userId;
            modal.show();
        }
    </script>
</body>
</html>