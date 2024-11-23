<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Model.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Profile</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Header.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Footer.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Profile.css">
</head>
<body>
	<%@ include file="Header.jsp"%>
	<%
	UserAccount user = (UserAccount) request.getAttribute("user");
	List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
	List<Cleaner> cleanerList = (List<Cleaner>) request.getAttribute("cleanerList");
	List<Address> addressList = (List<Address>) request.getAttribute("addressList");
	%>
	<div class="user-details">
		<h2>User Details</h2>
		<div class="detail">
			<label>Name:</label> <span><%=user.getName()%></span>
		</div>
		<div class="detail">
			<label>Email:</label> <span><%=user.getEmail()%></span>
		</div>
		<div class="detail">
			<label>Phone Number:</label> <span><%=user.getPhoneNumber()%></span>
		</div>
		<div class="actions">
			<a href="<%=request.getContextPath()%>/EditUserServlet"
				class="edit-btn">Edit</a> <a
				href="<%=request.getContextPath()%>/AddAddressServlet"
				class="add-address-btn">Add Address</a>
		</div>
	</div>

	<div class="booking-history">
		<h2>Booking History</h2>
		<div class="history-list">
			<%
			if (bookingList != null && !bookingList.isEmpty()) {
				for (int i = 0; i < bookingList.size(); i++) {
					Booking booking = bookingList.get(i);
					Cleaner cleaner = cleanerList.stream().filter(c -> c.getCleaner_id() == booking.getCleaner_id()).findFirst()
					.orElse(null);
			%>
			<div class="booking-item">
				<span class="booking-id">Booking #<%=i + 1%></span> <span
					class="booking-time"><%=booking.getBookingTime()%></span> <span
					class="booking-date"><%=booking.getBookingDate()%></span> <span
					class="cleaner-name"><%=cleaner != null ? cleaner.getCleaner_name() : "N/A"%></span>
				<span class="cleaner-contact"><%=cleaner != null ? cleaner.getCleaner_contact() : "N/A"%></span>
			</div>
			<%
			}
			} else {
			%>
			<div class="no-bookings">No booking history available.</div>
			<%
			}
			%>
		</div>
	</div>

	<div class="addresses">
		<h2>Addresses</h2>
		<div class="address-list">
			<%
			if (addressList != null && !addressList.isEmpty()) {
				for (Address address : addressList) {
			%>
			<div class="address-item">
				<span class="postal-code"><%=address.getPostal_code()%></span> <span
					class="block-number"><%=address.getBlock_number()%></span> <span
					class="street-name"><%=address.getStreet_name()%></span> <span
					class="unit-number"><%=address.getUnit_number()%></span> <span
					class="building-name"><%=address.getBuilding_name()%></span>
			</div>
			<%
			}
			} else {
			%>
			<div class="no-addresses">No addresses available.</div>
			<%
			}
			%>
		</div>
		<a href="<%=request.getContextPath()%>/AddAddressServlet"
			class="add-address-btn">Add Address</a>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>