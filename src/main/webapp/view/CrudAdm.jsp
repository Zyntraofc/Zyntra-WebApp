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
  </tr>
  </thead>
  <tbody>
  <c:forEach var="empresa" items="${administradores}">
    <tr>
      <td>${administradores.id}</td>
      <td>${administradores.email}</td>
      <td>${administradores.hashSenha}</td>
      <td>
        <div style="display: flex">
          <form action="AlterarAdm" method="post">
            <input type="hidden" name="id" value="${adm.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Alterar</button>
          </form>
          <form action="DeletarEmpresa" method="post">
            <input type="hidden" name="id" value="${adm.id}">
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
