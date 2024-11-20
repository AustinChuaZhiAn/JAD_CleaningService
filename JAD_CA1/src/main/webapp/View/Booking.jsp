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
		
	    <label for="specialRequests">Please enter any special requests:</label><br>
    	<textarea id="specialRequests" name="specialRequests" rows="4" cols="50" placeholder="Type your special requests here..."></textarea><br><br>
		
		<input type="submit" value="nextBtn">
	</form>

	<script>
        function updateSelectedTimes(selectElement, dayIndex) {
            const selectedTime = selectElement.value;
            const timesOutput = document.getElementById('timesOutput');
            
            // Create or update the hidden input for this day
            let hiddenInput = document.getElementById('hiddenTime' + dayIndex);
            if (!hiddenInput) {
                hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.id = 'hiddenTime' + dayIndex;
                hiddenInput.name = 'selectedTime' + dayIndex;
                document.getElementById('cleaningScheduleForm').appendChild(hiddenInput);
            }
            
            // Convert 24-hour to 12-hour format
            const formatTime = (time) => {
                const [hour, minute] = time.split(':');
                const hourNum = parseInt(hour);
                const ampm = hourNum >= 12 ? 'PM' : 'AM';
                const formattedHour = hourNum % 12 || 12;
                return `${formattedHour}:00 ${ampm}`;
            };

            // Update display
            const dayNames = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
            if (selectedTime) {
                const displayText = `${dayNames[dayIndex]}: ${formatTime(selectedTime)}`;
                hiddenInput.value = selectedTime;
                
                // Check if this day already has an entry
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
                // Remove entry if time is unselected
                const existingEntry = document.querySelector(`#timesOutput div[data-day="${dayIndex}"]`);
                if (existingEntry) {
                    existingEntry.remove();
                }
            }
        }
    </script>
</body>
</html>