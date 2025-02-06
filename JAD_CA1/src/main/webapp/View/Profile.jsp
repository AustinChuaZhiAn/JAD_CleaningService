<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Model.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Your Profile</title>
<!-- Bootstrap 5.3 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Footer.css">

<style>
<
style>.booking-row:hover {
	background-color: #f8f9fa;
	transition: background-color 0.2s ease;
}

/* Modal Styles */
.modal {
	display: none;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	z-index: 1000;
}

.modal-content {
	background: white;
	padding: 20px;
	margin: 10% auto;
	width: 50%;
	border-radius: 10px;
	text-align: center;
}

.modal span {
	float: right;
	cursor: pointer;
	font-size: 20px;
	font-weight: bold;
}

.modal input[type="text"] {
	width: 80%;
	padding: 10px;
	margin-top: 10px;
	font-size: 16px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

.modal button {
	background-color: #007bff;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	margin-top: 10px;
	font-size: 16px;
}

.modal input[type="text"] {
	width: 80%; /* Keeps it at 80% width of the modal */
	padding: 10px;
	margin-top: 10px;
	font-size: 16px;
	border: 1px solid #ccc;
	border-radius: 5px;
	resize: vertical; /* Allows vertical resizing */
}

.modal button:hover {
	background-color: #0056b3;
}
</style>
</head>
<body class="bg-light">
	<%@ include file="Header.jsp"%>
	<%
	String username = (String) session.getAttribute("username");
	if (username == null) {
		String contextPath = request.getContextPath();
		String loginPage = contextPath + "/View/Login.jsp";
		response.sendRedirect(loginPage);
	}

	UserAccount user = (UserAccount) request.getAttribute("user");
	UserDetails profileDetails = (UserDetails) request.getAttribute("userdetails");
	List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
/* 	List<Cleaner> cleanerList = (List<Cleaner>) request.getAttribute("cleanerList");
 */	List<Address> addressList = (List<Address>) request.getAttribute("addressList");
	%>

	<div class="container py-5">
		<!-- User Details Card -->
		<div class="card shadow-sm mb-4">
			<div class="card-header bg-primary text-white">
				<h4 class="mb-0">User Details</h4>
			</div>
			<div class="card-body">
				<div class="row mb-3">
					<div class="col-sm-3">
						<strong>Name:</strong>
					</div>
					<div class="col-sm-9">
						<%=user.getUsername()%>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-sm-3">
						<strong>Email:</strong>
					</div>
					<div class="col-sm-9">
						<%=profileDetails.getEmail()%>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-sm-3">
						<strong>Phone Number:</strong>
					</div>
					<div class="col-sm-9">
						<%=profileDetails.getPhone_number()%>
					</div>
				</div>
				<div class="text-end mt-4">
					<a
						href="<%=request.getContextPath()%>/userController?action=UpdateProfile"
						class="btn btn-primary me-2"> <i class="fas fa-edit"></i> Edit
						Profile
					</a> <a
						href="<%=request.getContextPath()%>/userController?action=AddAddress"
						class="btn btn-success"> <i class="fas fa-plus"></i> Add
						Address
					</a> <a href="<%=request.getContextPath()%>/View/Login.jsp"
						class="btn btn-danger"> <i class="fas fa-sign-out-alt"></i>
						Logout
					</a>
				</div>
			</div>
		</div>

		<!-- Booking History Card -->
		<div class="card shadow-sm mb-4">
			<div class="card-header bg-primary text-white">
				<h4 class="mb-0">Booking History</h4>
			</div>
			<div class="card-body">
				<%
				if (bookingList != null && !bookingList.isEmpty()) {
				%>
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Booking ID</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
						<%
							for (int i = 0; i < bookingList.size(); i++) {
								Booking booking = bookingList.get(i);
/* 								Cleaner cleaner = cleanerList.stream().filter(c -> c.getCleaner_id() == booking.getCleaner_id()).findFirst()
								.orElse(null); */
							%>
							<tr class="booking-row"
								onclick="window.location.href='<%=request.getContextPath()%>/BookingController?action=bookingById&id=<%=booking.getBooking_id()%>'"
								style="cursor: pointer;">
								<td><%=i + 1%></td>
<%-- 								<td><%=booking.getTime()%></td>
								<td><%=booking.getDate()%></td> --%>
								<td><%=booking.getStatus()%></td>
<%-- 								<td><%=cleaner != null ? cleaner.getCleaner_name() : "N/A"%></td>
								<td><%=cleaner != null ? cleaner.getCleaner_contact() : "N/A"%></td> --%>
								<td>
<%-- 									<%
									if (booking.getStatus() != null && booking.getStatus().equalsIgnoreCase("Completed")) {
									%>
									<button type="button" class="btn btn-primary"
										onclick="openFeedbackForm('<%=booking.getBooking_id()%>', event)">
										<%=(booking.getFeedback() != null && !booking.getFeedback().equals("No feedback yet"))
		? "Edit Feedback"
		: "Feedback"%>
									</button> <%
 } else {
 %> <span class="text-danger">Appointment not
										completed</span> <%
 }
 %>--%>
								</td>
							</tr>
							<%
							}
							%> 
						</tbody>
					</table>
				</div>
				<%
				} else {
				%>
				<div class="alert alert-info text-center" role="alert">No
					booking history available.</div>
				<%
				}
				%>
			</div>
		</div>

		<!-- Feedback Modal -->
		<div id="feedbackModal" class="modal">
			<div class="modal-content">
				<span onclick="closeFeedbackForm()">&times;</span>
				<h2>Submit Feedback</h2>
				<form id="feedbackForm"
					action="<%=request.getContextPath()%>/BookingController?action=EditFeedback"
					method="post">
					<input type="hidden" name="action" value="submitFeedback">
					<input type="hidden" id="modalBookingId" name="bookingId">

					<label for="modalFeedback">Feedback:</label> <input type="text"
						id="modalFeedback" name="feedback" placeholder="Enter feedback..."
						required>

					<button type="submit">Submit</button>
				</form>
			</div>
		</div>

		<!-- Addresses Card -->
		<div class="card shadow-sm">
			<div class="card-header bg-primary text-white">
				<h4 class="mb-0">Addresses</h4>
			</div>
			<div class="card-body">
				<%
				if (addressList != null && !addressList.isEmpty()) {
				%>
				<div class="row">
					<%
					for (Address address : addressList) {
					%>
					<div class="col-md-6 mb-3">
						<div class="card h-100">
							<div class="card-body">
								<h5 class="card-title"><%=address.getBuilding_name()%></h5>
								<p class="card-text">
									<strong>Block:</strong>
									<%=address.getBlock_number()%><br> <strong>Street:</strong>
									<%=address.getStreet_name()%><br> <strong>Unit:</strong>
									<%=address.getUnit_number()%><br> <strong>Postal
										Code:</strong>
									<%=address.getPostal_code()%>
								</p>
							</div>
						</div>
					</div>
					<%
					}
					%>
				</div>
				<%
				} else {
				%>
				<div class="alert alert-info text-center" role="alert">No
					addresses available.</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<%@ include file="Footer.jsp"%>

	<!-- Bootstrap 5.3 JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<script>
		function openFeedbackForm(bookingId) {
			event.stopPropagation(); // Prevents the row click event from triggering
			document.getElementById("modalBookingId").value = bookingId;
			document.getElementById("feedbackModal").style.display = "block";
		}

		function closeFeedbackForm() {
			document.getElementById("feedbackModal").style.display = "none";
		}
	</script>
</body>
</html>