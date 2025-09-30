<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Crud Motivo Falta - Área restrita</title>
</head>
<body>

<form action="InserirMotivoFalta" method="get">
    <button type="submit">Inserir motivo</button>
</form>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Motivo</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="motivo" items="${motivos}">
        <tr>
            <td>${motivo.id}</td>
            <td>${motivo.motivo}</td>

            <td>
                <div style="display: flex">
                    <form action="AlterarMotivoFalta" method="post">
                        <input type="hidden" name="id" value="${motivo.id}">
                        <input type="hidden" name="action" value="0">
                        <button type="submit">Alterar</button>
                    </form>
                    <form action="DeletarMotivoFalta" method="post">
                        <input type="hidden" name="id" value="${motivo.id}">
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
