<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4CAF50;
            --primary-dark: #45a049;
            --primary-light: rgba(76, 175, 80, 0.1);
            --text-primary: #333;
            --text-secondary: #666;
            --background-gradient: linear-gradient(135deg, #e6ffe6 0%, #ccffcc 100%);
            --card-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
            --transition: all 0.3s ease;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            min-height: 100vh;
            font-family: 'Segoe UI', Arial, sans-serif;
            overflow-x: hidden;
        }

        .page-container {
            display: flex;
            min-height: 100vh;
            background: var(--background-gradient);
        }

        .left-section {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 2rem;
            position: relative;
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
        }

        .company-info {
            text-align: center;
            max-width: 500px;
            z-index: 1;
        }

        .company-logo {
            font-size: 3.5rem;
            color: var(--primary-color);
            margin-bottom: 1.5rem;
        }

        .company-info h2 {
            font-size: 2.5rem;
            color: var(--text-primary);
            margin-bottom: 1rem;
        }

        .company-info p {
            font-size: 1.2rem;
            color: var(--text-secondary);
            line-height: 1.6;
            margin-bottom: 2rem;
        }

        .feature-list {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.5rem;
            width: 100%;
            max-width: 600px;
        }

        .feature-item {
            background: white;
            padding: 1.5rem;
            border-radius: 12px;
            display: flex;
            align-items: center;
            gap: 1rem;
            transition: var(--transition);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        }

        .feature-item:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
        }

        .feature-item i {
            font-size: 1.5rem;
            color: var(--primary-color);
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: var(--primary-light);
            border-radius: 10px;
        }

        .feature-item span {
            font-weight: 500;
            color: var(--text-primary);
        }

        .right-section {
            width: 500px;
            background: white;
            display: flex;
            align-items: center;
            padding: 2rem;
            position: relative;
        }

        .login-card {
            width: 100%;
            max-width: 400px;
            margin: 0 auto;
        }

        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .logo-icon {
            font-size: 2.5rem;
            color: var(--primary-color);
            margin-bottom: 1rem;
        }

        .login-header h1 {
            color: var(--text-primary);
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }

        .login-header p {
            color: var(--text-secondary);
            font-size: 1rem;
        }

        .input-group {
            margin-bottom: 1.5rem;
        }

        .input-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: var(--text-primary);
            font-size: 0.9rem;
            font-weight: 500;
        }

        .input-with-icon {
            position: relative;
        }

        .input-with-icon input {
            width: 100%;
            padding: 1rem 2.5rem 1rem 1rem;
            border: 2px solid #eee;
            border-radius: 10px;
            font-size: 1rem;
            transition: var(--transition);
        }

        .input-with-icon input:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px var(--primary-light);
        }

        .input-icon {
            position: absolute;
            right: 1rem;
            top: 50%;
            transform: translateY(-50%);
            color: #999;
            cursor: pointer;
            transition: var(--transition);
        }

        .input-icon:hover {
            color: var(--primary-color);
        }

        .remember-forgot {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            cursor: pointer;
        }

        .remember-me input[type="checkbox"] {
            display: none;
        }

        .checkmark {
            width: 18px;
            height: 18px;
            border: 2px solid #ddd;
            border-radius: 4px;
            display: inline-block;
            position: relative;
            transition: var(--transition);
        }

        .remember-me input[type="checkbox"]:checked + .checkmark {
            background: var(--primary-color);
            border-color: var(--primary-color);
        }

        .remember-me input[type="checkbox"]:checked + .checkmark::after {
            content: '\2714';
            position: absolute;
            color: white;
            font-size: 12px;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .forgot-link {
            color: var(--primary-color);
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
        }

        .forgot-link:hover {
            text-decoration: underline;
        }

        .login-button {
            width: 100%;
            padding: 1rem;
            background: var(--primary-color);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: var(--transition);
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            margin-bottom: 1.5rem;
        }

        .login-button:hover {
            background: var(--primary-dark);
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
        }

        .divider {
            text-align: center;
            position: relative;
            margin: 1.5rem 0;
        }

        .divider::before,
        .divider::after {
            content: "";
            position: absolute;
            top: 50%;
            width: 45%;
            height: 1px;
            background: #eee;
        }

        .divider::before { left: 0; }
        .divider::after { right: 0; }

        .divider span {
            background: white;
            padding: 0 1rem;
            color: var(--text-secondary);
            font-size: 0.9rem;
        }

        .social-buttons {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
            margin-bottom: 1.5rem;
        }

        .social-button {
            padding: 0.8rem;
            border: 2px solid #eee;
            border-radius: 10px;
            background: white;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            transition: var(--transition);
            font-weight: 500;
        }

        .social-button:hover {
            background: #f8f8f8;
            border-color: #ddd;
            transform: translateY(-1px);
        }

        .social-button img {
            width: 24px;
            height: 24px;
        }

        .login-footer {
            text-align: center;
        }

        .login-footer a {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
        }

        .login-footer a:hover {
            text-decoration: underline;
        }

        @media (max-width: 1024px) {
            .page-container {
                flex-direction: column;
            }

            .right-section {
                width: 100%;
            }

            .feature-list {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 480px) {
            .left-section,
            .right-section {
                padding: 1rem;
            }

            .company-info h2 {
                font-size: 2rem;
            }
        }
    </style>
</head>
<body>
    <div class="page-container">
        <section class="left-section">
            <div class="company-info">
                <i class="fas fa-spray-can company-logo"></i>
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
            </div>
        </section>

        <section class="right-section">
            <div class="login-card">
                <div class="login-header">
                    <div class="logo-container">
                        <i class="fas fa-broom logo-icon"></i>
                    </div>
                    <h1>Welcome Back!</h1>
                    <p>Sign in to access your cleaning service dashboard</p>
                </div>

                <form action="login" method="POST" class="login-form">
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
                            <img src="/api/placeholder/24/24" alt="Google icon">
                            <span>Google</span>
                        </button>
                        <button type="button" class="social-button facebook">
                            <img src="/api/placeholder/24/24" alt="Facebook icon">
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
</body>
</html>