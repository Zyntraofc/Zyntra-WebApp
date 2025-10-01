package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;

@WebServlet("/AlterarStatusAprovacao")
public class ServletAlterarStatusAprovacaoo extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
        if (action == 0){
            StatusAprovacao status = statusdao.listarStatusAprovacaoPorID(id);
            req.setAttribute("status", status);
            req.getRequestDispatcher("view/AlterarStatusAprovacao.jsp").forward(req, resp);
        }
        else if (action == 1) {
            String status = req.getParameter("status");
            // atualizações
            if(statusdao.alterarStatusStatusAprovacao(id, status.charAt(0))) req.setAttribute("erro", "Status atualizado com sucesso");
            else req.setAttribute("erro", "Erro!");
            java.util.List<StatusAprovacao> statuses = statusdao.listarTodosStatusAprovacao();
            req.setAttribute("status", statuses);
            req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
        }
    }
}
