<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="AlterarAdm" method="post">
  <input type="hidden" name="action" value="1">

  <input type="hidden" name="id" value="${administrador.getId()}">

  <input type="email" name="email" value="${administrador.getEmail()}">

  <button type="submit">Alterar</button>
</form>

<form action="AlterarSenha" method="post">
  <input type="hidden" name="id" value="${administrador.getId()}">
  <input type="hidden" name="action" value="0">
  <button type="submit">Alterar senha</button>

  <% if (request.getAttribute("erroSenha") != null) { %>
  <p class="erro-senha"><%= request.getAttribute("erroSenha") %></p>
  <% } %>
</form>

</body>
</html>
