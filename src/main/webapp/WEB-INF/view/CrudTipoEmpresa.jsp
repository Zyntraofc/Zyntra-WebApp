<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <title>Crud Tipo Empresa - Área restrita</title>
</head>
<body>
<aside>
    <div class="sidebar-header">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/logos/logo-azul.png" alt="logo-aion" class="logo-aion">
            <div class="brand-text">
                <p class="aion">aion</p>
                <h4>Tipo de empresa</h4>
            </div>
        </div>
        <hr>
    </div>

    <div class="barra-lateral">
        <form action="Autenticar" method="post">
            <button type="submit" class="botao">Empresa</button>
            <input type="hidden" name="endpointInput" value="private/ListarEmpresas">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="Autenticar" method="post">
            <button type="submit" class="botao">Adm</button>
            <input type="hidden" name="endpointInput" value="private/ListarAdministradores">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="Autenticar" method="post">
            <button type="submit" class="botao">Status Aprovação</button>
            <input type="hidden" name="endpointInput" value="private/ListarStatusAprovacao">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="Autenticar" method="post">
            <button type="submit" class="botao">Indices Classificação</button>
            <input type="hidden" name="endpointInput" value="private/ListarIndiceClassificacao">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="Autenticar" method="post">
            <button type="submit" class="botao active">Tipo Empresa</button>
            <input type="hidden" name="endpointInput" value="private/ListarTipoEmpresa">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="Autenticar" method="post">
            <button type="submit" class="botao">Motivo Falta</button>
            <input type="hidden" name="endpointInput" value="private/ListarMotivosFalta">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>

        <div class="sair-container">
            <form action="Logout">
                <button type="submit" class="sair">
                    <img href="assets/Saida.png" alt="Sair">
                    <span>Sair</span>
                </button>
            </form>
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
            <form action="InserirTipoEmpresa" class="button-adicionar-novo">
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
            <td>${String.valueOf(tipoEmpresa.status).equals("i") ?"Inativo": "Ativo"}</td>
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

</main>

<% if (request.getAttribute("popup-deletar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup-deletar">
    <h1>Deletar</h1>
    <p>Deseja mesmo excluir? Esta ação é irreversível.</p>
    <div class="opcoes-deletar">
        <form action="DeletarTipoEmpresa" method="post">
            <input type="hidden" name="action" value="1">
            <input type="hidden" name="id" value="${tipoEmpresa.getId()}">
            <button type="submit">Sim</button>
        </form>
        <form action="DeletarTipoEmpresa" method="post">
            <input type="hidden" name="id" value="${tipoEmpresa.getId()}">
            <input type="hidden" name="action" value="2">
            <button type="submit">Não</button>
        </form>
    </div>
</div>
<% } %>

<% if (request.getAttribute("popup-alterar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Editar Tipo de Empresa</h1>

    <form action="AlterarTipoEmpresa" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="id" value="${tipoEmpresa.getId()}">

        <label for="nome">Nome</label>
        <input type="text" name="nome" id="nome" value="${tipoEmpresa.getNome()}">

        <label for="descricao">Descrição</label>
        <textarea name="descricao" id="descricao" rows="2" >${tipoEmpresa.getDescricao()}</textarea>

        <div class="botoes">
            <div class="cancelar"> <a href="ListarTipoEmpresa">✖ Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Inserir tipo de empresa</h1>
    <form action="InserirTipoEmpresa" method="post">
        <label for="Novonome">Tipo de Empresa</label>
        <input type="text" name="nome" id="Novonome" placeholder="Digite o tipo de empresa">

        <label for="Novadescricao">Descrição</label>
        <textarea name="descricao" id="Novadescricao" rows="2" placeholder="Digite a descrição dessa empresa (opcional)"></textarea>

        <div class="botoes">
            <div class="cancelar"> <a href="ListarTipoEmpresa">✖ Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>
</body>
</html>
