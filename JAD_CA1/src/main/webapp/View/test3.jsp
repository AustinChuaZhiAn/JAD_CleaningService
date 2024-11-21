<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Sign Up</title>
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
            background: var(--background-gradient);
            position: relative;
        }

        .dots-container {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            pointer-events: none;
        }

        .floating-dot {
            position: absolute;
            width: 4px;
            height: 4px;
            background: rgba(255, 255, 255, 0.4);
            border-radius: 50%;
            pointer-events: none;
            animation: floatDot var(--float-duration) ease-in-out infinite;
        }

        @keyframes floatDot {
            0%, 100% {
                transform: translate(0, 0);
            }
            50% {
                transform: translate(var(--float-x), var(--float-y));
            }
        }

        .page-container {
            display: flex;
            min-height: 100vh;
            position: relative;
            justify-content: flex-end;
        }

        .left-section {
            position: absolute;
            left: 0;
            top: 0;
            width: calc(100% - 500px);
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 2rem;
            z-index: 1;
        }

        .company-info {
            text-align: center;
            max-width: 500px;
            z-index: 1;
            background: rgba(255, 255, 255, 0.95);
            padding: 3rem;
            border-radius: 20px;
            box-shadow: 0 8px 32px rgba(76, 175, 80, 0.15);
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

        .right-section {
            width: 500px;
            background: white;
            min-height: 100vh;
            display: flex;
            align-items: center;
            padding: 2rem;
            position: relative;
            z-index: 2;
            box-shadow: -10px 0 30px rgba(0, 0, 0, 0.1);
        }
        .signup-card {
            width: 100%;
            max-width: 400px;
            margin: 0 auto;
        }

        .signup-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .logo-icon {
            font-size: 2.5rem;
            color: var(--primary-color);
            margin-bottom: 1rem;
        }

        .signup-header h1 {
            color: var(--text-primary);
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }

        .signup-header p {
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

        .signup-button {
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

        .signup-button:hover {
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

        .login-link {
            text-align: center;
            color: var(--text-secondary);
        }

        .login-link a {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        .floating-particle {
            position: absolute;
            width: 6px;
            height: 6px;
            background: rgba(76, 175, 80, 0.2);
            border-radius: 50%;
            pointer-events: none;
            filter: blur(1px);
            animation: float var(--float-duration) ease-in-out infinite;
        }

        @keyframes float {
            0%, 100% { transform: translate(0, 0); }
            25% { transform: translate(10px, -20px); }
            50% { transform: translate(20px, 0); }
            75% { transform: translate(-10px, 20px); }
        }

 @media (max-width: 1024px) {
            .page-container {
                flex-direction: column;
            }

            .left-section {
                position: relative;
                width: 100%;
                min-height: 50vh;
            }

            .right-section {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="dots-container"></div>
    <div class="page-container">
        <section class="left-section">
            <div class="company-info">
                <i class="fas fa-spray-can company-logo"></i>
                <h2>Clean and Clear</h2>
                <p>Join our community of satisfied customers</p>
            </div>
        </section>

        <section class="right-section">
            <div class="signup-card">
                <div class="signup-header">
                    <div class="logo-container">
                        <i class="fas fa-broom logo-icon"></i>
                    </div>
                    <h1>Create Account</h1>
                    <p>Sign up to get started with our services</p>
                </div>

                <form action="signup" method="POST" class="signup-form">
                    <div class="input-group">
                        <label for="username">Username</label>
                        <div class="input-with-icon">
                            <input type="text" id="username" name="username" required placeholder="Enter your username">
                            <i class="fas fa-user input-icon"></i>
                        </div>
                    </div>

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

                    <button type="submit" class="signup-button">
                        <span>Sign Up</span>
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

                    <div class="login-link">
                        <p>Already have an account? <a href="login.html">Log in</a></p>
                    </div>
                </form>
            </div>
        </section>
    </div>

 <script>
        function createDots() {
            const container = document.querySelector('.dots-container');
            const dotCount = 50; // Increased number of dots
            
            for(let i = 0; i < dotCount; i++) {
                const dot = document.createElement('div');
                dot.className = 'floating-dot';
                
                // Random initial position
                const posX = Math.random() * 100;
                const posY = Math.random() * 100;
                dot.style.left = `${posX}%`;
                dot.style.top = `${posY}%`;
                
                // Random size between 2px and 4px
                const size = Math.random() * 2 + 2;
                dot.style.width = `${size}px`;
                dot.style.height = `${size}px`;
                
                // Random opacity between 0.2 and 0.4
                dot.style.opacity = (Math.random() * 0.2 + 0.2).toString();
                
                // Random float animation values
                const floatX = (Math.random() - 0.5) * 30; // -15px to 15px
                const floatY = (Math.random() - 0.5) * 30;
                dot.style.setProperty('--float-x', `${floatX}px`);
                dot.style.setProperty('--float-y', `${floatY}px`);
                
                // Random animation duration between 10s and 20s
                const duration = Math.random() * 10 + 10;
                dot.style.setProperty('--float-duration', `${duration}s`);
                
                // Random animation delay
                dot.style.animationDelay = `${Math.random() * -20}s`;
                
                container.appendChild(dot);
            }
        }

        // Initialize dots on load
        window.addEventListener('load', createDots);
    </script>
</body>
</html>