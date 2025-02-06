<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Model.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Booking Details</title>
    <!-- Bootstrap 5.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
</head>
<%
    // Retrieve the lists from request attributes
    List<BookingToService> btsList = (List<BookingToService>) request.getAttribute("btsList");
    List<Service> serviceList = (List<Service>) request.getAttribute("serviceList");
    List<Cleaner> cleanerList = (List<Cleaner>) request.getAttribute("cleanerList");
    List<Address> addressList = (List<Address>) request.getAttribute("addressList");
    String feedback = (String) request.getAttribute("feedback");

    // Check if user is logged in
    String username = (String) session.getAttribute("username");
    if (username == null) {
        String contextPath = request.getContextPath();
        String loginPage = contextPath + "/View/Login.jsp";
        response.sendRedirect(loginPage);
        return;
    }
%>
<body class="bg-light">
	<%@ include file="Header.jsp"%>
	
    <div class="container py-5">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Booking Details</h4>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Category Service Type</th>
                                <th>Frequency</th>
                                <th>Price</th>
                                <th>Special Request</th>
                                <th>Time</th>
                                <th>Date</th>
                                <th>Cleaner</th>
                                <th>Cleaner Contact</th>
                                <th>Postal Code</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                            for (int i = 0; i < btsList.size(); i++) { 
                                BookingToService bts = btsList.get(i);
                                Service service = serviceList.get(i);
                                Cleaner cleaner = cleanerList.get(i);
                                Address address = addressList.get(i);
                            %>
                            <tr>
                                <td><%=service.getCategory_name() + " - " + service.getService_type()%></td>
                                <td><%=service.getFrequency()%></td>
                                <td>$<%=service.getPrice()%></td>
                                <td><%=bts.getSpecialRequest() != null ? bts.getSpecialRequest() : "N/A"%></td>
                                <td><%=bts.getTime()%></td>
                                <td><%=bts.getDate()%></td>
                                <td><%=cleaner.getCleaner_name()%></td>
                                <td><%=cleaner.getCleaner_contact()%></td>
                                <td><%=address.getPostal_code()%></td>
                                <td><%=bts.getStatus_name()%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>

                <% 
                // Check if all bookings are completed
                boolean allCompleted = btsList.stream()
                    .allMatch(bts -> "Completed".equalsIgnoreCase(bts.getStatus_name()));
                
                if (allCompleted) { 
                %>
                <div class="card mt-4">
                    <div class="card-header">
                        <h5 class="mb-0">Feedback</h5>
                    </div>
                    <div class="card-body">
                        <form action="<%=request.getContextPath()%>/BookingController?action=EditFeedback" method="post">
                            <input type="hidden" name="action" value="submitFeedback">
                            <input type="hidden" value="<%=btsList.get(0).getBooking_id() %>" name="bookingId">
                                                        
                            <div class="mb-3">
                                <label for="feedbackTextarea" class="form-label">
                                    <%=("No feedback yet".equals(feedback)) ? "Add Feedback" : "Edit Feedback" %>
                                </label>
                                <textarea class="form-control" id="feedbackTextarea" name="feedback" rows="4" 
                                    <%=("No feedback yet".equals(feedback)) ? "" : "placeholder='" + feedback + "'"%>>
                                    <%=("No feedback yet".equals(feedback)) ? "" : feedback%>
                                </textarea>
                            </div>
                            
                            <div class="d-flex justify-content-between">
                                <a href="<%=request.getContextPath()%>/userController" class="btn btn-secondary">
                                    Back to Profile
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <%=("No feedback yet".equals(feedback)) ? "Submit Feedback" : "Update Feedback" %>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <% } else { %>
                <div class="text-center mt-4">
                    <a href="<%=request.getContextPath()%>/userController" class="btn btn-secondary">
                        Back to Profile
                    </a>
                </div>
                <% } %>
            </div>
        </div>
    </div>
	
    <!-- Bootstrap 5.3 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>