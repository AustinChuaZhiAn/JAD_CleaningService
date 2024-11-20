<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Clean and Clear - Login</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Footer.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Login.css">
</head>
<body>
    <%@ include file="Header.jsp"%>

    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <h1>Welcome Back!</h1>
                <p>Sign in to access your cleaning service dashboard</p>
            </div>

            <form action="login" method="POST">
                <div class="input-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" required placeholder="Enter your email">
                </div>

                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required placeholder="Enter your password">
                </div>

                <button type="submit" class="login-button">Sign In</button>

                <div class="divider">
                    <span>Or continue with</span>
                </div>

                <div class="social-buttons">
                    <button type="button" class="social-button">
                        <img src="<%=request.getContextPath()%>/images/google-icon.png" alt="Google icon" />
                        Google
                    </button>
                    <button type="button" class="social-button">
                        <img src="<%=request.getContextPath()%>/images/facebook-icon.png" alt="Facebook icon" />
                        Facebook
                    </button>
                </div>

                <div class="login-footer">
                    <a href="#">Forgot your password?</a>
                    <p>Don't have an account? <a href="<%=request.getContextPath()%>/View/Register.jsp">Sign up</a></p>
                </div>
            </form>
        </div>
    </div>

    <%@ include file="Footer.jsp"%>
</body>
</html>