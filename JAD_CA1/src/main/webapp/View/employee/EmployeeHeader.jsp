<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Session validation based on employee attributes
    if (session.getAttribute("cleaner_id") == null || 
        session.getAttribute("cleaner_name") == null || 
        session.getAttribute("isEmployee") == null || 
        !(Boolean)session.getAttribute("isEmployee")) {
        response.sendRedirect(request.getContextPath() + "/View/EmployeeLogin.jsp");
        return;
    }
%>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<!-- Custom CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/EmployeeHeader.css">
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <!-- Brand -->
        <a class="navbar-brand" href="${pageContext.request.contextPath}/BookingController?action=employeeDashboard">
            <i class="fas fa-spray-can me-2"></i>Clean and Clear
        </a>

        <!-- Toggle Button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar Content -->
        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/BookingController?action=employeeDashboard">
                        <i class="fas fa-calendar-alt me-1"></i>My Bookings
                    </a>
                </li>
            </ul>

            <!-- Employee Name and Logout Button -->
            <div class="d-flex align-items-center">
                <span class="text-light me-3">
                    <i class="fas fa-user-circle me-1"></i>
                    <%= session.getAttribute("cleaner_name") %>
                </span>
                <a href="${pageContext.request.contextPath}/View/EmployeeLogin.jsp" class="btn btn-light">
                    <i class="fas fa-sign-out-alt me-2"></i>Logout
                </a>
            </div>
        </div>
    </div>
</nav>