<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Área restrita | Inserir tipo Empresa</title>
</head>
<body>
<form action="InserirTipoEmpresa" method="post">
    <input type="text" name="nome" placeholder="Digite o tipo de empresa">
    <br>
    <input type="text" name="status" placeholder="Digite o status">
    <br>
    <input type="date" name="ultima_atualizacao" placeholder="Digite a data da ultima atualização dessa empresa">
    <br>
    <input type="text" name="descricao" placeholder="Digite a descrição dessa empresa">
    <br>
    <button type="submit">Inserir</button>
</form>

    <%if(request.getAttribute("erro") != null){%>
<p>${erro}</p>
    <%}%>
</body>

