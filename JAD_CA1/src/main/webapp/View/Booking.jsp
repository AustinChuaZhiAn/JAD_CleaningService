<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Clean And Clear | Scheduling</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Booking.css">
<body>
	<%@ include file="Header.jsp"%>

	<%!// Method declaration should be like this in JSP
	private Date[] getWeekDates(Calendar calendar) {
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
	// Using the method
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
	SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

	Date[] weekDates = getWeekDates(cal);
	%>

	<form id="cleaningScheduleForm">
		<div class="schedule-container">
			<div class="week-dates">
				<%
				for (Date date : weekDates) {
					cal.setTime(date);
				%>
				<span><%=dateFormat.format(date)%></span>
				<%
				}
				%>
			</div>

			<div class="dropdown-container">
				<%
				cal.setTime(new Date());
				for (int i = 0; i < weekDates.length; i++) {
					cal.setTime(weekDates[i]);
				%>
				<select name="time<%=i%>"
					onchange="updateSelectedTimes(this, <%=i%>)">
					<option value="">Select Time for
						<%=dayFormat.format(weekDates[i])%></option>
					<%
					for (int hour = 8; hour <= 21; hour++) {
						String timeLabel = (hour > 12 ? (hour - 12) : hour) + (hour >= 12 ? "pm" : "am");
					%>
					<option value="<%=hour%>:00"><%=timeLabel%></option>
					<%
					}
					%>
				</select>
				<%
				}
				%>
			</div>

			<div id="selectedTimesDisplay" class="selected-times">
				Selected Cleaning Times:
				<div id="timesOutput"></div>
			</div>
		</div>
		
		<label for="addressDropdown">Address</label>
                <div class="dropdown-container">
                    <select 
                        id="addressDropdown" 
                        name="address" 
                        required
                    >
                        <option value="">Select an Address</option>
                        
                        <% 
                        List<Address> addresses = (List<Address>) request.getAttribute("addresses");
                        if (addresses != null && !addresses.isEmpty()) {
                            for (Address address : addresses) { 
                        %>
                            <option value="<%= address.getId() %>">
                                <%= address.getStreetAddress() %>, <%= address.getCity() %>, <%= address.getState() %> <%= address.getZipCode() %>
                            </option>
                        <% 
                            } 
                        } else {
                        %>
                            <option value="" disabled>No addresses available</option>
                        <% } %>
                    </select>
                    
                    <a href="profile.jsp" class="add-address-btn" title="Add New Address">
                        <i class="fas fa-plus"></i>
                    </a>
                </div>
                
                <% 
                if (addresses == null || addresses.isEmpty()) { 
                %>
                    <div class="no-address-message">
                        You do not have an address. Please add an address.
                    </div>
                <% } %>
            </div>
		
	    <label for="specialRequests">Please enter any special requests:</label><br>
    	<textarea id="specialRequests" name="specialRequests" rows="4" cols="50" placeholder="Type your special requests here..."></textarea><br><br>
		
		<input type="submit" value="nextBtn">
	</form>

	<script>
	function updateSelectedTimes(selectElement, dayIndex) {
	    const selectedTime = selectElement.value;
	    const timesOutput = document.getElementById('timesOutput');
	    
	    // Disable the selected time in all other dropdowns
	    const allSelects = document.querySelectorAll('select[name^="time"]');
	    allSelects.forEach((select, index) => {
	        if (index !== dayIndex) {
	            // Remove previously disabled options
	            Array.from(select.options).forEach(option => {
	                if (option.disabled && option.value !== '') {
	                    option.disabled = false;
	                }
	                
	                // Disable the selected time in other dropdowns
	                if (option.value === selectedTime) {
	                    option.disabled = true;
	                }
	            });
	        }
	    });
	    
	    // Rest of the existing function remains the same...
	    let hiddenInput = document.getElementById('hiddenTime' + dayIndex);
	    if (!hiddenInput) {
	        hiddenInput = document.createElement('input');
	        hiddenInput.type = 'hidden';
	        hiddenInput.id = 'hiddenTime' + dayIndex;
	        hiddenInput.name = 'selectedTime' + dayIndex;
	        document.getElementById('cleaningScheduleForm').appendChild(hiddenInput);
	    }
	    
	    const formatTime = (time) => {
	        const [hour, minute] = time.split(':');
	        const hourNum = parseInt(hour);
	        const ampm = hourNum >= 12 ? 'PM' : 'AM';
	        const formattedHour = hourNum % 12 || 12;
	        return `${formattedHour}:00 ${ampm}`;
	    };

	    const dayNames = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
	    if (selectedTime) {
	        const displayText = `${dayNames[dayIndex]}: ${formatTime(selectedTime)}`;
	        hiddenInput.value = selectedTime;
	        
	        let existingEntry = document.querySelector(`#timesOutput div[data-day="${dayIndex}"]`);
	        if (existingEntry) {
	            existingEntry.textContent = displayText;
	        } else {
	            const newEntry = document.createElement('div');
	            newEntry.textContent = displayText;
	            newEntry.setAttribute('data-day', dayIndex);
	            timesOutput.appendChild(newEntry);
	        }
	    } else {
	        const existingEntry = document.querySelector(`#timesOutput div[data-day="${dayIndex}"]`);
	        if (existingEntry) {
	            existingEntry.remove();
	        }
	    }
	}
    </script>
</body>
</html>