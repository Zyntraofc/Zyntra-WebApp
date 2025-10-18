<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
  <title>Crud Indice classificação - Área restrita</title>
  <link rel="icon" type="image/png" href="../../assets/logos/logo-quadrada.png">
</head>
<body>
<aside>
  <div class="sidebar-header">
    <div class="logo-container">
      <img src="${pageContext.request.contextPath}/assets/logos/logo-azul.png" alt="logo-aion" class="logo-aion">
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
    <form action="" class="search-form">
      <button type="submit" >
        <img src="${pageContext.request.contextPath}/assets/icons/icon-lupa.png" alt="Pesquisar">
      </button>
      <input type="text" placeholder="Buscar por id, nome, email...">

    </form>

    <div class="actions">
      <button class="filtros">
        <span>Filtros</span>
        <img src="${pageContext.request.contextPath}/assets/icons/icon-circunflexo.png" alt="Filtros">
      </button>
      <form action="${pageContext.request.contextPath}/private/InserirIndiceClassificacao" class="button-adicionar-novo">
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
      <th>Preocupação</th>
      <th>Porcentagem</th>
      <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="indiceClassificacao" items="${indicesClassificacao}">
      <tr>
        <td>${indiceClassificacao.id}</td>
        <td>${indiceClassificacao.preocupacao}</td>
        <td>${indiceClassificacao.porcentagemMinima} - ${indiceClassificacao.porcentagemMaxima}%</td>
        <td class="actions">
          <div style="display: flex">
            <form action="${pageContext.request.contextPath}/private/AlterarIndiceClassificacao" method="post">
              <input type="hidden" name="id" value="${indiceClassificacao.id}">
              <input type="hidden" name="action" value="0">
              <button type="submit" style="border: none; background: none; cursor: pointer" > <img src="${pageContext.request.contextPath}/assets/icons/icon-edit.png"></button>
            </form>
            <form action="${pageContext.request.contextPath}/private/DeletarIndiceClassificacao" method="post">
              <input type="hidden" name="id" value="${indiceClassificacao.id}">
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
    <input type="number" name="porcentagemMinima" id="porcentagemMinima" value="${indiceClassificacao.getPorcentagemMinima()}">

    <label for="porcentagemMaxima">Porcentagem máxima</label>
    <input type="number" name="porcentagemMaxima" id="porcentagemMaxima" value="${indiceClassificacao.getPorcentagemMaxima()}">

    <label for="preocupacao">Preocupação</label>
    <input type="text" name="preocupacao" id="preocupacao" value="${indiceClassificacao.getPreocupacao()}">
    <label for="recomendacao">Recomendação</label>
    <textarea rows="4" name="recomendacao" id="recomendacao">${indiceClassificacao.getRecomendacao()}</textarea>

    <div class="botoes">
      <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarIndiceClassificacao">✖ Cancelar</a></div>
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
    <input type="number" name="porcentagemMinima"  id="Novaminima" placeholder="Digite a porcentagem mínima (%)">
    <label for="Novamaxima">Porcentagem máxima</label>
    <input type="number" name="porcentagemMaxima" id="Novamaxima" placeholder="Digite a porcentagem máxima (%)">
    <label for="Novapreocupacao">Preocupação</label>
    <input type="text" name="preocupacao" id="Novapreocupacao" placeholder="Digite o o nível de preocupação">
    <label for="Novarecomendacao">Recomendação</label>
    <textarea rows="4" name="recomendacao" id="Novarecomendacao" placeholder="Digite a recomendação para esse índice classificação"></textarea>

    <div class="botoes">
      <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarIndiceClassificacao">✖ Cancelar</a></div>
      <button type="submit" class="confirmar">✔ Confirmar</button>
    </div>
  </form>
</div>
<% } %>
</body>
</html>