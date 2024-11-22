<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.SimpleDateFormat, Model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clean And Clear | Scheduling</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Booking.css">
</head>
<%
List<Cleaner> listCleaner = (List<Cleaner>) request.getAttribute("cleanerList");
List<Address> listAddresses = (List<Address>) request.getAttribute("addressList");
UserAccount user = (UserAccount) session.getAttribute("User");
if (user == null) {
    String contextPath = request.getContextPath();
    String loginPage = contextPath + "/View/Login.jsp";
    response.sendRedirect(loginPage);
}
%>
<body>
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

    <form id="cleaningScheduleForm" action="<%=request.getContextPath()%>/Controller/BookingController.java" method="Post">
        <div class="schedule-container">
            <!-- Single hidden input for the selected date and time -->
            <input type="hidden" id="selectedDateTime" name="selectedDateTime">
            
            <!-- Date Selection -->
            <div class="date-selection">
                <label for="dateSelect">Select Date:</label>
                <select id="dateSelect" onchange="updateTimeOptions()">
                    <option value="">Select a Date</option>
                    <%
                    for (Date date : weekDates) {
                        String formattedDate = dateFormat.format(date);
                        String dayName = dayFormat.format(date);
                    %>
                    <option value="<%=formattedDate%>"><%=dayName%> - <%=formattedDate%></option>
                    <%
                    }
                    %>
                </select>
            </div>

            <!-- Time Selection -->
            <div class="time-selection">
                <label for="timeSelect">Select Time:</label>
                <select id="timeSelect" onchange="updateSelectedDateTime()">
                    <option value="">Select Time</option>
                    <%
                    for (int hour = 8; hour <= 21; hour++) {
                        String timeLabel = (hour > 12 ? (hour - 12) : hour) + ":00" + (hour >= 12 ? "pm" : "am");
                        String timeValue = String.format("%02d:00", hour);
                    %>
                    <option value="<%=timeValue%>"><%=timeLabel%></option>
                    <%
                    }
                    %>
                </select>
            </div>

            <!-- Display selected date and time -->
            <div id="selectedDateTimeDisplay" class="selected-datetime">
                Selected Date and Time: <span id="dateTimeOutput">None selected</span>
            </div>
        </div>

        <!-- Rest of your form remains the same -->
        <div>
            <label for="addressDropdown">Address</label>
            <div class="dropdown-container">
                <select id="addressDropdown" name="address" required>
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
                <a href="<%=request.getContextPath()%>/View/Profile.jsp" class="add-address-btn" title="Add New Address">
                    <i class="fas fa-plus"></i>
                </a>
            </div>
        </div>

        <div>
            <label for="cleaner">Cleaner of Choice</label>
            <div class="dropdown-container">
                <select id="cleanerDropdown" name="cleaner" required>
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
        </div>

        <label for="specialRequests">Please enter any special requests:</label><br>
        <textarea id="specialRequests" name="specialRequests" rows="4" cols="50"
            placeholder="Type your special requests here..."></textarea>
        <br><br>
        <input type="submit" value="nextBtn">
    </form>

    <script>
    function updateSelectedDateTime() {
        const dateSelect = document.getElementById('dateSelect');
        const timeSelect = document.getElementById('timeSelect');
        const selectedDateTime = document.getElementById('selectedDateTime');
        const dateTimeOutput = document.getElementById('dateTimeOutput');

        if (dateSelect.value && timeSelect.value) {
            // Combine date and time into a single value
            const combinedDateTime = dateSelect.value + ' ' + timeSelect.value;
            selectedDateTime.value = combinedDateTime;
            dateTimeOutput.textContent = combinedDateTime;
        } else {
            selectedDateTime.value = '';
            dateTimeOutput.textContent = 'None selected';
        }
    }

    function updateTimeOptions() {
        // Reset time selection when date changes
        const timeSelect = document.getElementById('timeSelect');
        timeSelect.value = '';
        updateSelectedDateTime();
    }
    </script>
</body>
</html>