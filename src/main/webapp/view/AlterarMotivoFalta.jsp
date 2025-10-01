<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="AlterarMotivoFalta" method="post">
  <input type="hidden" name="action" value="1">

  <input type="hidden" name="id" value="${motivo.getId()}">

  <input type="text" name="motivo" value="${motivo.getMotivo()}">

  <button type="submit">Alterar</button>
</form>
</body>
</html>