<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clean and Clear - Sign Up</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Using the same root variables as login page */
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

        /* Left Section - Similar to login page */
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

        /* Right Section - Sign Up Form */
        .right-section {
            width: 500px;
            background: white;
            display: flex;
            align-items: center;
            padding: 2rem;
            position: relative;
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

        @media (max-width: 1024px) {
            .page-container {
                flex-direction: column;
            }

            .right-section {
                width: 100%;
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
        
        .sparkle {
            position: absolute;
            pointer-events: none;
            background: radial-gradient(circle, #fff 0%, transparent 70%);
            border-radius: 50%;
            opacity: 0;
            z-index: 1000;
        }

        @keyframes sparkle {
            0% { transform: scale(0); opacity: 0; }
            50% { opacity: 0.5; }
            100% { transform: scale(3); opacity: 0; }
        }

        .ripple {
            position: absolute;
            border: 2px solid var(--primary-color);
            border-radius: 50%;
            animation: ripple 1s linear infinite;
            pointer-events: none;
            z-index: 1000;
        }

        @keyframes ripple {
            0% { transform: scale(0.5); opacity: 0.5; }
            100% { transform: scale(2); opacity: 0; }
        }

        .floating-particle {
            position: absolute;
            width: 6px;
            height: 6px;
            background: var(--primary-color);
            border-radius: 50%;
            pointer-events: none;
            opacity: 0.3;
            animation: float 3s ease-in-out infinite;
        }

        @keyframes float {
            0%, 100% { transform: translateY(0) rotate(0deg); }
            50% { transform: translateY(-20px) rotate(180deg); }
        }

        .glow-effect {
            position: absolute;
            width: 100px;
            height: 100px;
            background: radial-gradient(circle, rgba(76, 175, 80, 0.2) 0%, transparent 70%);
            border-radius: 50%;
            pointer-events: none;
            transition: all 0.3s ease;
            z-index: 999;
        }

        .pulse {
            position: absolute;
            width: 10px;
            height: 10px;
            background: var(--primary-color);
            border-radius: 50%;
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% { transform: scale(1); opacity: 1; }
            100% { transform: scale(20); opacity: 0; }
        }

        /* Add hover effects to inputs */
        .input-with-icon input:hover {
            border-color: var(--primary-light);
            box-shadow: 0 0 10px rgba(76, 175, 80, 0.1);
        }

        /* Add hover effects to buttons */
        .social-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.15);
        }
    </style>
</head>
<body>
    <div class="page-container">
        <div class="floating-leaves">
            <div class="leaf" style="left: 10%; animation-delay: 0s;"></div>
            <div class="leaf" style="left: 30%; animation-delay: 2s;"></div>
            <div class="leaf" style="left: 50%; animation-delay: 4s;"></div>
            <div class="leaf" style="left: 70%; animation-delay: 6s;"></div>
            <div class="leaf" style="left: 90%; animation-delay: 8s;"></div>
        </div>
        
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
        function togglePassword(icon) {
            const passwordInput = document.getElementById('password');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        } function togglePassword(icon) {
            const passwordInput = document.getElementById('password');
            
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                passwordInput.type = 'password';
                icon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        }

        // New Effects
        function createSparkles(e) {
            const sparkle = document.createElement('div');
            sparkle.className = 'sparkle';
            sparkle.style.left = e.pageX + 'px';
            sparkle.style.top = e.pageY + 'px';
            sparkle.style.width = '10px';
            sparkle.style.height = '10px';
            document.body.appendChild(sparkle);
            sparkle.style.animation = 'sparkle 1s ease-out forwards';
            setTimeout(() => sparkle.remove(), 1000);
        }

        function createRipple(x, y) {
            const ripple = document.createElement('div');
            ripple.className = 'ripple';
            ripple.style.left = x + 'px';
            ripple.style.top = y + 'px';
            document.body.appendChild(ripple);
            setTimeout(() => ripple.remove(), 1000);
        }

        function createFloatingParticles() {
            const container = document.querySelector('.left-section');
            for(let i = 0; i < 15; i++) {
                const particle = document.createElement('div');
                particle.className = 'floating-particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.top = Math.random() * 100 + '%';
                particle.style.animationDelay = Math.random() * 2 + 's';
                container.appendChild(particle);
            }
        }

        function createGlowEffect(e) {
            const glow = document.querySelector('.glow-effect') || document.createElement('div');
            glow.className = 'glow-effect';
            document.body.appendChild(glow);
            
            const updateGlowPosition = (e) => {
                glow.style.left = e.pageX - 50 + 'px';
                glow.style.top = e.pageY - 50 + 'px';
            };
            
            updateGlowPosition(e);
        }

        function createPulse() {
            const inputs = document.querySelectorAll('input');
            inputs.forEach(input => {
                input.addEventListener('focus', (e) => {
                    const pulse = document.createElement('div');
                    pulse.className = 'pulse';
                    pulse.style.left = e.target.offsetLeft + 'px';
                    pulse.style.top = e.target.offsetTop + 'px';
                    document.querySelector('.signup-card').appendChild(pulse);
                    setTimeout(() => pulse.remove(), 2000);
                });
            });
        }

        // Event Listeners
        document.addEventListener('mousemove', (e) => {
            createSparkles(e);
            createGlowEffect(e);
        });
        document.addEventListener('click', (e) => createRipple(e.pageX, e.pageY));
        window.addEventListener('load', () => {
            createFloatingParticles();
            createPulse();
        });
    </script>
</body>
</html>