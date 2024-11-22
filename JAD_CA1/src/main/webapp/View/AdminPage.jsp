<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | Services Management</title>
    <link href="css/admin-dashboard.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <%
        // Session checking
        String userRole = (String) session.getAttribute("userRole");
        String userName = (String) session.getAttribute("userName");
        if (userRole == null || !userRole.equals("ADMIN")) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <div class="wrapper">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>ServiceHub Admin</h3>
            </div>
            
            <div class="profile-info">
                <div class="profile-image">
                    <i class="fas fa-user"></i>
                </div>
                <div class="profile-details">
                    <h4><%= userName %></h4>
                    <p>Administrator</p>
                </div>
            </div>

            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="dashboard.jsp" class="nav-link active">
                        <i class="fas fa-th-large"></i>
                        Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a href="services.jsp" class="nav-link">
                        <i class="fas fa-cogs"></i>
                        Services
                    </a>
                </li>
                <li class="nav-item">
                    <a href="categories.jsp" class="nav-link">
                        <i class="fas fa-tags"></i>
                        Categories
                    </a>
                </li>
                <li class="nav-item">
                    <a href="members.jsp" class="nav-link">
                        <i class="fas fa-users"></i>
                        Members
                    </a>
                </li>
                <li class="nav-item">
                    <a href="logout.jsp" class="nav-link">
                        <i class="fas fa-sign-out-alt"></i>
                        Logout
                    </a>
                </li>
            </ul>
        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <div class="page-header">
                <h1 class="page-title">Dashboard Overview</h1>
                <ul class="breadcrumb">
                    <li>Home</li>
                    <li>Dashboard</li>
                </ul>
            </div>

            <!-- Statistics Cards -->
            <div class="dashboard-cards">
                <%
                    // Get statistics from your DAO/Service
                    ServiceDAO serviceDAO = new ServiceDAO();
                    int totalServices = serviceDAO.getTotalServices();
                    int totalCategories = serviceDAO.getTotalCategories();
                    int activeMembers = serviceDAO.getActiveMembers();
                    double totalRevenue = serviceDAO.getTotalRevenue();
                %>
                <div class="card stat-card">
                    <div class="stat-icon" style="background: var(--primary-color)">
                        <i class="fas fa-cogs"></i>
                    </div>
                    <div class="stat-details">
                        <h3><%= totalServices %></h3>
                        <p>Total Services</p>
                    </div>
                </div>

                <div class="card stat-card">
                    <div class="stat-icon" style="background: var(--accent-color)">
                        <i class="fas fa-tags"></i>
                    </div>
                    <div class="stat-details">
                        <h3><%= totalCategories %></h3>
                        <p>Categories</p>
                    </div>
                </div>

                <div class="card stat-card">
                    <div class="stat-icon" style="background: var(--success-color)">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-details">
                        <h3><%= activeMembers %></h3>
                        <p>Active Members</p>
                    </div>
                </div>

                <div class="card stat-card">
                    <div class="stat-icon" style="background: var(--warning-color)">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <div class="stat-details">
                        <h3>$<%= String.format("%.2f", totalRevenue) %></h3>
                        <p>Total Revenue</p>
                    </div>
                </div>
            </div>

            <!-- Services Table -->
            <div class="table-container">
                <div class="table-header">
                    <h2>Services List</h2>
                    <a href="addService.jsp" class="btn btn-primary">
                        <i class="fas fa-plus"></i>
                        Add New Service
                    </a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Service Name</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Service> services = serviceDAO.getAllServices();
                            for(Service service : services) {
                        %>
                        <tr>
                            <td><%= service.getName() %></td>
                            <td><%= service.getServiceType().getName() %></td>
                            <td>$<%= String.format("%.2f", service.getPrice()) %></td>
                            <td>
                                <span class="status-badge <%= service.isActive() ? "status-active" : "status-inactive" %>">
                                    <%= service.isActive() ? "Active" : "Inactive" %>
                                </span>
                            </td>
                            <td>
                                <div class="action-buttons">
                                    <a href="editService.jsp?id=<%= service.getId() %>" class="btn btn-edit">
                                        <i class="fas fa-edit"></i>
                                        Edit
                                    </a>
                                    <a href="javascript:void(0);" onclick="deleteService(<%= service.getId() %>)" class="btn btn-delete">
                                        <i class="fas fa-trash"></i>
                                        Delete
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <script>
        function deleteService(id) {
            if(confirm('Are you sure you want to delete this service?')) {
                window.location.href = 'deleteService?id=' + id;
            }
        }
    </script>
</body>
</html>