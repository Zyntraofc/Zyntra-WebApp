<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="InserirEmpresa" method="post">
        <input type="number" name="idTipoEmpresa" placeholder="Digite o id do tipo da empresa">
        <br>
        <input type="number" name="idIndiceClassificacao" placeholder="Digite o id do índice de classificação">
        <br>
        <input type="number" name="idStatusAprovacao" placeholder="Digite o id do Status de aprovação">
        <br>
        <input type="text" name="nome" placeholder="Digite o nome da empresa">
        <br>
        <input type="text" name="cnpj" placeholder="Digite o cnpj da empresa">
        <br>
        <input type="email" name="email" placeholder="Digite o email da empresa">
        <br>
        <input type="text" name="telefone" placeholder="Digite o telefone da empresa">
        <br>
        <button type="submit">Inserir</button>
    </form>

<%if(request.getAttribute("erro") != null){%>
<p>${erro}</p>
<%}%>


</body>
</html>
