package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;
import java.util.List;

@WebServlet("/ListarStatusAprovacao")
public class ServletListarStatusAprovacao extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
        List<StatusAprovacao> status = statusdao.listarTodosStatusAprovacao();
        req.setAttribute("status", status);
        req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
    }
}
