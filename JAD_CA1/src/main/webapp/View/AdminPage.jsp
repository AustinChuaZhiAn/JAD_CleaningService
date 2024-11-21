<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .admin-container {
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .session-info {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        
        .info-item {
            margin: 10px 0;
            padding: 10px;
            background: #f8f9fa;
            border-radius: 4px;
        }
        
        .label {
            font-weight: bold;
            color: #333;
        }
        
        .admin-role {
            background: #dc3545;
            color: white;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="admin-container">
        <h1>Admin Dashboard</h1>
        
        <!-- Session Information -->
        <div class="session-info">
            <h2>Session Details</h2>
            <% 
                if (session != null && session.getAttribute("user_id") != null) {
            %>
                <div class="info-item">
                    <span class="label">User ID:</span>
                    <%= session.getAttribute("user_id") %>
                </div>
                <div class="info-item">
                    <span class="label">Role ID:</span>
                    <span class="admin-role">
                        <%= session.getAttribute("role_id") %>
                        (Administrator)
                    </span>
                </div>
                <div class="info-item">
                    <span class="label">Session ID:</span>
                    <%= session.getId() %>
                </div>
            <% 
                } else {
                    response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
                }
            %>
        </div>
        
        <!-- Just to show we're on admin page -->
        <div style="text-align: center; margin-top: 20px;">
            <h2><i class="fas fa-lock"></i> Admin Access Confirmed</h2>
        </div>
    </div>
</body>
</html>