<%@ page import="org.example.model.TipoEmpresa" %>
<%@ page import="org.example.model.IndiceClassificacao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  IndiceClassificacao indiceClassificacao = (IndiceClassificacao) request.getAttribute("indiceClassificacao");
  int id = indiceClassificacao.getId();
  String preocupacao = indiceClassificacao.getPreocupacao() != null ? indiceClassificacao.getPreocupacao() : "";
  double porcentagemMinima = indiceClassificacao.getPorcentagemMinima();
  double porcentagemMaxima = indiceClassificacao.getPorcentagemMaxima();
  String recomendacao = indiceClassificacao.getRecomendacao() != null ? indiceClassificacao.getRecomendacao() : "";

%>
<html>
<head>
  <title>Alterar Tipo Empresa</title>
</head>
<body>
<form action="AlterarTipoEmpresa" method="post">
  <input type="hidden" name="id" value="<%= id %>">
  <input type="hidden" name="action" value="1">

  <input type="text" name="preocupacao" value="<%= preocupacao %>">
  <br>

  <input type="number" name="porcentagemMinima" value="<%= porcentagemMinima %>">
  <br>

  <input type="number" name="porcentagemMaxima" value="<%= porcentagemMaxima %>">
  <br>

  <input type="text" name="recomendacao" value="<%= recomendacao %>">
  <br>

  <button type="submit">Atualizar</button>
</form>

<% if (request.getAttribute("erro") != null) { %>
<p><%= request.getAttribute("erro") %></p>
<% } %>
</body>
</html>

