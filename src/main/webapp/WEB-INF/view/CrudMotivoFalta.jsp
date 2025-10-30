<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.MotivoFalta" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <script src="${pageContext.request.contextPath}/js/pesquisa.js"></script>
    <title>Crud Motivo Falta - Área restrita</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/logos/logo-quadrada.png">
</head>
<body>

<aside>
    <div class="sidebar-header">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/logos/logo-quadrada.png" alt="logo-aion"
                 class="logo-aion">
            <div class="brand-text">
                <p class="aion">aion</p>
                <h4>Motivo de Falta</h4>
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
            <button type="submit" class="botao">Tipo Empresa</button>
            <input type="hidden" name="endpointInput" value="/private/ListarTipoEmpresa">
            <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
        </form>
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao active">Motivo Falta</button>
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
            <input id="searchbar" name="searchbar" onkeyup="search()" type="text" placeholder="Buscar id...">

        </form>

        <div class="actions">
            <form action="${pageContext.request.contextPath}/private/InserirMotivoFalta" class="button-adicionar-novo">
                <button type="submit">
                    <img src="${pageContext.request.contextPath}/assets/icons/icon-add.png" alt="Adicionar">
                    <span>Adicionar Novo</span>
                </button>
            </form>
        </div>
    </div>
    <%
        if (request.getAttribute("erro") != null) {
    %>
    <p><%=request.getAttribute("erro")%>
    </p>
    <%
        }
    %>
    <section class="table-card">
        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Motivo</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<MotivoFalta> motivos = (List<MotivoFalta>) request.getAttribute("motivos");
                    if (motivos != null) {
                        for (MotivoFalta motivo : motivos) {
                %>
                <tr class="linhas">
                    <td data-label="Pesquisar"><%= motivo.getId() %></td>
                    <td><%= motivo.getMotivo() %></td>

                    <td class="actions">
                        <div style="display: flex">
                            <form action="${pageContext.request.contextPath}/private/AlterarMotivoFalta"
                                  method="post">
                                <input type="hidden" name="id" value="<%= motivo.getId() %>">
                                <input type="hidden" name="action" value="0">
                                <button type="submit" style="border: none; background: none; cursor: pointer"><img
                                        src="${pageContext.request.contextPath}/assets/icons/icon-edit.png">
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/private/DeletarMotivoFalta"
                                  method="post">
                                <input type="hidden" name="id" value="<%= motivo.getId() %>">
                                <input type="hidden" name="action" value="0">
                                <button type="submit" style="border: none; background: none; cursor: pointer"><img
                                        src="${pageContext.request.contextPath}/assets/icons/icon-excluir.png">
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
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
        <form action="${pageContext.request.contextPath}/private/DeletarMotivoFalta" method="post">
            <input type="hidden" name="action" value="1">
            <input type="hidden" name="id" value="${motivo.getId()}">
            <button type="submit">Sim</button>
        </form>
        <form action="${pageContext.request.contextPath}/private/DeletarMotivoFalta" method="post">
            <input type="hidden" name="id" value="${motivo.getId()}">
            <input type="hidden" name="action" value="2">
            <button type="submit">Não</button>
        </form>
    </div>
</div>
<% } %>

<% if (request.getAttribute("popup-alterar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Editar Motivo de Falta</h1>

    <form action="${pageContext.request.contextPath}/private/AlterarMotivoFalta" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="id" value="${motivo.getId()}">

        <label for="motivo">Motivo</label>
        <input type="text" id="motivo" name="motivo" value="${motivo.getMotivo()}" placeholder="Ex: Férias coletivas, Manutenção programada, Greve">>

        <div class="botoes">
            <div class="cancelar"><a href="${pageContext.request.contextPath}/private/ListarMotivosFalta">✖ Cancelar</a>
            </div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Inserir Motivo de Falta</h1>
    <form action="${pageContext.request.contextPath}/private/InserirMotivoFalta" method="post">
        <label for="NovoMotivoFalta">Motivo</label>
        <input type="text" name="motivo" id="NovoMotivoFalta" placeholder="Ex: Férias coletivas, Manutenção programada, Greve">>
        <div class="botoes">
            <div class="cancelar"><a href="${pageContext.request.contextPath}/private/ListarMotivosFalta">✖ Cancelar</a>
            </div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>
</body>
</html>