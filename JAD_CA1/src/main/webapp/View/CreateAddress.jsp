<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Address</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Footer.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/CreateAddress.css">
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=request.getContextPath()%>/AddAddressServlet"
		method="post">
		<div class="form-group">
			<label for="address_type_id">Address Type:</label> <select
				id="address_type_id" name="address_type_id">
				<%
				List<AddressType> addressTypes = (List<AddressType>) request.getAttribute("addressTypes");
				for (AddressType type : addressTypes) {
				%>
				<option value="<%=type.getId()%>"><%=type.getName()%></option>
				<%
				}
				%>
			</select>
		</div>
		<div class="form-group">
			<label for="postal_code">Postal Code:</label> <input type="text"
				id="postal_code" name="postal_code">
		</div>
		<div class="form-group">
			<label for="block_number">Block Number:</label> <input type="text"
				id="block_number" name="block_number">
		</div>
		<div class="form-group">
			<label for="street_name">Street Name:</label> <input type="text"
				id="street_name" name="street_name">
		</div>
		<div class="form-group">
			<label for="unit_number">Unit Number:</label> <input type="text"
				id="unit_number" name="unit_number">
		</div>
		<div class="form-group">
			<label for="building_name">Building Name:</label> <input type="text"
				id="building_name" name="building_name">
		</div>
		<div class="actions">
			<button type="submit">Save Address</button>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>