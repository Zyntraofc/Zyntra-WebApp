<%--
  Created by IntelliJ IDEA.
  User: erickbarbosa-ieg
  Date: 25/09/2025
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
    <form action="AlterarSenha" method="post" class="form-fields" name="loginForm">
        <input type="hidden" name="id" value="${administrador.id}">
    <div class="input-group">
        <label for="passwordAtual">Senha Atual</label>
        <input type="password" id="passwordAtual" name="senhaAtual" required placeholder="Confirme aqui a senha atual" value="${not empty senhaDigitada ? senhaDigitada : param.senha}">
    </div>
        <div class="input-group">
            <label for="passwordNova">Nova Senha</label>
            <input type="password" id="passwordNova" name="senhaNova" required placeholder="Digite a nova senha" value="${not empty senhaDigitada ? senhaDigitada : param.senha}">
        </div>


    <div class="submit-button">
        <input type="hidden" name="action" value="1">
        <button type="submit">ALterar</button>
    </div>
</form>
</body>
</html>
