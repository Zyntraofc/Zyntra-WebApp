<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Crud Tipo Empresa - Área restrita</title>
</head>
<body>

<form action="InserirTipoEmpresa" method="get">
    <button type="submit">Inserir tipo empresa</button>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Nome</th>
        <th>ID</th>
        <th>Status</th>
        <th>Última atualização</th>
        <th>Descrição</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="tipoEmpresa" items="${tiposEmpresa}">
        <tr>
            <td>${tipoEmpresa.nome}</td>
            <td>${tipoEmpresa.id}</td>
            <td>${String.valueOf(tipoEmpresa.status).equals("i")?"Inativo": "Ativo"}</td>
            <td>${tipoEmpresa.ultimaAtualizacao}</td>
            <td>${tipoEmpresa.descricao}</td>
            <td>
                <div style="display: flex">
                    <form action="AlterarTipoEmpresa" method="post">
                        <input type="hidden" name="id" value="${tipoEmpresa.id}">
                        <input type="hidden" name="action" value="0">
                        <button type="submit">Alterar</button>
                    </form>
                    <form action="DeletarTipoEmpresa" method="post">
                        <input type="hidden" name="id" value="${tipoEmpresa.id}">
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
