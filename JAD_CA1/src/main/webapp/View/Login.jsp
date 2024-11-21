<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Login.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Footer.css">
</head>
<body>
    <div class="page-container">
        <section class="left-section">
            <div class="company-info">
                <a href="home.jsp" class="company-logo-link">
    <i class="fas fa-spray-can company-logo"></i>
</a>
                <h2>Clean and Clear</h2>
                <p>Providing professional cleaning services with dedication and excellence</p>
                
                <div class="feature-list">
                    <div class="feature-item">
                        <i class="fas fa-clock"></i>
                        <span>24/7 Service Available</span>
                    </div>
                    <div class="feature-item">
                        <i class="fas fa-star"></i>
                        <span>Professional Staff</span>
                    </div>
                    <div class="feature-item">
                        <i class="fas fa-shield-alt"></i>
                        <span>Fully Insured & Bonded</span>
                    </div>
                    <div class="feature-item">
                        <i class="fas fa-hand-sparkles"></i>
                        <span>Satisfaction Guaranteed</span>
                    </div>
                </div>
                <div class="guest-access">
    <a href="Home.jsp" class="guest-button">
        <i class="fas fa-user-clock"></i>
        <span>View Page</span>
    </a>
</div>
            </div>
        </section>

        <section class="right-section">
            <div class="login-card">
                <div class="login-header">
                    <div class="logo-container">
                        <i class="fas fa-broom logo-icon"></i>
                    </div>
                    <h1>Welcome Back!</h1>
                    <p>Log in to get access to your cleaning service resources.</p>
                </div>

                <form action="verifyAccount" method="POST" class="login-form">
                    <div class="input-group">
                        <label for="email">Email Address</label>
                        <div class="input-with-icon">
                            <input type="email" id="email" name="email" required placeholder="Enter your email">
                            <i class="fas fa-envelope input-icon"></i>
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="password">Password</label>
                        <div class="input-with-icon">
                            <input type="password" id="password" name="password" required placeholder="Enter your password">
                            <i class="fas fa-eye input-icon" onclick="togglePassword(this)"></i>
                        </div>
                    </div>

                    <div class="remember-forgot">
                        <label class="remember-me">
                            <input type="checkbox" name="remember">
                            <span class="checkmark"></span>
                            Remember me
                        </label>
                        <a href="#" class="forgot-link">Forgot password?</a>
                    </div>

                    <button type="submit" class="login-button">
                        <span>Sign In</span>
                        <i class="fas fa-arrow-right"></i>
                    </button>

                    <div class="divider">
                        <span>Or continue with</span>
                    </div>

                    <div class="social-buttons">
                        <button type="button" class="social-button google">
                            <img src="https://imgur.com/VOjbIxc.png" alt="Google icon">
                            <span>Google</span>
                        </button>
                        <button type="button" class="social-button facebook">
                            <img src="https://imgur.com/6FC0uL6.png" alt="Facebook icon">
                            <span>Facebook</span>
                        </button>
                    </div>

                    <div class="login-footer">
                        <p>Don't have an account? <a href="#">Sign up</a></p>
                    </div>
                </form>
            </div>
        </section>
    </div>

    <script>
        function togglePassword(icon) {
            const passwordInput = document.getElementById('password');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        }
    </script>
    
    		<%@ include file="Footer.jsp"%>
</body>

</html>