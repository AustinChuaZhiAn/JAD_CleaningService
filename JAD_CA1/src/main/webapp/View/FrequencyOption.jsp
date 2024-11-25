<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Frequency Options</title>

<!-- Bootstrap 5.3 CSS (via CDN) -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Footer.css">

<style>
@charset "UTF-8";

body html {
	margin: 0;
	height: 100%;
	width: 100%
}

.main-container {
	margin: 5rem auto;
	padding: 2rem;
	background: white;
	border-radius: 0.5rem;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
	height: 100%;
	width: 100%;
}

.section-title {
	font-size: 1.25rem;
	font-weight: bold;
	border-bottom: 2px solid #000;
	padding-bottom: 0.5rem;
	margin-bottom: 1rem;
}

.card-link {
	text-decoration: none;
	color: inherit;
}

.frequency-card {
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	transition: transform 0.2s;
}

.frequency-card:hover {
	transform: scale(1.05);
}

.card-body {
	text-align: center;
}

.card-title {
	font-size: 1.25rem;
	margin-bottom: 0.5rem;
}
</style>
</head>
<body class="container-fluid">

	<%@ include file="Header.jsp"%>
	<div class="main-container">
		<h2 class="section-title">Frequency Options</h2>
		<div class="container">
			<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
				<%
				List<Frequency> frequencyList = (List<Frequency>) request.getAttribute("frequencyList");
				if (frequencyList != null && !frequencyList.isEmpty()) {
					for (Frequency freq : frequencyList) {
				%>
				<div class="col">
					<a href="<%=request.getContextPath() %>/BookingController?frequency_id=<%= freq.getFrequencyId() %>"
						class="card-link">
						<div class="card frequency-card h-100">
							<div class="card-body">
								<h3 class="card-title"><%=freq.getFrequency()%></h3>
								<p class="card-text">Select this frequency for your booking.</p>
							</div>
						</div>
					</a>
				</div>
				<%
				}
				} else {
				%>
				<div class="col-12">
					<p class="text-center">No frequency options available.</p>
				</div>
				<%
				}
				%>
			</div>
			<div class="my-4">
				<h2 class="section-title">Cleaning Duration</h2>
				<p class="duration-text">Cleaning will take 2 - 4 hrs</p>
			</div>
		</div>
	</div>
	<%@ include file="Footer.jsp"%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
