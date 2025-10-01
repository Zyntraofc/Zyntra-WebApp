<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Crud Status Aprovação - Área restrita</title>
</head>
<body>

<form action="InserirStatusAprovacao" method="get">
    <button type="submit">Inserir status</button>
</form>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Status</th>
        <th>Motivo de Rejeição</th>
        <th>Data de Solicitação</th>
        <th>Data de Aprovação</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="status" items="${status}">
        <tr>
            <td>${status.id}</td>
            <td>${String.valueOf(status.status).equals("a") ? "Aprovado" : String.valueOf(status.status).equals("r") ? "Recusado" : "Pendente"}</td>
            <td>${status.motivoRejeicao}</td>
            <td>${status.dataSolicitacao}</td>
            <td>${status.dataAprovacao}</td>


            <td>
                <div style="display: flex">
                    <form action="AlterarStatusAprovacao" method="post">
                        <input type="hidden" name="id" value="${status.id}">
                        <input type="hidden" name="action" value="0">
                        <button type="submit">Alterar</button>
                    </form>
                    <form action="DeletarStatusAprovacao" method="post">
                        <input type="hidden" name="id" value="${status.id}">
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
