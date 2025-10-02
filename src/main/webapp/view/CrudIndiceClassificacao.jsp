<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Crud Indice classificação - Área restrita</title>
</head>
<body>

<form action="InserirIndiceClassificacao" method="get">
  <button type="submit">Inserir indice classificação</button>
</form>

<table border="1">
  <thead>
  <tr>
    <th>Preocupação</th>
    <th>ID</th>
    <th>Porcentagem mínima</th>
    <th>Porcentagem máxima</th>
    <th>Recomendação</th>
    <th>Ações</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="indiceClassificacao" items="${indicesClassificacao}">
    <tr>
      <td>${indiceClassificacao.preocupacao}</td>
      <td>${indiceClassificacao.id}</td>
      <td>${indiceClassificacao.porcentagemMinima}</td>
      <td>${indiceClassificacao.porcentagemMaxima}</td>
      <td>${indiceClassificacao.recomendacao}</td>
      <td>
        <div style="display: flex">
          <form action="AlterarIndiceClassificacao" method="post">
            <input type="hidden" name="id" value="${indiceClassificacao.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Alterar</button>
          </form>
          <form action="DeletarIndiceClassificacao" method="post">
            <input type="hidden" name="id" value="${indiceClassificacao.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Deletar</button>
          </form>
        </div>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<%if(request.getAttribute("erro") != null){%>
<p>${erro}</p>
<%}%>

</body>
</html>
