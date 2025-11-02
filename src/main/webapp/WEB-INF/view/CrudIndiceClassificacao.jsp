<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.IndiceClassificacao" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <title>Crud Indice classificação - Área restrita</title>
    <script src="${pageContext.request.contextPath}/js/pesquisa.js"></script>
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
                <h4>Índice de Classificação</h4>
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
            <button type="submit" class="botao active">Indices Classificação</button>
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
        <form class="search-form">
            <img src="${pageContext.request.contextPath}/assets/icons/icon-lupa.png" alt="Pesquisar">
            <input type="text" id="searchbar" name="searchbar" onkeyup="search()" placeholder="Buscar %...">

        </form>

        <div class="actions">
            <form action="${pageContext.request.contextPath}/private/InserirIndiceClassificacao"
                  class="button-adicionar-novo">
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
                    <th>Preocupação</th>
                    <th>Porcentagem</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<IndiceClassificacao> indicesClassificacao = (List<IndiceClassificacao>) request.getAttribute("indicesClassificacao");
                    if (indicesClassificacao != null) {
                        for (IndiceClassificacao indiceClassificacao : indicesClassificacao) {
                %>
                <tr class="linhas">
                    <td><%= indiceClassificacao.getId() %></td>
                    <td><%= indiceClassificacao.getPreocupacao() %></td>
                    <td data-label="Pesquisar"><%= indiceClassificacao.getPorcentagemMinima() %>
                        - <%= indiceClassificacao.getPorcentagemMaxima() %>%
                    </td>
                    <td class="actions">
                        <div style="display: flex">
                            <form action="${pageContext.request.contextPath}/private/AlterarIndiceClassificacao"
                                  method="post">
                                <input type="hidden" name="id" value="<%= indiceClassificacao.getId() %>">
                                <input type="hidden" name="action" value="0">
                                <button type="submit" style="border: none; background: none; cursor: pointer"><img
                                        src="${pageContext.request.contextPath}/assets/icons/icon-edit.png">
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/private/DeletarIndiceClassificacao"
                                  method="post">
                                <input type="hidden" name="id" value="<%= indiceClassificacao.getId() %>">
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
        <form action="${pageContext.request.contextPath}/private/DeletarIndiceClassificacao" method="post">
            <input type="hidden" name="action" value="1">
            <input type="hidden" name="id" value="${indiceClassificacao.getId()}">
            <button type="submit">✔ Confirmar</button>
        </form>
        <form action="${pageContext.request.contextPath}/private/DeletarIndiceClassificacao" method="post">
            <input type="hidden" name="id" value="${indiceClassificacao.getId()}">
            <input type="hidden" name="action" value="2">
            <button type="submit">✖ Cancelar</button>
        </form>
    </div>
</div>
<% } %>

<% if (request.getAttribute("popup-alterar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Editar Índice de Classificação</h1>

    <form action="${pageContext.request.contextPath}/private/AlterarIndiceClassificacao" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="id" value="${indiceClassificacao.getId()}">

        <label for="porcentagemMinima">Porcentagem Mínima</label>
        <input type="number" name="porcentagemMinima" id="porcentagemMinima"
               value="${indiceClassificacao.getPorcentagemMinima()}" min="0" required class="validar-input"  placeholder="Ex: 10.0" step="0.1">

        <label for="porcentagemMaxima">Porcentagem máxima</label>
        <input type="number" name="porcentagemMaxima" id="porcentagemMaxima"
               value="${indiceClassificacao.getPorcentagemMaxima()}" max="100" required class="validar-input" placeholder="Ex: 25.0" step="0.1">

        <label for="preocupacao">Preocupação</label>
        <input type="text" name="preocupacao" required id="preocupacao" value="${indiceClassificacao.getPreocupacao()}" placeholder="Ex: Baixa">
        <label for="recomendacao">Recomendação</label>
        <textarea rows="4" name="recomendacao" id="recomendacao" required placeholder="Digite as recomendações para esta faixa de classificação...">${indiceClassificacao.getRecomendacao()}</textarea>

        <div class="botoes">
            <div class="cancelar"><a href="${pageContext.request.contextPath}/private/ListarIndiceClassificacao">✖
                Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Inserir Índice de Classificação</h1>

    <form action="${pageContext.request.contextPath}/private/InserirIndiceClassificacao" method="post">
        <label for="Novaminima">Porcentagem mínima</label>
        <input type="number" name="porcentagemMinima" id="Novaminima" placeholder="Digite a porcentagem mínima (%)"
               min="0" required class="validar-input" placeholder="Ex: 10.0" step="0.1">
        <label for="Novamaxima">Porcentagem máxima</label>
        <input type="number" name="porcentagemMaxima" id="Novamaxima" placeholder="Digite a porcentagem máxima (%)"
               max="100" required class="validar-input" placeholder="Ex: 25.0" step="0.1">
        <label for="Novapreocupacao">Preocupação</label>
        <input type="text" name="preocupacao" id="Novapreocupacao" required placeholder="Digite o o nível de preocupação" placeholder="Ex: Baixa">
        <label for="Novarecomendacao">Recomendação</label>
        <textarea rows="4" name="recomendacao" id="Novarecomendacao" required
                  placeholder="Digite as recomendações para esta faixa de classificação..."></textarea>

        <div class="botoes">
            <div class="cancelar"><a href="${pageContext.request.contextPath}/private/ListarIndiceClassificacao">✖
                Cancelar</a></div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>
</body>
</html>