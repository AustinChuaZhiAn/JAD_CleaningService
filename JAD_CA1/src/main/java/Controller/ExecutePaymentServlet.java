package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@WebServlet("/ExecutePaymentServlet")
public class ExecutePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID = "AXXyrijYc4BAfZB5tK1Ilk3iiw1OsQyHM0IcR3oimvfqCH-reutRIRlrxoaXh9GZRKXotlHzoU81vhKv";
	private static final String CLIENT_SECRET = "EO3gimrBbdKDWsajBIm8Oozd0hVTop5bHBouP0nCde5rKp_V6tB20MhBx1Cg5g9JQ8qY81GuEQrpxb3M";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Read JSON data from request body
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}

		// Parse JSON
		JsonObject jsonObject = JsonParser.parseString(buffer.toString()).getAsJsonObject();

		// Get parameters from JSON
		String paymentId = jsonObject.get("paymentId").getAsString();
		String payerId = jsonObject.get("payerId").getAsString();

		// Validate parameters
		if (paymentId == null || payerId == null) {
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"Missing required parameters\"}");
			return;
		}

		try {
			APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(payerId);

			Payment payment = Payment.get(apiContext, paymentId);
			Payment executedPayment = payment.execute(apiContext, paymentExecution);

			response.setContentType("application/json");
			response.getWriter()
					.write("{\"status\":\"success\"," + "\"payer\":{\"name\":{\"given_name\":\""
							+ executedPayment.getPayer().getPayerInfo().getFirstName() + "\"}},"
							+ "\"redirectForm\":\"<form id='redirectForm' method='POST' action='"
							+ request.getContextPath() + "/BookingController'>"
							+ "<input type='hidden' name='action' value='AddBookings'/>" + "</form>\"" + "}");
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}