<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
  <title>Crud Empresa - Área restrita</title>
</head>
<body>
<aside>
  <div class="sidebar-header">
    <div class="logo-container">
      <img src="${pageContext.request.contextPath}/assets/logos/logo-azul.png" alt="logo-aion" class="logo-aion">
      <div class="brand-text">
        <p class="aion">aion</p>
        <h4>Empresa</h4>
      </div>
    </div>
    <hr>
  </div>

  <div class="barra-lateral">
    <form action="ListarEmpresas" method="post">
      <button type="submit" class="botao active">Empresa</button>
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
      <button type="submit" class="botao">Motivo Falta</button>
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
      <form action="InserirEmpresa" class="button-adicionar-novo">
        <input type="hidden" name="caminho" value="Empresas">
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
    <th>CNPJ</th>
    <th>Email</th>
    <th>Ações</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="empresa" items="${empresas}">
    <tr>
      <td>${empresa.nome}</td>
      <td>${empresa.id}</td>
      <td>${empresa.cnpj}</td>
      <td>${empresa.email}</td>
      <td>
        <div style="display: flex">
          <form action="AlterarEmpresa" method="post">
            <input type="hidden" name="id" value="${empresa.id}">
            <input type="hidden" name="action" value="0">
            <button type="submit">Alterar</button>
          </form>
          <form action="DeletarEmpresa" method="post">
            <input type="hidden" name="id" value="${empresa.id}">
            <input type="hidden" name="caminho" value="Empresas">
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
    <form action="DeletarEmpresa" method="post">
      <input type="hidden" name="action" value="1">
      <input type="hidden" name="caminho" value="${caminho}">
      <input type="hidden" name="idStatus" value="${empresa.getIdStatusAprovacao()}">
      <input type="hidden" name="id" value="${empresa.getId()}">
      <button type="submit">✔ Confirmar</button>
    </form>
    <form action="DeletarEmpresa" method="post">
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

  <form action="AlterarEmpresa" method="post">
    <input type="hidden" name="action" value="1">
    <input type="hidden" name="id" value="${empresa.getId()}">

    <label for="idTipoEmpresa">Tipo de Empresa</label>
    <select name="idTipoEmpresa" id="idTipoEmpresa" required>
      <c:forEach var="tipo" items="${tipos}">
        <option value="${tipo.id}" ${empresa.idTipoEmpresa == tipo.id ? "selected" : ""}>${tipo.nome}</option>
      </c:forEach>
    </select>

    <label for="IndiceClassificacao">Índice de Classificação</label>
    <select name="idIndiceClassificacao" id="IndiceClassificacao" required>
      <c:forEach var="status" items="${statuses}">
        <option value="${status.id}" ${empresa.idIndiceClassificacao == status.id ? "selected" : ""}>${String.format("%.1f", status.porcentagemMinima)}% - ${String.format("%.1f", status.porcentagemMaxima)}%</option>
      </c:forEach>
    </select>

    <label for="nome">Nome</label>
    <input type="text" id="nome" name="nome" value="${empresa.getNome()}">

    <label for="cnpj">CNPJ</label>
    <input type="text" id="cnpj" name="cnpj" value="${empresa.getCnpj()}" readonly>

    <label for="email">E-mail</label>
    <input type="email" id="email" name="email" value="${empresa.getEmail()}">

    <label for="telefone">Telefone</label>
    <input type="text" id="telefone" name="telefone" value="${empresa.getTelefone()}">

    <div class="botoes">
      <div class="cancelar"> <a href="ListarEmpresas">✖ Cancelar</a></div>
      <button type="submit" class="confirmar">✔ Confirmar</button>
    </div>
  </form>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
  <h1>Inserir empresa (conectada à Status)</h1>

  <form action="InserirEmpresa" method="post">
    <input type="hidden" name="caminho" value="${caminho}">

    <label for="NovoTipoEmpresa">Tipo de Empresa</label>
    <select name="idTipoEmpresa" id="NovoTipoEmpresa" required>
      <option  value="" disabled selected>Selecione o tipo de empresa </option>
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
      <div class="cancelar"> <a href="ListarEmpresas">✖ Cancelar</a></div>
      <button type="submit" class="confirmar">✔ Confirmar</button>
    </div>
  </form>
</div>
<% } %>
</body>
</html>
