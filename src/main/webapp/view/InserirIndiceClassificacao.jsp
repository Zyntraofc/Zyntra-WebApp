<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Área restrita | Inserir indice classificação</title>
</head>
<body>
<form action="InserirIndiceClassificacao" method="post">
    <input type="text" name="preocupacao" placeholder="Digite o o nível de preocupação">
    <br>
    <input type="number" name="porcentagemMinima" placeholder="Digite a porcentagem mínima">
    <br>
    <input type="number" name="porcentagemMaxima" placeholder="Digite a porcentagem máxima">
    <br>
    <input type="text" name="recomendacao" placeholder="Digite a recomendação para esse índice classificação">
    <br>
    <button type="submit">Inserir</button>
</form>

<%if(request.getAttribute("erro") != null){%>
<p>${erro}</p>
<%}%>
</body>

