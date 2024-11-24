<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="AdminHeader.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminPage.css">
<%
Integer userCount = (Integer) request.getAttribute("userCount");
Integer categoryCount = (Integer) request.getAttribute("categoryCount");
userCount = userCount != null ? userCount : 0;
categoryCount = categoryCount != null ? categoryCount : 0;
%>

<!-- Welcome Section -->
<div class="welcome-section">
    <div class="container">
        <h1><i class="fas fa-tachometer-alt me-2"></i>Admin Dashboard</h1>
        <p>Welcome back, <%= session.getAttribute("username") %>!</p>
    </div>
</div>

<!-- Main Content -->
<div class="container">
    <!-- Error Messages -->
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-circle me-2"></i>
        <%= request.getAttribute("errorMessage") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% } %>

    <!-- Stats Cards -->
    <div class="row g-4">
        <!-- Users Card -->
        <div class="col-md-6">
            <div class="card stat-card">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted mb-2">Total Users</h6>
                            <h2 class="mb-0"><%= userCount %></h2>
                            <p class="text-success mt-2 mb-0">
                                <i class="fas fa-chart-line me-1"></i>Active Members
                            </p>
                        </div>
                        <div class="stat-icon bg-primary bg-opacity-10">
                            <i class="fas fa-users fa-2x text-primary"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Categories Card -->
        <div class="col-md-6">
            <div class="card stat-card">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-muted mb-2">Total Categories</h6>
                            <h2 class="mb-0"><%= categoryCount %></h2>
                            <p class="text-info mt-2 mb-0">
                                <i class="fas fa-list-alt me-1"></i>Service Categories
                            </p>
                        </div>
                        <div class="stat-icon bg-info bg-opacity-10">
                            <i class="fas fa-th-large fa-2x text-info"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="AdminFooter.jsp"%>