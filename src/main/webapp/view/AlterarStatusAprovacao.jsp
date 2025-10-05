<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="AlterarStatusAprovacao" method="post">
    <input type="hidden" name="action" value="1">

    <input type="hidden" name="id" value="${status.getId()}">
    <input type="text" name="motivoRejeicao" value="${status.getMotivoRejeicao()}">

    <input type="radio" name="status" value="a" id="ativo" ${String.valueOf(status.status).equals("a") ? 'checked' : ''}>
    <label for="ativo">Ativo</label>
    <input type="radio" name="status" value="p" id="pendente" ${String.valueOf(status.status).equals("p") ? 'checked' : ''}>
    <label for="pendente">Pendente</label>
    <input type="radio" name="status" value="r" id="recusado" ${String.valueOf(status.status).equals("r") ? 'checked' : ''}>
    <label for="recusado">Recusado</label>



    <button type="submit">Alterar</button>
</form>
</body>
</html>
</html>
