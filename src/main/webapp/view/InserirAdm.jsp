<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="InserirAdm" method="post">
    <input type="email" name="email" placeholder="Digite o email do administrador">
    <input type="password" name="senha" placeholder="Digite a senha dada ao administrador">


    <button type="submit">Inserir</button>
</form>

<form action="ListarAdministradores" method="post">
    <button type="submit">Voltar</button>
</form>


</body>
</html>
