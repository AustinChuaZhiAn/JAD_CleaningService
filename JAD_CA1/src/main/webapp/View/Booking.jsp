<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.SimpleDateFormat, Model.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clean And Clear | Scheduling</title>
    <!-- Bootstrap 5.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Header.css">
</head>
<%
List<Cleaner> listCleaner = (List<Cleaner>) request.getAttribute("cleanerList");
List<Address> listAddresses = (List<Address>) request.getAttribute("addressList");
String username = (String) session.getAttribute("username");
if (username == null) {
    String contextPath = request.getContextPath();
    String loginPage = contextPath + "/Login";
    response.sendRedirect(loginPage);
}
%>
<body class="bg-light">
    <%@ include file="Header.jsp"%>

    <%!private Date[] getWeekDates(Calendar calendar) {
        Date[] weekDates = new Date[7];
        Calendar tempCal = (Calendar) calendar.clone();
        tempCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        for (int i = 0; i < 7; i++) {
            weekDates[i] = tempCal.getTime();
            tempCal.add(Calendar.DAY_OF_WEEK, 1);
        }
        return weekDates;
    }%>

    <%
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
    Date[] weekDates = getWeekDates(cal);
    %>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-body p-4">
                        <h2 class="card-title text-center mb-4">Schedule Cleaning Service</h2>
                        
                        <form id="cleaningScheduleForm" action="<%=request.getContextPath()%>/BookingController?action=AddToCart" method="Post">
                            <!-- Hidden input for selected date and time -->
                            <input type="hidden" id="selectedDateTime" name="selectedDateTime">
                            
                            <!-- Date and Time Selection -->
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="dateSelect" class="form-label">Select Date</label>
                                        <select id="dateSelect" class="form-select" onchange="updateTimeOptions()">
                                            <option value="">Select a Date</option>
                                            <% for (Date date : weekDates) {
                                                String formattedDate = dateFormat.format(date);
                                                String dayName = dayFormat.format(date);
                                            %>
                                            <option value="<%=formattedDate%>"><%=dayName%> - <%=formattedDate%></option>
                                            <% } %>
                                        </select>
                                    </div>
                                </div>
                                
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="timeSelect" class="form-label">Select Time</label>
                                        <select id="timeSelect" class="form-select" onchange="updateSelectedDateTime()">
                                            <option value="">Select Time</option>
                                            <% for (int hour = 8; hour <= 21; hour++) {
                                                String timeLabel = (hour > 12 ? (hour - 12) : hour) + ":00" + (hour >= 12 ? "pm" : "am");
                                                String timeValue = String.format("%02d:00", hour);
                                            %>
                                            <option value="<%=timeValue%>"><%=timeLabel%></option>
                                            <% } %>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <!-- Selected DateTime Display -->
                            <div class="alert alert-info mb-4" id="selectedDateTimeDisplay">
                                Selected Date and Time: <span id="dateTimeOutput" class="fw-bold">None selected</span>
                            </div>

                            <!-- Address Selection -->
                            <div class="mb-4">
                                <label for="addressDropdown" class="form-label">Address</label>
                                <div class="input-group">
                                    <select id="addressDropdown" name="address" class="form-select" required>
                                        <option value="">Select an Address</option>
                                        <% if (listAddresses != null && !listAddresses.isEmpty()) {
                                            for (Address address : listAddresses) { %>
                                        <option value="<%=address.getAddress()%>">
                                            <%=address.getBlock_number()%>,
                                            <%=address.getStreet_name()%>,
                                            <%=address.getUnit_number()%>
                                            <%=address.getPostal_code()%>
                                        </option>
                                        <% }
                                        } else { %>
                                        <option value="" disabled>No addresses available</option>
                                        <% } %>
                                    </select>
                                    <a href="<%=request.getContextPath()%>/userController" class="btn btn-primary" title="Add New Address">
                                        <i class="fas fa-plus"></i>
                                    </a>
                                </div>
                            </div>

                            <!-- Cleaner Selection -->
                            <div class="mb-4">
                                <label for="cleanerDropdown" class="form-label">Cleaner of Choice</label>
                                <select id="cleanerDropdown" name="cleaner" class="form-select" required>
                                    <option value="">Select a Cleaner</option>
                                    <% if (listCleaner != null && !listCleaner.isEmpty()) {
                                        for (Cleaner cleaner : listCleaner) { %>
                                    <option value="<%=cleaner.getCleaner_id()%>">
                                        <%=cleaner.getCleaner_name()%>
                                    </option>
                                    <% }
                                    } else { %>
                                    <option value="" disabled>No Cleaner available</option>
                                    <% } %>
                                </select>
                            </div>

                            <!-- Special Requests -->
                            <div class="mb-4">
                                <label for="specialRequests" class="form-label">Special Requests</label>
                                <textarea id="specialRequests" name="specialRequests" class="form-control" 
                                    rows="4" placeholder="Type your special requests here..."></textarea>
                            </div>

                            <!-- Submit Button -->
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary btn-lg px-5">Next</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5.3 JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
    function updateSelectedDateTime() {
        const dateSelect = document.getElementById('dateSelect');
        const timeSelect = document.getElementById('timeSelect');
        const selectedDateTime = document.getElementById('selectedDateTime');
        const dateTimeOutput = document.getElementById('dateTimeOutput');

        if (dateSelect.value && timeSelect.value) {
            const combinedDateTime = dateSelect.value + ' ' + timeSelect.value;
            selectedDateTime.value = combinedDateTime;
            dateTimeOutput.textContent = combinedDateTime;
        } else {
            selectedDateTime.value = '';
            dateTimeOutput.textContent = 'None selected';
        }
    }

    function updateTimeOptions() {
        const timeSelect = document.getElementById('timeSelect');
        timeSelect.value = '';
        updateSelectedDateTime();
    }
    </script>
</body>
</html>