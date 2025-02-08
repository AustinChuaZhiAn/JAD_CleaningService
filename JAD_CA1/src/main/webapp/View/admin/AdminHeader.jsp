<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Clean and Clear - Admin Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/AdminHeader.css">
</head>
<body>
	<%
    if (session.getAttribute("role_id") == null || !session.getAttribute("role_id").equals(1)) {
        response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
        return;
    }
    %>

	<!-- Navigation Bar -->
	<nav class="navbar navbar-expand-lg navbar-light sticky-top">
		<div class="container">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/AdminController"> <i
				class="fas fa-spray-can"></i> Clean and Clear
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/CategoryController?action=list">
							<i class="fas fa-th-large"></i>Categories
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/ServiceController"> <i
							class="fas fa-cogs"></i>Services
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/AdminController?action=list">
							<i class="fas fa-users"></i>Users
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/AdminController?action=CustomerInquiry">
							<i class="fas fa-users"></i>Customer Details
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/AdminController?action=Reports">
							<i class="fas fa-chart-bar"></i>Reports
					</a></li>
					<li class="nav-item"><a class="nav-link logout-btn"
						href="${pageContext.request.contextPath}/View/Login.jsp"> <i
							class="fas fa-sign-out-alt"></i>Logout
					</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>