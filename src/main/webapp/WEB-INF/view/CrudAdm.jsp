<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/crud.css">
  <title>Crud Adm - Área restrita</title>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/logos/logo-quadrada.png">
  <script src="${pageContext.request.contextPath}/js/mascara.js" defer></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-pap4G5rY9O6..." crossorigin="anonymous" referrerpolicy="no-referrer" />
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
    <form action="${pageContext.request.contextPath}/Autenticar" method="post">
      <button type="submit" class="botao">Empresa</button>
      <input type="hidden" name="endpointInput" value="/private/ListarEmpresas">
      <input type="hidden" name="usuario" value="${session.getAttribute("usuario")}">
    </form>
    <form action="${pageContext.request.contextPath}/Autenticar" method="post">
      <button type="submit" class="botao active">Adm</button>
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
      <form action="${pageContext.request.contextPath}/private/InserirAdm" class="button-adicionar-novo">
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
      <th>Email</th>
      <th>Senha</th>
      <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="administrador" items="${administradores}">
      <tr>
        <td>${administrador.id}</td>
        <td>${administrador.email}</td>
        <td class="sensivel">${administrador.hashSenha}</td>
        <td class="actions">
          <div style="display: flex">
            <button style="border: none; background: none; cursor: pointer" class="toggleLinha" data-olho="${pageContext.request.contextPath}/assets/icons/icon-olho.png"
                    data-olho-fechado="${pageContext.request.contextPath}/assets/icons/icon-olho-fechado.png">
              <img src="${pageContext.request.contextPath}/assets/icons/icon-olho-fechado.png" />
            </button>
            <form action="${pageContext.request.contextPath}/private/AlterarAdm" method="post">
              <input type="hidden" name="id" value="${administrador.id}">
              <input type="hidden" name="action" value="0">
              <button type="submit" style="border: none; background: none; cursor: pointer" > <img src="${pageContext.request.contextPath}/assets/icons/icon-edit.png"></button>
            </form>
            <form action="${pageContext.request.contextPath}/private/DeletarAdm" method="post">
              <input type="hidden" name="id" value="${administrador.id}">
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
    <form action="${pageContext.request.contextPath}/private/DeletarAdm" method="post">
      <input type="hidden" name="action" value="1">
      <input type="hidden" name="id" value="${administrador.getId()}">
      <button type="submit">✔ Confirmar</button>
    </form>
    <form action="${pageContext.request.contextPath}/private/DeletarAdm" method="post">
      <input type="hidden" name="id" value="${administrador.getId()}">
      <input type="hidden" name="action" value="2">
      <button type="submit">✖ Cancelar</button>
    </form>
  </div>
</div>
<% } %>

<% if (request.getAttribute("popup-inserir") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
  <h1>Inserir Administrador</h1>

  <form action="${pageContext.request.contextPath}/private/InserirAdm" method="post">
    <label for="NovoEmail">Email</label>
    <input type="email" name="email" id="NovoEmail" placeholder="Digite o email do administrador">

    <label for="NovaSenha">Senha</label>
    <input type="password" name="senha" id="NovaSenha" placeholder="Digite a senha dada ao administrador">
    <div class="botoes">
      <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarAdministradores">✖ Cancelar</a></div>
      <button type="submit" class="confirmar">✔ Confirmar</button>
    </div>
  </form>
</div>
<% } %>

<% if (request.getAttribute("popup-alterar") != null) { %>
<div class="tela-transparente"></div>
<div class="popup">
  <div id="alterarSenha-container">
    <h1>Editar Adm</h1>
    <form action="${pageContext.request.contextPath}/private/AlterarSenha" method="post">
      <input type="hidden" name="action" value="0">
      <input type="hidden" name="id" value="${administrador.getId()}">
      <button type="submit" class="btn-editar">
        <i class="fa fa-pencil" aria-hidden="true"></i> Editar Senha</button>
    </form>
  </div>

  <form action="${pageContext.request.contextPath}/private/AlterarAdm" method="post">
    <input type="hidden" name="action" value="1">
    <input type="hidden" name="id" value="${administrador.getId()}">

    <label for="email">E-mail</label>
    <input type="email" name="email" id="email" value="${administrador.getEmail()}">
    <div class="botoes">
      <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarAdministradores">✖ Cancelar</a></div>
      <button type="submit" class="confirmar">✔ Confirmar</button>
    </div>
  </form>
</div>
<% } %>

<% if (request.getAttribute("popup-senha") != null){ %>
<div class="tela-transparente"></div>
<div class="popup">
  <h1>Trocar Senha</h1>
  <form action="${pageContext.request.contextPath}/private/AlterarSenha" method="post">
    <input type="hidden" name="action" value="1">
    <input type="hidden" name="id" value="${administrador.getId()}">

    <label for="passwordAtual">Senha Atual</label>
    <input type="password" id="passwordAtual" name="senhaAtual" required placeholder="Confirme aqui a senha atual">

    <label for="passwordNova">Nova Senha</label>
    <input type="password" id="passwordNova" name="senhaNova" required placeholder="Digite a nova senha">
    <div class="botoes">
      <div class="cancelar"> <a href="${pageContext.request.contextPath}/private/ListarAdministradores?popup-alterar=true&id=${administrador.getId()}">✖ Cancelar</a></div>
      <button type="submit" class="confirmar">✔ Confirmar</button>
    </div>
  </form>
</div>
<% } %>
</body>
</html>