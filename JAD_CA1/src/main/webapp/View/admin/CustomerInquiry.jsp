<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page import="Model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Inquiry</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">
    <%@ include file="AdminHeader.jsp" %>

    <div class="container my-5">
        <h2 class="mb-4">Customer Inquiry</h2>
        <input type="text" id="search" class="form-control mb-3" placeholder="Search by Email or Contact">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Contact</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                <% 
                List<UserAccount> userAccounts = (List<UserAccount>) request.getAttribute("listOfUsers");
                List<UserDetails> userDetails = (List<UserDetails>) request.getAttribute("listOfUserDetails");
                List<List<Address>> addressesList = (List<List<Address>>) request.getAttribute("listOfAddresses");
                List<AddressType> addressTypes = (List<AddressType>) request.getAttribute("listOfAddressTypes");
                
                for (int i = 0; i < userAccounts.size(); i++) {
                    UserAccount account = userAccounts.get(i);
                    UserDetails details = userDetails.get(i);
                    List<Address> addresses = addressesList.get(i);
                %>
                    <tr class="user-row" data-user-id="<%= details.getUser_details_id() %>">
                        <td><%= account.getUsername() %></td>
                        <td><%= details.getPhone_number() %></td>
                        <td><%= details.getEmail() %></td>
                    </tr>
                    <tr class="address-row d-none" id="addresses-<%= details.getUser_details_id() %>">
                        <td colspan="3">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Block</th>
                                        <th>Street</th>
                                        <th>Unit</th>
                                        <th>Building</th>
                                        <th>Postal Code</th>
                                        <th>Address Type</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                    for (Address address : addresses) {
                                        String addressTypeName = "";
                                        for (AddressType addressType : addressTypes) {
                                            if (addressType.getAddress_type_id() == address.getAddress_type_id()) {
                                                addressTypeName = addressType.getAddress_type();
                                                break;
                                            }
                                        }
                                    %>
                                        <tr>
                                            <td><%= address.getBlock_number() %></td>
                                            <td><%= address.getStreet_name() %></td>
                                            <td><%= address.getUnit_number() %></td>
                                            <td><%= address.getBuilding_name() %></td>
                                            <td><%= address.getPostal_code() %></td>
                                            <td><%= addressTypeName %></td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <script>
        document.querySelectorAll('.user-row').forEach(row => {
            row.addEventListener('click', function() {
                let userId = this.getAttribute('data-user-id');
                let addressRow = document.getElementById('addresses-' + userId);
                addressRow.classList.toggle('d-none');
            });
        });

        document.getElementById('search').addEventListener('input', function() {
            let searchTerm = this.value.toLowerCase();
            document.querySelectorAll('.user-row').forEach(row => {
                let email = row.children[2].innerText.toLowerCase();
                let contact = row.children[1].innerText.toLowerCase();
                if (email.includes(searchTerm) || contact.includes(searchTerm)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    </script>
</body>
</html>
