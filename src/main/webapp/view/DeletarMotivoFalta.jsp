<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>Tem certeza disso?</h1>
<form action="DeletarMotivoFalta" method="post">
  <input type="hidden" name="action" value="1">
  <input type="hidden" name="id" value="${motivo.getId()}">
  <button type="submit">Sim</button>
</form>
<form action="DeletarMotivoFalta" method="post">
  <input type="hidden" name="id" value="${motivo.getId()}">
  <input type="hidden" name="action" value="2">
  <button type="submit">Não</button>
</form>
</body>
</html>