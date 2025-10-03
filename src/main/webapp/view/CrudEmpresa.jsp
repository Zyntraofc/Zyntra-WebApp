<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Crud Empresa - Área restrita</title>
</head>
<body>

<form action="InserirEmpresa" method="get">
  <button type="submit">Inserir empresa</button>
</form>

<table border="1">
  <thead>
  <tr>
    <th>Nome</th>
    <th>ID</th>
    <th>CNPJ</th>
    <th>Email</th>
    <th>Ações</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="empresa" items="${empresas}">
    <tr>
      <td>${empresa.nome}</td>
      <td>${empresa.id}</td>
      <td>${empresa.cnpj}</td>
      <td>${empresa.email}</td>
      <td>
        <div style="display: flex">
          <form action="AlterarEmpresa" method="post">
            <input type="hidden" name="id" value="${empresa.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Alterar</button>
          </form>
          <form action="DeletarEmpresa" method="post">
            <input type="hidden" name="id" value="${empresa.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Deletar</button>
          </form>
        </div>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<%
  if(request.getAttribute("erro") != null){
%>
<p><%=request.getAttribute("erro")%></p>
<%
  }
%>
</body>
</html>
