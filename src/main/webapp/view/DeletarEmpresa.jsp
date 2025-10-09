
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Tem certeza disso?</h1>
    <form action="DeletarEmpresa" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="idStatus" value="${empresa.getIdStatusAprovacao()}">
        <input type="hidden" name="id" value="${empresa.getId()}">
        <button type="submit">Sim</button>
    </form>
    <form action="DeletarEmpresa" method="post">
        <input type="hidden" name="id" value="${empresa.getId()}">
        <input type="hidden" name="action" value="2">
        <button type="submit">Não</button>
    </form>

    <%
        if(request.getAttribute("erro") != null){
    %>
    <p>${erro}</p>
    <%
        }
    %>
</body>
</html>
