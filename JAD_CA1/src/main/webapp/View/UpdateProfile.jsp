<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Profile</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Footer.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/UpdateProfile.css">
</head>
<% 
	UserAccount user = (UserAccount) request.getAttribute("user"); 
	UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
	
	String username = (String) session.getAttribute("username");
	if (username == null) {
	    String contextPath = request.getContextPath();
	    String loginPage = contextPath + "/View/Login.jsp";
	    response.sendRedirect(loginPage);
	}
	
	if (request.getAttribute("errorMessage") != null) {
	%>
	<script>
        alert('<%=request.getAttribute("errorMessage")%>
		');
	</script>
	<%
	}
%>
<body>
<%@ include file="Header.jsp"%>
  <form action="<%=request.getContextPath()%>/Controller/userController?action=UpdateProfile" method="post">
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" value="<%=user.getUsername()%>">
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" value="<%=userDetails.getEmail()%>">
    </div>
    <div class="form-group">
      <label for="phone">Phone Number:</label>
      <input type="tel" id="phone" name="phone" value="<%=userDetails.getPhone_number()%>">
    </div>
    <div class="actions">
      <button type="submit">Save Changes</button>
    </div>
  </form>
  <%@ include file="Footer.jsp"%>
</body>
</html>