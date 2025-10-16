package org.example.servlet.controle;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/Logout")
public class ServletLogout extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        session.removeAttribute("usuario");
        req.getRequestDispatcher("index.html").forward(req, resp);
    }
}
