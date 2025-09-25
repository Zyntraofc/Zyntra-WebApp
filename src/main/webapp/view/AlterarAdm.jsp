<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="AlterarAdm" method="post">
  <input type="hidden" name="action" value="1">

  <input type="hidden" name="id" value="${administrador.getId()}">

  <input type="email" name="idTipoEmpresa" value="${administrador.getEmail}">

  <button type="submit">Alterar</button>
</form>

</body>
</html>
