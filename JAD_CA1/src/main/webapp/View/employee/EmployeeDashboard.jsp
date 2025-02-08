<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ include file="EmployeeHeader.jsp"%>
<%@ page import="Model.BookingToService"%>
<%@ page import="Model.Service"%>
<%@ page import="Model.Cleaner"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Dashboard</title>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

<!-- Custom CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/EmployeeDashboard.css">
</head>
<body>
	<div class="container dashboard-container">
		<div class="row">
			<div class="col-12">
				<div class="card bookings-card">
					<div class="card-header bg-white">
						<h4 class="mb-0">
							<i class="fas fa-calendar-alt me-2"></i>My Assigned Bookings
						</h4>
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-hover align-middle">
								<thead>
									<tr>
										<th>Booking ID</th>
										<th>Date</th>
										<th>Time</th>
										<th>Service Details</th>
										<th>Price</th>
										<th>Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<%
									List<BookingToService> bookings = (List<BookingToService>) request.getAttribute("bookings");
									List<Service> services = (List<Service>) request.getAttribute("services");
									if (bookings != null && !bookings.isEmpty()) {
										for (int i = 0; i < bookings.size(); i++) {
											BookingToService booking = bookings.get(i);
											Service service = services.get(i);
									%>
									<tr>
										<td><%=booking.getBooking_id()%></td>
										<td><%=booking.getDate() != null ? booking.getDate() : "N/A"%></td>
										<td><%=booking.getTime() != null ? booking.getTime() : "N/A"%></td>
										<td>
											<div>
												<strong><%=service.getCategory_name()%></strong><br>
												<small class="text-muted"> <%=service.getService_type()%>
													- <%=service.getFrequency()%>
												</small>
											</div>
										</td>
										<td>$<%=service.getPrice()%></td>
										<td><span
											class="badge status-badge <%=booking.getStatus_name().toLowerCase().replace(" ", "-")%>">
												<%=booking.getStatus_name()%>
										</span></td>
										<td>
											<div class="d-flex gap-2">
												<button class="btn btn-sm btn-view-details"
													data-bs-toggle="modal"
													data-bs-target="#bookingDetailsModal<%=booking.getBookingToService_id()%>">
													<i class="fas fa-eye"></i> View
												</button>

												<%
												if (booking.getStatus_name().equals("Pending") || booking.getStatus_name().equals("Paid")) {
												%>
												<form
													action="${pageContext.request.contextPath}/BookingController"
													method="post" style="display: inline;">
													<input type="hidden" name="action" value="updateStatus">
													<input type="hidden" name="bookingId"
														value="<%=booking.getBookingToService_id()%>"> <input
														type="hidden" name="statusId" value="2">
													<!-- In Progress -->
													<button type="submit" class="btn btn-sm btn-success">
														<i class="fas fa-play"></i> Start
													</button>
												</form>
												<%
												} else if (booking.getStatus_name().equals("In Progress")) {
												%>
												<form
													action="${pageContext.request.contextPath}/BookingController"
													method="post" style="display: inline;">
													<input type="hidden" name="action" value="updateStatus">
													<input type="hidden" name="bookingId"
														value="<%=booking.getBookingToService_id()%>"> <input
														type="hidden" name="statusId" value="3">
													<!-- Completed -->
													<button type="submit" class="btn btn-sm btn-danger">
														<i class="fas fa-stop"></i> End
													</button>
												</form>
												<%
												}
												%>
											</div>
										</td>
									</tr>

									<!-- Booking Details Modal -->
									<div class="modal fade"
										id="bookingDetailsModal<%=booking.getBookingToService_id()%>"
										tabindex="-1">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h5 class="modal-title">Booking Details</h5>
													<button type="button" class="btn-close"
														data-bs-dismiss="modal"></button>
												</div>
												<div class="modal-body">
													<p>
														<strong>Booking ID:</strong>
														<%=booking.getBooking_id()%></p>
													<p>
														<strong>Date:</strong>
														<%=booking.getDate() != null ? booking.getDate() : "N/A"%></p>
													<p>
														<strong>Time:</strong>
														<%=booking.getTime() != null ? booking.getTime() : "N/A"%></p>
													<p>
														<strong>Category:</strong>
														<%=service.getCategory_name()%></p>
													<p>
														<strong>Service Type:</strong>
														<%=service.getService_type()%></p>
													<p>
														<strong>Frequency:</strong>
														<%=service.getFrequency()%></p>
													<p>
														<strong>Price:</strong> $<%=service.getPrice()%></p>
													<p>
														<strong>Special Requests:</strong>
														<%=booking.getSpecialRequest() != null ? booking.getSpecialRequest() : "None"%></p>
													<p>
														<strong>Status:</strong>
														<%=booking.getStatus_name()%></p>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-bs-dismiss="modal">Close</button>
												</div>
											</div>
										</div>
									</div>
									<%
									}
									} else {
									%>
									<tr>
										<td colspan="7" class="text-center text-muted">No
											bookings assigned</td>
									</tr>
									<%
									}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ include file="EmployeeFooter.jsp"%>