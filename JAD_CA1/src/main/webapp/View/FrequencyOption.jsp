<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clean And Clear | Frequency</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Footer.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/FrequencyOption.css">
</head>
<body>
	<%@ include file="Header.jsp"%>

	<form action="<%=request.getContextPath()%>/Controller/Booking.java" method="POST">
		<div class="section">
			<div class="section-title">Frequency Options</div>
			<div class="options-container">
				<%
				// Retrieve the frequencyList from the request attribute
				List<Frequency> frequencyList = (List<Frequency>) request.getAttribute("frequencyList");
				if (frequencyList != null) {
					for (Frequency freq : frequencyList) {
				%>
				<label class="option-card"> <input type="radio"
					name="frequency" value="<%=freq.getFrequencyId()%>" /> <%=freq.getFrequency()%>
				</label>
				<%
				}
				} else {
				%>
				<p>No frequency options available.</p>
				<%
				}
				%>
			</div>
		</div>

		<div class="section">
			<div class="section-title">Cleaning Duration</div>
			<div class="options-container">
				<label class="option-card"> <input type="radio"
					name="duration" value="2hr" /> 2 Hours
				</label> <label class="option-card"> <input type="radio"
					name="duration" value="3hr" /> 3 Hours
				</label> <label class="option-card"> <input type="radio"
					name="duration" value="4hr" /> 4 Hours
				</label> <label class="option-card"> <input type="radio"
					name="duration" value="5hr" /> 5 Hours
				</label>
			</div>
		</div>

		<button type="submit" class="next-button">Next</button>
	</form>

	<%@ include file="Footer.jsp"%>
</body>
</html>