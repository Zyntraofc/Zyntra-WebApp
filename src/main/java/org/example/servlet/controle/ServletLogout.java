package org.example.servlet.controle;

/// Classe para fazer o logout e deslogar o usuário

//Importações
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//ENDPOINT do logout (Não é uma página restrita)
@WebServlet("/Logout")

//Abertura da classe de Servlet
public class ServletLogout extends HttpServlet{

    //Metodo doGet para remover usuário da sessão e encaminhar para a landingPage
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //Abrindo a sessão
        HttpSession session = req.getSession(false);
        //Removendo o usuário logado da sessão
        session.removeAttribute("usuario");
        //Enviando para index.html
        req.getRequestDispatcher("index.html").forward(req, resp);
    }
}
