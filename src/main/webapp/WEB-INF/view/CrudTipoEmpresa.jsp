<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <script src="${pageContext.request.contextPath}/js/pesquisa.js"></script>
    <title>Crud Tipo Empresa - Área restrita</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/logos/logo-quadrada.png">
</head>
<body>
<aside>
    <div class="sidebar-header">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/logos/logo-quadrada.png" alt="logo-aion" class="logo-aion">
            <div class="brand-text">
                <p class="aion">aion</p>
                <h4>Tipo de empresa</h4>
            </div>
        </div>
        <hr>
    </div>

    <div class="barra-lateral">
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Empresa</button>
            <input type="hidden" name="endpointInput" value="/private/ListarEmpresas">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Adm</button>
            <input type="hidden" name="endpointInput" value="/private/ListarAdministradores">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Status Aprovação</button>
            <input type="hidden" name="endpointInput" value="/private/ListarStatusAprovacao">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Indices Classificação</button>
            <input type="hidden" name="endpointInput" value="/private/ListarIndiceClassificacao">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao active">Tipo Empresa</button>
            <input type="hidden" name="endpointInput" value="/private/ListarTipoEmpresa">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Motivo Falta</button>
            <input type="hidden" name="endpointInput" value="/private/ListarMotivosFalta">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>

        <div class="sair-container">
            <form action="${pageContext.request.contextPath}/Logout">
                <button type="submit" class="sair">
                    <img src="${pageContext.request.contextPath}/assets/icons/icon-saida.png" alt="Sair">
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
        <form class="search-form">
            <img src="${pageContext.request.contextPath}/assets/icons/icon-lupa.png" alt="Pesquisar">
            <input id="searchbar" name="searchbar" onkeyup="search()" type="text" placeholder="Buscar nome...">
        </form>

        <div class="actions">
            <input type="checkbox" id="toggle-filtros" hidden>

            <label for="toggle-filtros" class="filtros">
                <span>Filtros</span>
                <img src="${pageContext.request.contextPath}/assets/icons/icon-circunflexo.png" alt="Filtros">
            </label>

            <label for="toggle-filtros" class="filtros-overlay"></label>


            <!-- POPUP DOS FILTROS -->
            <div class="filtros-container">
                <div class="filtros-header">
                    <h3 class="filtros-titulo">Filtros</h3>
                </div>
                <form action="${pageContext.request.contextPath}/private/ListarTipoEmpresa" method="post">
                    <div class="filtro-section">


                    <div class="filtro-item">
                        <label for="statusesOrdenados">Status: </label>
                        <select name="ordenarStatus" id="statusesOrdenados">
                            <option value="">Todas</option>
                            <option value="a" ${param.ordenarStatus == 'a' ? 'selected' : ''}>Ativas</option>
                            <option value="p" ${param.ordenarStatus == 'i' ? 'selected' : ''}>Inativas</option>
                        </select>
                    </div>

                    <div class="filtro-item">
                        <label for="atualizacoesOrdenadas">Últimas atualizações: </label>
                        <select name="ordenarAtualizacoes" id="atualizacoesOrdenadas">
                            <option value="">Todas</option>
                            <option value="1" ${param.ordenarAtualizacoes == '1' ? 'selected' : ''}>Recentes</option>
                            <option value="2" ${param.ordenarAtualizacoes == '2' ? 'selected' : ''}>Antigas</option>
                        </select>
                    </div>
                    </div>

                    <button type="submit" class="botao-filtrar">Aplicar Filtros</button>
                </form>
            </div>

            <!-- CORREÇÃO: Botão Adicionar Novo dentro do container actions -->
            <form action="${pageContext.request.contextPath}/private/InserirTipoEmpresa" class="button-adicionar-novo">
                <button type="submit">
                    <img src="${pageContext.request.contextPath}/assets/icons/icon-add.png" alt="Adicionar">
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
    <section class="table-card">
        <div class="table-container">
        <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Status</th>
            <th>Última atualização</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tipoEmpresa" items="${tiposEmpresa}">
            <tr class="linhas">
                <td>${tipoEmpresa.id}</td>
                <td data-label="Pesquisar">${tipoEmpresa.nome}</td>
                <td>${String.valueOf(tipoEmpresa.status).equals("i") ?"Inativo": "Ativo"}</td>
                <td>${tipoEmpresa.ultimaAtualizacao}</td>
                <td class="actionsgit">
                    <div style="display: flex">
                        <form action="${pageContext.request.contextPath}/private/AlterarTipoEmpresa" method="post">
                            <input type="hidden" name="id" value="${tipoEmpresa.id}">
                            <input type="hidden" name="action" value="0">
                            <button type="submit" style="border: none; background: none; cursor: pointer" > <img src="${pageContext.request.contextPath}/assets/icons/icon-edit.png"></button>
                        </form>
                        <form action="${pageContext.request.contextPath}/private/DeletarTipoEmpresa" method="post">
                            <input type="hidden" name="id" value="${tipoEmpresa.id}">
                            <input type="hidden" name="action" value="0">
                            <button type="submit" style="border: none; background: none; cursor: pointer" ><img src="${pageContext.request.contextPath}/assets/icons/icon-excluir.png"></button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
        </div>
    </section>
</main>

<% if (request.getAttribute("popup-deletar") != null) { %>
<div class="tela-transparente"></div>
<div class="deletar">
    <h1>Deletar</h1>
    <p>Deseja mesmo excluir? Esta ação é irreversível.</p>
    <div class="opcoes">
        <form action="${pageContext.request.contextPath}/private/DeletarTipoEmpresa" method="post">
            <input type="hidden" name="action" value="1">
            <input type="hidden" name="id" value="${tipoEmpresa.getId()}">
            <button type="submit">Sim</button>
        </form>
        <form action="${pageContext.request.contextPath}/private/DeletarTipoEmpresa" method="post">
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

    <form action="${pageContext.request.contextPath}/private/AlterarTipoEmpresa" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="id" value="${tipoEmpresa.getId()}">

        <label for="nome">Nome</label>
        <input type="text" name="nome" id="nome" value="${tipoEmpresa.getNome()}">

        <label for="descricao">Descrição</label>
        <textarea name="descricao" id="descricao" rows="2" >${tipoEmpresa.getDescricao()}</textarea>

        <div class="botoes">
            <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarTipoEmpresa">✖ Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Inserir tipo de empresa</h1>
    <form action="${pageContext.request.contextPath}/private/InserirTipoEmpresa" method="post">
        <label for="Novonome">Tipo de Empresa</label>
        <input type="text" name="nome" id="Novonome" placeholder="Digite o tipo de empresa">

        <label for="Novadescricao">Descrição</label>
        <textarea name="descricao" id="Novadescricao" rows="2" placeholder="Digite a descrição dessa empresa (opcional)"></textarea>

        <div class="botoes">
            <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarTipoEmpresa">✖ Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>
</body>
</html>