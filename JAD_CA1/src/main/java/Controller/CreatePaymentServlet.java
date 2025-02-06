package Controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.io.*;

/**
 * Servlet implementation class CreatePaymentServlet
 */
@WebServlet("/CreatePaymentServlet")
public class CreatePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String CLIENT_ID = "AXXyrijYc4BAfZB5tK1Ilk3iiw1OsQyHM0IcR3oimvfqCH-reutRIRlrxoaXh9GZRKXotlHzoU81vhKv";
    private static final String CLIENT_SECRET = "EO3gimrBbdKDWsajBIm8Oozd0hVTop5bHBouP0nCde5rKp_V6tB20MhBx1Cg5g9JQ8qY81GuEQrpxb3M";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreatePaymentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String requestData = jsonBuilder.toString();

            // Extract amount from request JSON
            double totalPrice = extractAmountFromJson(requestData);

            if (totalPrice <= 0) {
                response.getWriter().write("{\"error\": \"Invalid amount\"}");
                return;
            }
			createPayment(request, response, totalPrice);
		} catch (SQLException ex) {
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
    private double extractAmountFromJson(String json) {
        try {
            json = json.replaceAll("[^0-9.]", ""); // Remove non-numeric characters
            return Double.parseDouble(json);
        } catch (Exception e) {
            return -1;
        }
    }

	private void createPayment(HttpServletRequest request, HttpServletResponse response, double amountValue)
			throws SQLException, ServletException, IOException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");

	    Amount amount = new Amount();
	    amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", amountValue)); // Ensure 2 decimal places

	    Transaction transaction = new Transaction();
	    transaction.setAmount(amount);
	    transaction.setDescription("Test Payment");

	    List<Transaction> transactions = new ArrayList<>();
	    transactions.add(transaction);

	    Payer payer = new Payer();
	    payer.setPaymentMethod("paypal");

	    Payment payment = new Payment();
	    payment.setIntent("sale");
	    payment.setPayer(payer);
	    payment.setTransactions(transactions);

	    // Build the full URLs
	    String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    System.out.println(baseURL);
	    RedirectUrls redirectUrls = new RedirectUrls();
	    redirectUrls.setCancelUrl(baseURL + "/View/AddToCart.jsp");
	    redirectUrls.setReturnUrl(baseURL + "/ExecutePaymentServlet");
	    
	    payment.setRedirectUrls(redirectUrls);

	    try {
	        Payment createdPayment = payment.create(apiContext);
	        
	        // Find the approval URL
	        String approvalUrl = createdPayment.getLinks().stream()
	            .filter(link -> link.getRel().equals("approval_url"))
	            .findFirst()
	            .orElseThrow(() -> new PayPalRESTException("No approval URL found"))
	            .getHref();
	            
	        // Extract the EC token from the approval URL
	        String ecToken = approvalUrl.substring(approvalUrl.lastIndexOf("EC-"));
	        
	        response.setContentType("application/json");
	        String jsonResponse = "{\"id\":\"" + ecToken + "\"}";
	        response.getWriter().write(jsonResponse);
	    } catch (PayPalRESTException e) {
	        System.out.println("PayPal error: " + e.getMessage());
	        e.printStackTrace();
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
	    }
	}

}

