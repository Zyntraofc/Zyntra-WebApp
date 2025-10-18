package org.example.servlet.controle;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;

@WebServlet("/Autenticar")
public class ServletAutenticacao extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String endpoint = req.getParameter("endpointInput");
        HttpSession session = req.getSession(true);
        session.setAttribute("usuario", req.getParameter("usuario"));
        req.getRequestDispatcher(endpoint).forward(req, resp);
    }

}
