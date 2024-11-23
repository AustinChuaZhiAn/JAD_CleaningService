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
<% UserAccount user = (UserAccount) request.getAttribute("user"); %>
<body>
<%@ include file="Header.jsp"%>
  <form action="<%=request.getContextPath()%>/UpdateUserServlet" method="post">
    <div class="form-group">
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" value="<%=user.getUsername()%>">
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" value="<%=user.getEmail()%>">
    </div>
    <div class="form-group">
      <label for="phone">Phone Number:</label>
      <input type="tel" id="phone" name="phone" value="<%=user.getPhoneNumber()%>">
    </div>
    <div class="actions">
      <button type="submit">Save Changes</button>
    </div>
  </form>
  <%@ include file="Footer.jsp"%>
</body>
</html>