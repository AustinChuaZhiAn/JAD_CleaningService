<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Session check
    if (session.getAttribute("username") == null || !session.getAttribute("role_id").equals(1)) {
        response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Clean and Clear</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Admin.css">
    <style>
        .dashboard {
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
        }

        .sidebar-header {
            padding: 20px 0;
            text-align: center;
            border-bottom: 1px solid #34495e;
        }

        .admin-info {
            padding: 20px 0;
            text-align: center;
        }

        .admin-info img {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            margin-bottom: 10px;
        }

        .nav-links {
            margin-top: 20px;
        }

        .nav-links a {
            display: flex;
            align-items: center;
            padding: 15px;
            color: white;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .nav-links a:hover {
            background-color: #34495e;
        }

        .nav-links i {
            margin-right: 10px;
            width: 20px;
        }

        .main-content {
            flex-grow: 1;
            padding: 20px;
            background-color: #f5f6fa;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .stat-card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .stat-card h3 {
            margin: 0;
            color: #2c3e50;
        }

        .stat-card p {
            font-size: 24px;
            margin: 10px 0;
            color: #3498db;
        }

        .logout-btn {
            padding: 8px 15px;
            background-color: #e74c3c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }

        .logout-btn:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
    <div class="dashboard">
        <div class="sidebar">
            <div class="sidebar-header">
                <h2>Clean and Clear</h2>
            </div>
            <div class="admin-info">
                <img src="/api/placeholder/80/80" alt="Admin Avatar">
                <h3>Welcome, <%= session.getAttribute("username") %></h3>
                <p>Administrator</p>
            </div>
            <nav class="nav-links">
                <a href="#"><i class="fas fa-home"></i> Dashboard</a>
                <a href="#"><i class="fas fa-users"></i> Users</a>
                <a href="#"><i class="fas fa-calendar"></i> Bookings</a>
                <a href="#"><i class="fas fa-broom"></i> Services</a>
                <a href="#"><i class="fas fa-cog"></i> Settings</a>
            </nav>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Admin Dashboard</h1>
                <a href="<%=request.getContextPath()%>/View/Login.jsp" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
            </div>

            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Total Users</h3>
                    <p>125</p>
                    <small>+12% from last month</small>
                </div>
                <div class="stat-card">
                    <h3>Active Bookings</h3>
                    <p>48</p>
                    <small>Current pending bookings</small>
                </div>
                <div class="stat-card">
                    <h3>Total Services</h3>
                    <p>15</p>
                    <small>Available service types</small>
                </div>
                <div class="stat-card">
                    <h3>Revenue</h3>
                    <p>$12,450</p>
                    <small>This month's earnings</small>
                </div>
            </div>

            <!-- Add more admin content sections here -->

        </div>
    </div>
</body>
</html>