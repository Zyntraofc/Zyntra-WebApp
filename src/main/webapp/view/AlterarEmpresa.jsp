<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="AlterarEmpresa" method="post">
    <input type="hidden" name="action" value="1">
    <input type="hidden" name="id" value="${empresa.getId()}">

    <input type="number" name="idTipoEmpresa" value="${empresa.getIdTipoEmpresa()}">
    <br>
    <input type="number" name="idIndiceClassificacao" value="${empresa.getIdIndiceClassificacao()}">
    <br>
    <input type="number" name="idStatusAprovacao" value="${empresa.getIdStatusAprovacao()}">
    <br>
    <input type="text" name="nome" value="${empresa.getNome()}">
    <br>
    <input type="text" name="cnpj" value="${empresa.getCnpj()}" readonly>
    <br>
    <input type="email" name="email" value="${empresa.getEmail()}">
    <br>
    <input type="text" name="telefone" value="${empresa.getTelefone()}">
    <br>
    <button type="submit">Alterar</button>
</form>

<%
    if(request.getAttribute("erro") != null){
%>
<p>${erro}</p>
<%
    }%>


</body>
</html>
