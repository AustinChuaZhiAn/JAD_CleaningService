<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clean And Clear</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Footer.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Home.css">
</head>
<body>
	<%@ include file="Header.jsp"%>
		<div class="service-banner">
			<div class="banner-content">
				<div class="text-content">
					<p>Sparkling Clean, Every Time!</p>
					<p>Looking for reliable and professional cleaning services?
						We’ve got you covered!</p>
					<p>✨ Why Choose Us?</p>
					<p>Spotless Results: We leave your home or office gleaming like
						new.</p>
					<p>Trusted Professionals: Our trained team ensures your space
						is in safe hands.</p>
					<p>Customized Cleaning Plans: From one-time deep cleans to
						recurring services, we tailor to your needs.</p>
					<p>Eco-Friendly Solutions: Safe for your family, pets, and the
						planet. 📞 Call us today or book online to enjoy a fresh, clean
						environment you’ll love coming back to! 👉 Let us do the dirty
						work, so you don’t have to!</p>
					
					<a href="#" class="book-button">Book Now!</a>
				
				</div>
        		<div class="icon">
            		<img src="path/to/your/image.png" alt="Cleaning Icon" width="150" height="150">
        		</div>
			</div>
		</div>

		<div class="stats-container">
			<div class="stat-block">
				<h2 class="stat-title">Number of Members</h2>
				<p class="stat-number">164</p>
			</div>

			<div class="stat-block">
				<h2 class="stat-title">Average Rating</h2>
				<p class="stat-number">4.7</p>
			</div>

			<div class="stat-block">
				<h2 class="stat-title">JOBS COMPLETED SO FAR</h2>
				<p class="stat-number">7800</p>
			</div>
		</div>

		<%@ include file="Footer.jsp"%>

</body>
</html>