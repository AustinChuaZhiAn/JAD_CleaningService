<!-- testDatabase.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Database Connection Test</title>
    <style>
        .test-button {
            padding: 10px 20px;
            margin: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .test-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h2>Database Connection Test Page</h2>
    
    <form action="${pageContext.request.contextPath}/TestDatabaseController" method="get">
        <button type="submit" class="test-button">Test Database Connection</button>
    </form>
</body>
</html>