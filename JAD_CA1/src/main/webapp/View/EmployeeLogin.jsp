<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Employee Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/LoginRegister.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Footer.css">
</head>
<body>
<%
    if (session.getAttribute("cleaner_name") != null) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/View/EmployeeLogin.jsp");
        return;
    }
%>
    <div class="page-container">
        <section class="left-section">
            <div class="company-info">
                <a href="home.jsp" class="company-logo-link">
                    <i class="fas fa-spray-can company-logo"></i>
                </a>
                <h2>Clean and Clear</h2>
                <p>Employee Portal</p>
                
                <div class="feature-list">
                    <div class="feature-item">
                        <i class="fas fa-user-tie"></i>
                        <span>Employee Access</span>
                    </div>
                    <div class="feature-item">
                        <i class="fas fa-calendar-check"></i>
                        <span>Schedule Management</span>
                    </div>
                    <div class="feature-item">
                        <i class="fas fa-tasks"></i>
                        <span>Task Overview</span>
                    </div>
                    <div class="feature-item">
                        <i class="fas fa-clock"></i>
                        <span>Time Tracking</span>
                    </div>
                </div>
                <div class="guest-access">
                    <a href="${pageContext.request.contextPath}/View/Login.jsp" class="guest-button">
                        <i class="fas fa-user"></i>
                        <span>Customer Login</span>
                    </a>
                </div>
            </div>
        </section>

        <section class="right-section">
            <div class="login-card">
                <div class="login-header">
                    <div class="logo-container">
                        <i class="fas fa-user-tie logo-icon"></i>
                    </div>
                    <h1>Employee Login</h1>
                    <p>Access your work dashboard</p>
                </div>

                <form action="${pageContext.request.contextPath}/AdminController" method="POST" class="login-form">
                    <input type="hidden" name="action" value="verifyEmployee">
                    
                    <% if (session.getAttribute("error") != null) { %>
                        <div class="error-message">
                            <i class="fas fa-exclamation-circle"></i>
                            <%= session.getAttribute("error") %>
                        </div>
                        <% session.removeAttribute("error"); %>
                    <% } %>

                    <div class="input-group">
                        <label for="name">Full Name</label>
                        <div class="input-with-icon">
                            <input type="text" id="name" name="name" required 
                                   placeholder="Enter your full name">
                            <i class="fas fa-user input-icon"></i>
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="phone">Phone Number</label>
                        <div class="input-with-icon">
                            <input type="tel" id="phone" name="phone" required 
                                   pattern="[0-9]+" 
                                   placeholder="Enter your phone number"
                                   title="Please enter numbers only">
                            <i class="fas fa-phone input-icon"></i>
                        </div>
                    </div>

                    <button type="submit" class="login-button">
                        <span>Sign In</span>
                        <i class="fas fa-arrow-right"></i>
                    </button>

                    <div class="login-footer">
                        <p>Contact admin for account issues</p>
                    </div>
                </form>
            </div>
        </section>
    </div>
    
    <%@ include file="Footer.jsp"%>
</body>
</html>