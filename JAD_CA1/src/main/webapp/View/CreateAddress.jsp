<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.*, java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Address</title>
    <!-- Bootstrap 5.3 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Header.css">
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/Footer.css">
</head>
<body>
    <% 
    String username = (String) session.getAttribute("username");
    if (username == null) {
        String contextPath = request.getContextPath();
        String loginPage = contextPath + "/View/Login.jsp";
        response.sendRedirect(loginPage);
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    List<AddressType> addressTypeList = (List) request.getAttribute("addressType");
    %>

    <%@ include file="Header.jsp"%>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <!-- Error Alert -->
                <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
                    <div class="alert alert-danger alert-dismissible fade show mb-4" role="alert">
                        <%= errorMessage %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                <% } %>

                <div class="card shadow">
                    <div class="card-body">
                        <h3 class="card-title text-center mb-4">Add New Address</h3>
                        
                        <form action="<%=request.getContextPath() %>/userController?action=AddAddress" method="post" class="needs-validation" novalidate>
                            <div class="mb-3 row">
                                <label for="addressType" class="col-sm-3 col-form-label">Address Type:</label>
                                <div class="col-sm-9">
                                    <select class="form-select" id="addressType" name="address_type_id" required>
                                        <option value="">Select Address Type</option>
                                        <% for (AddressType type : addressTypeList) { %>
                                            <option value="<%=type.getAddress_type_id()%>"><%=type.getAddress_type()%></option>
                                        <% } %>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please select an address type.
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <label for="postalCode" class="col-sm-3 col-form-label">Postal Code:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="postalCode" name="postal_code" required>
                                    <div class="invalid-feedback">
                                        Please enter a postal code.
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <label for="blockNumber" class="col-sm-3 col-form-label">Block Number:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="blockNumber" name="block_number" required>
                                    <div class="invalid-feedback">
                                        Please enter a block number.
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <label for="streetName" class="col-sm-3 col-form-label">Street Name:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="streetName" name="street_name" required>
                                    <div class="invalid-feedback">
                                        Please enter a street name.
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <label for="unitNumber" class="col-sm-3 col-form-label">Unit Number:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="unitNumber" name="unit_number" required>
                                    <div class="invalid-feedback">
                                        Please enter a unit number.
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3 row">
                                <label for="buildingName" class="col-sm-3 col-form-label">Building Name:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="buildingName" name="building_name">
                                </div>
                            </div>

                            <div class="text-end mt-4">
                                <button type="submit" class="btn btn-primary">Save Address</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="Footer.jsp"%>

    <!-- Bootstrap 5.3 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

    <!-- Form validation script -->
    <script>
    // Enable Bootstrap form validation
    (function () {
        'use strict'
        
        // Fetch all forms we want to apply validation styles to
        var forms = document.querySelectorAll('.needs-validation')
        
        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    
                    form.classList.add('was-validated')
                }, false)
            })
    })()
    </script>
</body>
</html>