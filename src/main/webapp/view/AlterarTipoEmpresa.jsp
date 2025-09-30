<%@ page import="org.example.model.TipoEmpresa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  TipoEmpresa tipoEmpresa = (TipoEmpresa) request.getAttribute("tipoEmpresa");
  String dataFormatada = (String) request.getAttribute("dataFormatada");
  if (dataFormatada == null) dataFormatada = "";

  String nomeValue = (tipoEmpresa != null && tipoEmpresa.getNome() != null) ? tipoEmpresa.getNome() : "";
  String statusLabel = (tipoEmpresa != null && tipoEmpresa.getStatus() == 'a') ? "Ativo" : "Inativo";
  String descricaoValue = (tipoEmpresa != null && tipoEmpresa.getDescricao() != null) ? tipoEmpresa.getDescricao() : "";
  int idValue = (tipoEmpresa != null) ? tipoEmpresa.getId() : 0;
%>
<html>
<head>
  <title>Alterar Tipo Empresa</title>
</head>
<body>
<form action="AlterarTipoEmpresa" method="post">
  <input type="hidden" name="id" value="<%= idValue %>">
  <input type="hidden" name="action" value="1">

  <input type="text" name="nome" value="<%= nomeValue %>">
  <br>

  <input type="text" name="status" value="<%= statusLabel %>">
  <br>

  <input type="date" name="ultima_atualizacao" value="<%= dataFormatada %>">
  <br>

  <input type="text" name="descricao" value="<%= descricaoValue %>">
  <br>

  <button type="submit">Atualizar</button>
</form>

<% if (request.getAttribute("erro") != null) { %>
<p><%= request.getAttribute("erro") %></p>
<% } %>
</body>
</html>
