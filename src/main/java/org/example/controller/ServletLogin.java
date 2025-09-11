package org.example.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.dao.HashSenha;
import org.example.model.Administrador;


@WebServlet("/LoginAdministrador")
public class ServletLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // caminho absoluto a partir do contexto
        req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        AdministradorDAO adminDao = new AdministradorDAO();
        Administrador listagem = adminDao.listarAdministradorPorEmail(email);

        if (listagem != null) {
            if (listagem.getHashSenha().equals(String.valueOf(new HashSenha(senha)))) {
                HttpSession session = req.getSession(true);
                session.setAttribute("usuarioLogado", listagem.getEmail());

                req.getRequestDispatcher("/pages/TestePaginaCrud.jsp").forward(req, resp);
            } else {
                req.setAttribute("erroLogin", "Senha incorreta!");
                req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("erroLogin", "Usuário não encontrado!");
            req.getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        }
    }
}


