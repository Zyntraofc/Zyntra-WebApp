<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>aion/login</title>
    <<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<main></main>

<section>
    <div id="container">

        <!-- Opções (Entrar / Cadastrar) -->
        <div id="options">
            <form>
                <input type="radio" id="Entrar" name="opcao" value="entrar" checked>
                <label for="Entrar">Entrar</label>
                
            </form>
        </div>

        <!-- Formulário de Login -->
        <div class="conteudo a">
            <h1>Acesse sua conta!</h1>
            <form action="<%= request.getContextPath() %>/LoginAdministrador" method="post" id="e-senha">
                <div>
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" required placeholder="Digite aqui seu e-mail">
                </div>

                <div>
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" required placeholder="Digite aqui sua senha">
                </div>

                <% if (request.getAttribute("erroLogin") != null) { %>
                <p class="erro-login"><%= request.getAttribute("erroLogin") %></p>
                <% } %>

                <div class="botao">
                    <button type="submit">Fazer Login</button>
                </div>
            </form>
        </div>


    </div>
</section>
</body>
</html>
