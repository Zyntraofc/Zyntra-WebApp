<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <script src="${pageContext.request.contextPath}/js/mascara.js" defer></script>
    <title>Crud Status Aprovação - Área restrita</title>
</head>
<body>
<aside>
    <div class="sidebar-header">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/logos/logo-azul.png" alt="logo-aion" class="logo-aion">
            <div class="brand-text">
                <p class="aion">aion</p>
                <h4>Status de Aprovação</h4>
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
            <button type="submit" class="botao active">Status Aprovação</button>
            <input type="hidden" name="endpointInput" value="/private/ListarStatusAprovacao">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Indices Classificação</button>
            <input type="hidden" name="endpointInput" value="/private/ListarIndiceClassificacao">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao">Tipo Empresa</button>
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
        <form action="" class="search-form">
            <button type="submit">
                <img src="${pageContext.request.contextPath}/assets/icons/icon-lupa.png" alt="Pesquisar">
            </button>
            <input type="text" placeholder="Buscar por id, nome, email...">
        </form>

        <div class="actions">
            <button class="filtros">
                <span>Filtros</span>
                <img src="${pageContext.request.contextPath}/assets/icons/icon-circunflexo.png" alt="Filtros">
            </button>
            <form action="${pageContext.request.contextPath}/private/InserirEmpresa" class="button-adicionar-novo">
                <input type="hidden" name="caminho" value="StatusAprovacao">
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
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome da Empresa</th>
            <th>Status</th>
            <th>Data de Solicitação</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="status" items="${statuses}">
            <tr>
                <td>${status.id}</td>
                <td class="sensivel">${nomesEmpresas[status.id]}</td>
                <td>${String.valueOf(status.status).equals("a") ? "Aprovado" : String.valueOf(status.status).equals("r") ? "Recusado" : "Pendente"}</td>
                <td class="sensivel">${status.dataSolicitacao}</td>
                <td class="actions">
                    <div style="display: flex">
                        <button style="border: none; background: none; cursor: pointer" class="toggleLinha" data-olho="${pageContext.request.contextPath}/assets/icons/icon-olho.png"
                                data-olho-fechado="${pageContext.request.contextPath}/assets/icons/icon-olho-fechado.png">
                            <img src="${pageContext.request.contextPath}/assets/icons/icon-olho-fechado.png" />
                        </button>
                        <form action="${pageContext.request.contextPath}/private/AlterarStatusAprovacao" method="post">
                            <input type="hidden" name="id" value="${status.id}">
                            <input type="hidden" name="action" value="0">
                            <button type="submit" style="border: none; background: none; cursor: pointer" > <img src="${pageContext.request.contextPath}/assets/icons/icon-edit.png"></button>
                        </form>
                        <form action="${pageContext.request.contextPath}/private/DeletarEmpresa" method="post">
                            <input type="hidden" name="idStatus" value="${status.id}">
                            <input type="hidden" name="caminho" value="StatusAprovacao">
                            <input type="hidden" name="action" value="0">
                            <button type="submit" style="border: none; background: none; cursor: pointer" ><img src="${pageContext.request.contextPath}/assets/icons/icon-excluir.png"></button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </section>
</main>
<% if (request.getAttribute("popup-deletar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup-deletar">
    <h1>Deletar</h1>
    <p>Deseja mesmo excluir? Esta ação é irreversível.</p>
    <div class="opcoes-deletar">
        <form action="${pageContext.request.contextPath}/private/DeletarEmpresa" method="post">
            <input type="hidden" name="action" value="1">
            <input type="hidden" name="caminho" value="${caminho}">
            <input type="hidden" name="idStatus" value="${empresa.getIdStatusAprovacao()}">
            <input type="hidden" name="id" value="${empresa.getId()}">
            <button type="submit">✔ Confirmar</button>
        </form>
        <form action="${pageContext.request.contextPath}/private/DeletarEmpresa" method="post">
            <input type="hidden" name="caminho" value="${caminho}">
            <input type="hidden" name="action" value="2">
            <button type="submit">✖ Cancelar</button>
        </form>
    </div>
</div>
<% } %>

<% if (request.getAttribute("popup-alterar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Editar Status de Aprovação</h1>

    <form action="${pageContext.request.contextPath}/private/AlterarStatusAprovacao" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="id" value="${alterarStatus.getId()}">

        <div class="status">
            <input type="radio" name="status" value="a" id="ativo" ${String.valueOf(alterarStatus.status).equals("a") ? 'checked' : ''}>
            <label for="ativo">Ativo</label>
            <input type="radio" name="status" value="p" id="pendente" ${String.valueOf(alterarStatus.status).equals("p") ? 'checked' : ''}>
            <label for="pendente">Pendente</label>
            <input type="radio" name="status" value="r" id="recusado" ${String.valueOf(alterarStatus.status).equals("r") ? 'checked' : ''}>
            <label for="recusado">Recusado</label>
        </div>

        <label for="motivoRejeicao">Motivo de rejeição</label>
        <textarea name="motivoRejeicao" id="motivoRejeicao" rows="4">${alterarStatus.getMotivoRejeicao()}</textarea>

        <div class="botoes">
            <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarStatusAprovacao">✖ Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Insira uma empresa para associá-la ao Status</h1>

    <form action="${pageContext.request.contextPath}/private/InserirEmpresa" method="post">
        <input type="hidden" name="caminho" value="${caminho}">

        <label for="NovoTipoEmpresa">Tipo de Empresa</label>
        <select name="idTipoEmpresa" id="NovoTipoEmpresa" required>
            <option value="" disabled selected>Selecione o tipo de empresa</option>
            <c:forEach var="tipo" items="${tipos}">
                <option value="${tipo.id}">${tipo.nome}</option>
            </c:forEach>
        </select>

        <label for="Novonome">Nome</label>
        <input type="text" id="Novonome" name="nome">

        <label for="Novocnpj">CNPJ</label>
        <input type="text" id="Novocnpj" name="cnpj">

        <label for="Novoemail">E-mail</label>
        <input type="email" id="Novoemail" name="email">

        <label for="Novotelefone">Telefone</label>
        <input type="text" id="Novotelefone" name="telefone">

        <div class="botoes">
            <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarStatusAprovacao">✖ Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>
</body>
</html>