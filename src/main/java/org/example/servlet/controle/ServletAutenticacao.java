package org.example.servlet.controle;

/// Classe criada com objetivo de realizar autenticação trocar de páginas restritas no sistema
/// É uma forma de autoriz

//Importações
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//ENDPOINT do servlet
@WebServlet("/Autenticar")

//Abertura da classe de Servlet
public class ServletAutenticacao extends HttpServlet{

    //Metodo doPost que realizará a troca de página na área restrita
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //Pega endpoint do input hidden do JSP
        String endpoint = req.getParameter("endpointInput");

        //Inicia nova sessão com valor da atual
        HttpSession session = req.getSession(true);

        //Pega valor do usuário que está no input da página
        Object usuario = req.getParameter("usuario");

        //Seta o atributo na nova sessão
        session.setAttribute("usuario", usuario);

        //Encaminha para o endpoint de página restrita com a sessão autorizada
        req.getRequestDispatcher(endpoint).forward(req, resp);
    }

}
