<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header class="main-header">
	<div class="logo">
		<a
			href="<%=request.getContextPath()%>/View/Home.jsp">Clean
			and Clear</a>
	</div>
	<nav class="nav-bar">
		<a
			href="<%=request.getContextPath()%>/CategoryController?action=view"
			class="nav-button">OUR SERVICES</a>
		<%
		if (session.getAttribute("user_id") != null) {
		%>
		<a
			href="<%=request.getContextPath()%>/userController"
			class="nav-button">Your Bookings</a>
		<%
		} else {
		%>
		<a
			href="<%=request.getContextPath()%>/View/Login.jsp"
			class="nav-button">Login</a>
		<%
		}
		%>
	</nav>
</header>
