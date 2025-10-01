<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Crud Adm - Área restrita</title>
</head>
<body>

<form action="InserirAdm" method="get">
  <button type="submit">Inserir adm</button>
</form>

<table border="1">
  <thead>
  <tr>

    <th>ID</th>
    <th>Email</th>
    <th>Hash senha</th>
    <th>Ações</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="administrador" items="${administradores}">
    <tr>
      <td>${administrador.id}</td>
      <td>${administrador.email}</td>
      <td>${administrador.hashSenha}</td>
      <td>
        <div style="display: flex">
          <form action="AlterarAdm" method="post">
            <input type="hidden" name="id" value="${administrador.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Alterar</button>
          </form>
          <form action="DeletarAdm" method="post">
            <input type="hidden" name="id" value="${administrador.id}">
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
