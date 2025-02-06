<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, Model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Cart</title>

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
<script
	src="https://www.paypal.com/sdk/js?client-id=AXXyrijYc4BAfZB5tK1Ilk3iiw1OsQyHM0IcR3oimvfqCH-reutRIRlrxoaXh9GZRKXotlHzoU81vhKv&currency=USD"></script>
<style>
.cart-table th, .cart-table td {
	text-align: center;
	vertical-align: middle;
}
</style>

</head>
<body class="bg-light">
	<%@ include file="Header.jsp"%>

	<div class="container py-5">
		<h2 class="text-center mb-4">Your Cart</h2>

		<!-- Retrieve the list of bookings from the session -->
		<%
		List<List<String>> listOfServices = (List<List<String>>) session.getAttribute("listOfServices");
		List<Service> listOfServiceDisplay = (List<Service>) session.getAttribute("listOfServiceDisplay");
		List<Cleaner> listOfCleaners = (List<Cleaner>) session.getAttribute("listOfCleaners");
		%>

		<div class="card shadow-sm">
			<div class="card-header bg-primary text-white">
				<h4 class="mb-0">Added Bookings</h4>
			</div>
			<div class="card-body">
				<%
				if (listOfServices != null && !listOfServices.isEmpty()) {
				%>
				<div class="table-responsive">
					<table class="table table-bordered cart-table">
						<thead class="table-dark">
							<tr>
								<th>#</th>
								<th>Cleaner</th>
								<th>Date And Time</th>
								<th>Special Request</th>
								<th>Category</th>
								<th>Service Type</th>
								<th>Frequency</th>
								<th>Price(w/o GST)</th>
								<th>Price(w GST)</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (int i = 0; i < listOfServices.size(); i++) {
								List<String> services = listOfServices.get(i);
								Cleaner cleaner = listOfCleaners.get(i);
								Service serviceDisplay = listOfServiceDisplay.get(i);
							%>
							<tr>
								<td><%=i + 1%></td>
								<td><%=cleaner.getCleaner_name()%>
								<td><%=services.get(2)%></td>
								<td><%=services.get(0)%></td>
								<td><%=serviceDisplay.getCategory_name()%></td>
								<td><%=serviceDisplay.getService_type()%></td>
								<td><%=serviceDisplay.getFrequency()%></td>
								<td><%=serviceDisplay.getPrice()%></td>
								<td><%=String.format("%.2f", (109.00/100) * Double.parseDouble(serviceDisplay.getPrice()))%></td>
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
				<div class="alert alert-info text-center">Your cart is empty.</div>
				<%
				}
				%>
			</div>
		</div>

		<!-- Buttons -->
		<div class="text-center mt-4">
							
			<a
				href="<%=request.getContextPath()%>/CategoryController?action=view"
				class="btn btn-success mb-3"> <i class="fas fa-plus"></i> Add
				More Bookings
			</a>

			<!-- PayPal Button Container -->
			<div id="paypal-button-container" class="mt-3"></div>

			<!-- PayPal Integration Script -->

			<script>
			paypal.Buttons({
			    createOrder: function(data, actions) {
			        let totalPriceWithGST = 0;
			        <%if (listOfServiceDisplay != null) {%>
			            <%for (Service service : listOfServiceDisplay) {%>
			                totalPriceWithGST += (109.0 / 100) * (parseFloat("<%=service.getPrice()%>") || 0);
			            <%}%>
			        <%}%>

			        return fetch('<%=request.getContextPath()%>/CreatePaymentServlet', {
			            method: 'POST',
			            headers: {
			                'Content-Type': 'application/json'
			            },
			            body: JSON.stringify({
			                amount: totalPriceWithGST.toFixed(2)
			            })
			        })
			        .then(response => response.json())
			        .then(data => {
			            if (data.error) {
			                throw new Error(data.error);
			            }
			            return data.id;
			        })
			        .catch(err => {
			            console.error('Error:', err);
			            alert('There was an error processing your payment. Please try again.');
			            throw err;
			        });
			    },
			    onApprove: function(data, actions) {
			        console.log('Payment Data:', data); // Debug log
			        return fetch('<%=request.getContextPath()%>/ExecutePaymentServlet', {
			            method: 'POST',
			            headers: {
			                'Content-Type': 'application/json'
			            },
			            body: JSON.stringify({
			                paymentId: data.paymentID,  // Changed to match the PayPal response
			                payerId: data.payerID      // Changed to match the PayPal response
			            })
			        })
			        .then(response => {
			            if (!response.ok) {
			                throw new Error('Network response was not ok');
			            }
			            return response.json();
			        })
					.then(details => {
					    if (details.error) {
					        throw new Error(details.error);
					    }
					    
					    if (details.status === 'success') {
					        const payerName = details.payer?.name?.given_name || 'customer';
					        alert('Transaction completed by ' + payerName);
					        
					        // Create a temporary div to hold the form
					        const tempDiv = document.createElement('div');
					        tempDiv.innerHTML = details.redirectForm;
					        
					        // Get the form from the div and add it to the document
					        const form = tempDiv.firstChild;
					        document.body.appendChild(form);
					        
					        // Submit the form
					        document.getElementById('redirectForm').submit();
					    } else {
					        throw new Error('Payment was not successful');
					    }
			        })
			        .catch(err => {
			            console.error('Error:', err);
			            alert('There was an error completing your payment. Please try again.');
			            throw err;
			        });
			    }
			}).render('#paypal-button-container');
            </script>
		</div>
	</div>

	<%@ include file="Footer.jsp"%>

	<!-- Bootstrap 5.3 JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
