<%@ page import="org.example.model.TipoEmpresa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  TipoEmpresa tipoEmpresa = (TipoEmpresa) request.getAttribute("tipoEmpresa");
  String dataFormatada = (String) request.getAttribute("dataFormatada");
  if (dataFormatada == null) dataFormatada = "";

  String nome = (tipoEmpresa != null && tipoEmpresa.getNome() != null) ? tipoEmpresa.getNome() : "";
  String status = (tipoEmpresa != null && tipoEmpresa.getStatus() == 'a') ? "Ativo" : "Inativo";
  String descricao = (tipoEmpresa != null && tipoEmpresa.getDescricao() != null) ? tipoEmpresa.getDescricao() : "";
  int id = (tipoEmpresa != null) ? tipoEmpresa.getId() : 0;
%>
<html>
<head>
  <title>Alterar Tipo Empresa</title>
</head>
<body>
<form action="AlterarTipoEmpresa" method="post">
  <input type="hidden" name="id" value="<%= id %>">
  <input type="hidden" name="action" value="1">

  <input type="text" name="nome" value="<%= nome %>">
  <br>

  <input type="text" name="status" value="<%= status %>">
  <br>

  <input type="date" name="ultima_atualizacao" value="<%= dataFormatada %>">
  <br>

  <input type="text" name="descricao" value="<%= descricao %>">
  <br>

  <button type="submit">Atualizar</button>
</form>

<% if (request.getAttribute("erro") != null) { %>
<p><%= request.getAttribute("erro") %></p>
<% } %>
</body>
</html>
