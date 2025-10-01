
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>Tem certeza disso?</h1>
<form action="DeletarTipoEmpresa" method="post">
  <input type="hidden" name="action" value="1">
  <input type="hidden" name="id" value="${tipoEmpresa.getId()}">
  <button type="submit">Sim</button>
</form>
<form action="DeletarTipoEmpresa" method="post">
  <input type="hidden" name="id" value="${tipoEmpresa.getId()}">
  <input type="hidden" name="action" value="2">
  <button type="submit">Não</button>
</form>

<%
  if(request.getAttribute("erro") != null){
%>
<p>${erro}</p>
<%
  }
%>
</body>
</html>
