<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error-styles.css">
    <title>Erro interno</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/logos/logo-quadrada.png">
</head>
<body>
<div class="error-container">
    <div class="error-header">
        <img src="${pageContext.request.contextPath}/assets/icons/icon-error.png"
             alt="Ícone de Erro" class="error-icon">
        <h1>Ocorreu um erro!</h1>
    </div>

    <div class="error-content">
        <section class="error-section">
            <h2>HTTP Status 500 – Internal Server Error</h2>

            <div class="error-details">
                <div class="detail-row">
                    <strong>Type:</strong> Exception Report
                </div>
                <div class="detail-row">
                    <strong>Message:</strong>
                    <%
                        String erro = (String) request.getAttribute("erro");
                        if (erro != null && !erro.isEmpty()) {
                            out.print(erro);
                        } else {
                            out.print("Cannot invoke \"java.sql.Connection.setAutoCommit(boolean)\" because \"org.example.conexao.ConexooManager.com\" is null");
                        }
                    %>
                </div>
                <div class="detail-row">
                    <strong>Description:</strong> The server encountered an unexpected condition that prevented it from fulfilling the request.
                </div>
            </div>
        </section>

        <section class="exception-section">
            <h3>Exception</h3>
            <div class="exception-details">
                <%
                    Exception excecao = (Exception) request.getAttribute("exception");
                    if (excecao != null) {
                %>
                <div class="exception-stack">
                    <code>
                        <%= excecao.getClass().getName() %>: <%= excecao.getMessage() %><br>
                        <%
                            StackTraceElement[] stackTrace = excecao.getStackTrace();
                            for (int i = 0; i < Math.min(stackTrace.length, 5); i++) {
                        %>
                        &nbsp;&nbsp;&nbsp;&nbsp;at <%= stackTrace[i] %><br>
                        <%
                            }
                            if (stackTrace.length > 5) {
                        %>
                        &nbsp;&nbsp;&nbsp;&nbsp;...<br>
                        <%
                            }
                        %>
                    </code>
                </div>
                <%
                } else {
                %>
                <div class="exception-stack">
                    <code>
                        java.lang.NullPointerException: Cannot invoke "java.sql.Connection.setAutoCommit(boolean)" because "org.example.conexao.ConexooManager.com" is null<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;org.example.conexao.ConexooManager.conector(ConexaoManager.java:32)<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;org.example.dao.AdministradorDAO.listarAdministradorPortEmail(AdministradorDAO.java:78)<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;org.example.servlet.controls.ServletLogin.doPost(ServletLogin.java:39)<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)<br>
                    </code>
                </div>
                <%
                    }
                %>
            </div>
        </section>

        <section class="note-section">
            <div class="note">
                <strong>Note:</strong> A pilha de erros completa da causa principal está disponível nos logs do servidor.
            </div>
        </section>

        <div class="action-buttons">
            <button onclick="window.history.back()" class="btn btn-back">Voltar</button>
            <button onclick="window.location.reload()" class="btn btn-reload">Recarregar</button>
            <button onclick="window.location.href='${pageContext.request.contextPath}'" class="btn btn-home">Página Inicial</button>
        </div>
    </div>
</div>
</body>
</html>