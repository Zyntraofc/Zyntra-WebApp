<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Empresa" %>
<%@ page import="org.example.model.TipoEmpresa" %>
<%@ page import="org.example.model.IndiceClassificacao" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
    <script src="${pageContext.request.contextPath}/js/mascara.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/pesquisa.js"></script>
    <title>Crud Empresa - Área restrita</title>
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
                <h4>Empresa</h4>
            </div>
        </div>
        <hr>
    </div>

    <div class="barra-lateral">
        <form action="${pageContext.request.contextPath}/Autenticar" method="post">
            <button type="submit" class="botao active">Empresa</button>
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
    <h1>Área Restrita </h1>
    <p>CRUD</p>

    <div class="top-bar">
        <form class="search-form">
            <img src="${pageContext.request.contextPath}/assets/icons/icon-lupa.png" alt="Pesquisar">
            <input type="text" id="searchbar" name="searchbar" onkeyup="search()" placeholder="Buscar nome...">

        </form>

        <div class="actions">
            <input type="checkbox" id="toggle-filtros">

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
                <form action="${pageContext.request.contextPath}/private/ListarEmpresas">
                    <div class="filtro-section">

                        <div class="filtro-item">
                            <label>
                                <input type="checkbox" name="ordenarNome" ${ordenarNome ? 'checked' : ''}>
                                <span>Ordem alfabética (Nome)</span>
                            </label>
                        </div>

                        <div class="filtro-item">
                            <label for="statusesOrdenados">Status: </label>
                            <select name="ordenarStatus" id="statusesOrdenados">
                                <option value="">Todas</option>
                                <option value="a" ${param.ordenarStatus == 'a' ? 'selected' : ''}>Aprovadas</option>
                                <option value="p" ${param.ordenarStatus == 'p' ? 'selected' : ''}>Pendentes</option>
                                <option value="r" ${param.ordenarStatus == 'r' ? 'selected' : ''}>Rejeitadas</option>
                            </select>
                        </div>
                    </div>
                    <div class="filtro-section">
                        <div class="filtro-item">
                            <label for="idTipoEmpresaFiltro">Tipo de Empresa:</label>
                            <select name="idTipoEmpresaFiltro" id="idTipoEmpresaFiltro">
                                <option value="">Todas</option>
                                <%
                                    List<TipoEmpresa> tiposFiltro = (List<TipoEmpresa>) request.getAttribute("tiposFiltro");
                                    String idTipoEmpresaFiltro = (String) request.getAttribute("idTipoEmpresaFiltro");
                                    if (tiposFiltro != null) {
                                        for (TipoEmpresa tipo : tiposFiltro) {
                                %>
                                <option <%= tipo.getId() == (idTipoEmpresaFiltro != null ? Integer.parseInt(idTipoEmpresaFiltro) : 0) ? "selected" : "" %>
                                        value="<%= tipo.getId() %>"><%= tipo.getNome() %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <div class="filtro-item">
                            <label for="idIndiceClassificacao">Índice de Classificação</label>
                            <select name="idIndiceClassificacaoFiltro" id="idIndiceClassificacao">
                                <option value="">Todas</option>
                                <%
                                    List<IndiceClassificacao> indices = (List<IndiceClassificacao>) request.getAttribute("indices");
                                    Empresa empresaAttr = (Empresa) request.getAttribute("empresa");
                                    if (indices != null) {
                                        for (IndiceClassificacao indice : indices) {
                                %>
                                <option value="<%= indice.getId() %>" <%= empresaAttr != null && empresaAttr.getIdIndiceClassificacao() == indice.getId() ? "selected" : "" %>>
                                    <%= String.format("%.1f", indice.getPorcentagemMinima()) %>%
                                    - <%= String.format("%.1f", indice.getPorcentagemMaxima()) %>%
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="botao-filtrar">Aplicar Filtros</button>
                </form>
            </div>

            <form action="${pageContext.request.contextPath}/private/InserirEmpresa" class="button-adicionar-novo">
                <input type="hidden" name="caminho" value="Empresas">
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
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CNPJ</th>
                    <th>Telefone</th>
                    <th>Tipo de empresa</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Empresa> empresas = (List<Empresa>) request.getAttribute("empresas");
                    java.util.Map<Integer, String> tiposEmpresa = (java.util.Map<Integer, String>) request.getAttribute("tiposEmpresa");
                    if (empresas != null) {
                        for (Empresa empresa : empresas) {
                %>
                <tr class="linhas">
                    <td data-label="ID"><%= empresa.getId() %></td>
                    <td data-label="Pesquisar"><%= empresa.getNome() %></td>
                    <td data-label="Email" class="sensivel"><%= empresa.getEmail() %></td>
                    <td data-label="CNPJ" class="sensivel"><%= empresa.getCnpj() %></td>
                    <td data-label="Telefone" class="sensivel"><%= empresa.getTelefone() %></td>
                    <td data-label="Tipo de empresa"><%= tiposEmpresa != null ? tiposEmpresa.get(empresa.getId()) : "" %></td>

                    <td data-label="Ações" class="actions">
                        <div style="display: flex">
                            <form>
                                <button style="border: none; background: none; cursor: pointer" class="toggleLinha"
                                        data-olho="${pageContext.request.contextPath}/assets/icons/icon-olho.png"
                                        data-olho-fechado="${pageContext.request.contextPath}/assets/icons/icon-olho-fechado.png">
                                    <img src="${pageContext.request.contextPath}/assets/icons/icon-olho-fechado.png"/>
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/private/AlterarEmpresa" method="post">
                                <input type="hidden" name="id" value="<%= empresa.getId() %>">
                                <input type="hidden" name="action" value="0">
                                <button type="submit" style="border: none; background: none; cursor: pointer"><img
                                        src="${pageContext.request.contextPath}/assets/icons/icon-edit.png">
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/private/DeletarEmpresa" method="post">
                                <input type="hidden" name="id" value="<%= empresa.getId() %>">
                                <input type="hidden" name="caminho" value="Empresas">
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
    <h1>Editar Empresa</h1>

    <form action="${pageContext.request.contextPath}/private/AlterarEmpresa" method="post">
        <input type="hidden" name="action" value="1">
        <input type="hidden" name="id" value="${empresa.getId()}">

        <label for="idTipoEmpresa">Tipo de Empresa</label>
        <select name="idTipoEmpresa" id="idTipoEmpresa" required>
            <%
                List<TipoEmpresa> tipos = (List<TipoEmpresa>) request.getAttribute("tipos");
                Empresa empresaAlterar = (Empresa) request.getAttribute("empresa");
                if (tipos != null) {
                    for (TipoEmpresa tipo : tipos) {
            %>
            <option value="<%= tipo.getId() %>" <%= empresaAlterar != null && empresaAlterar.getIdTipoEmpresa() == tipo.getId() ? "selected" : "" %>><%= tipo.getNome() %></option>
            <%
                    }
                }
            %>
        </select>

        <label for="IndiceClassificacao">Índice de Classificação</label>
        <select name="idIndiceClassificacao" id="IndiceClassificacao" required>
            <%
                List<IndiceClassificacao> indicesAlterar = (List<IndiceClassificacao>) request.getAttribute("indices");
                if (indicesAlterar != null) {
                    for (IndiceClassificacao indice : indicesAlterar) {
            %>
            <option value="<%= indice.getId() %>" <%= empresaAlterar != null && empresaAlterar.getIdIndiceClassificacao() == indice.getId() ? "selected" : "" %>>
                <%= String.format("%.1f", indice.getPorcentagemMinima()) %>%
                - <%= String.format("%.1f", indice.getPorcentagemMaxima()) %>%
            </option>
            <%
                    }
                }
            %>
        </select>

        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" value="${empresa.getNome()}">

        <label for="cnpj">CNPJ</label>
        <input type="text" id="cnpj" name="cnpj" value="${empresa.getCnpj()}" readonly>

        <label for="email">E-mail</label>
        <input class="validar-input" required type="email" id="email" name="email" value="${empresa.getEmail()}">

        <label for="telefone-mask">Telefone</label>
        <input type="text" id="telefone-mask" class="validar-input" required pattern="^([^,]{15})$" value="${empresa.getTelefone()}">
        <input type="hidden" id="telefone" name="telefone">

        <div class="botoes">
            <div class="cancelar"><a href="${pageContext.request.contextPath}/private/ListarEmpresas">✖ Cancelar</a>
            </div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
    <h1>Inserir empresa (conectada à Status)</h1>

    <form action="${pageContext.request.contextPath}/private/InserirEmpresa" method="post">
        <input type="hidden" name="caminho" value="${caminho}">

        <label for="NovoTipoEmpresa">Tipo de Empresa</label>
        <select name="idTipoEmpresa" id="NovoTipoEmpresa" required>
            <option value="" disabled selected>Selecione o tipo de empresa</option>
            <%
                List<TipoEmpresa> tiposInserir = (List<TipoEmpresa>) request.getAttribute("tipos");
                if (tiposInserir != null) {
                    for (TipoEmpresa tipo : tiposInserir) {
            %>
            <option value="<%= tipo.getId() %>"><%= tipo.getNome() %></option>
            <%
                    }
                }
            %>
        </select>

        <label for="Novonome">Nome</label>
        <input type="text" id="Novonome" name="nome">

        <label for="Novocnpj-mask">CNPJ</label>
        <input type="text" id="Novocnpj-mask" class="validar-input" required pattern="^([^ ]{18})$">
        <input type="hidden" id="Novocnpj" name="cnpj">

        <label for="Novoemail">E-mail</label>
        <input type="email" id="Novoemail" name="email" required class="validar-input">

        <label for="Novotelefone-mask">Telefone</label>
        <input type="text" id="Novotelefone-mask" class="validar-input" pattern="^([^,]{15})$" required>
        <input type="hidden" id="Novotelefone" name="telefone">

        <div class="botoes">
            <div class="cancelar"><a href="${pageContext.request.contextPath}/private/ListarEmpresas">✖ Cancelar</a>
            </div>
            <button type="submit" class="confirmar">✔ Confirmar</button>
        </div>
    </form>
</div>
<% } %>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.js"></script>
<script>
    $('#Novotelefone-mask').mask('(00) 00000-0000').on('keyup blur', function() { $('#Novotelefone').val($(this).cleanVal());});
    $('#Novocnpj-mask').mask('00.000.000/0000-00', {reverse: true}).on('keyup blur', function () {$('#Novocnpj').val($(this).cleanVal());});
    $('#telefone-mask').mask('(00) 00000-0000').on('keyup blur', function() { $('#telefone').val($(this).cleanVal());});
    $('#cnpj').mask('00.000.000/0000-00', {reverse: true});
</script>
</body>
</html>