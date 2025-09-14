<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>aion - Login</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<!-- Wrapper principal -->
<div class="login-wrapper">
    <!-- Seção da logo -->
    <div class="logo-section">
        <div class="logo-content">
            <div class="logo-image">
                <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Logo aion">
            </div>
            <div class="logo-text">aion</div>
            <div class="logo-tagline">Entenda o tempo e transforme a presença das suas equipes!</div>
        </div>
    </div>

    <!-- Seção do login -->
    <div class="login-section">
        <div class="login-container">
            <div class="login-header">
                <h1>Acesse sua conta</h1>
                <p>Entre com suas credenciais</p>
            </div>



            <form action="${pageContext.request.contextPath}/LoginAdministrador" method="post" class="form-fields" name="loginForm">
                <div class="input-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" required placeholder="Digite aqui seu e-mail">
                </div>

                <div class="input-group">
                    <label for="password">Senha</label>
                    <input type="password" id="password" name="senha" required placeholder="Digite aqui sua senha">
                </div>

                <% if (request.getAttribute("erroLogin") != null) { %>
                <p class="erro-login"><%= request.getAttribute("erroLogin") %></p>
                <% } %>

                <div class="submit-button">
                    <button type="submit">Fazer Login</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
 