<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <title>Crud Motivo Falta - Área restrita</title>
</head>
<body>

<aside>
    <div class="sidebar-header">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/logos/logo-azul.png" alt="logo-aion" class="logo-aion">
            <div class="brand-text">
                <p class="aion">aion</p>
                <h4>Adm</h4>
            </div>
        </div>
        <hr>
    </div>

    <div class="barra-lateral">
        <form action="ListarEmpresas" method="post">
            <button type="submit" class="botao">Empresa</button>
        </form>
        <form action="ListarAdministradores" method="post">
            <button type="submit" class="botao">Adm</button>
        </form>
        <form action="ListarStatusAprovacao" method="post">
            <button type="submit" class="botao">Status Aprovação</button>
        </form>
        <form action="ListarIndiceClassificacao" method="post">
            <button type="submit" class="botao">Indices Classificação</button>
        </form>
        <form action="ListarTipoEmpresa" method="post">
            <button type="submit" class="botao">Tipo Empresa</button>
        </form>
        <form action="ListarMotivosFalta" method="post">
            <button type="submit" class="botao active">Motivo Falta</button>
        </form>

        <div class="sair-container">
            <a href="index.html" class="sair">
                <img src="assets/Saida.png" alt="Sair">
                <span>Sair</span>
            </a>
        </div>
    </div>
</aside>

<main>
    <h1>Área Restrita</h1>
    <p>CRUD</p>

    <div class="top-bar">
        <form action="">
            <input type="text" placeholder="Buscar por id, nome, email...">
            <button type="submit">
                <img src="assets/Vector.png" alt="Pesquisar">
            </button>
        </form>

        <div class="actions">
            <button class="filtros">
                <span>Filtros</span>
                <img src="assets/filtros.png" alt="Filtros">
            </button>
            <form action="InserirMotivoFalta.jsp" class="button-adicionar-novo">
                <button type="submit">
                    <img src="assets/add.png" alt="Adicionar">
                    <span>Adicionar Novo</span>
                </button>
            </form>
        </div>
    </div>
    <%
        if(request.getAttribute("erro") != null){
    %>
    <p><%=request.getAttribute("erro")%></p>
    <%
        }
    %>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Motivo</th>
        <th>Ações</th>
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
</main>
<% if (request.getAttribute("popup-deletar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup-deletar">
    <h1>Deletar</h1>
    <p>Deseja mesmo excluir? Esta ação é irreversível.</p>
    <div class="opcoes-deletar">
        <form action="DeletarMotivoFalta" method="post">
            <input type="hidden" name="action" value="1">
            <input type="hidden" name="id" value="${motivo.getId()}">
            <button type="submit">Sim</button>
        </form>
        <form action="DeletarMotivoFalta" method="post">
            <input type="hidden" name="id" value="${motivo.getId()}">
            <input type="hidden" name="action" value="2">
            <button type="submit">Não</button>
        </form>
    </div>
</div>
<% } %>
</body>
</html>
