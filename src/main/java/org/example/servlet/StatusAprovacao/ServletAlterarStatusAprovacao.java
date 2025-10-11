package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;
import java.util.List;

@WebServlet("/AlterarStatusAprovacao")
public class ServletAlterarStatusAprovacao extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
        StatusAprovacao statusID = statusdao.listarStatusAprovacaoPorID(id);
        if (action == 0){
            StatusAprovacao status = statusdao.listarStatusAprovacaoPorID(id);
            req.setAttribute("alterarStatus", status);
            req.setAttribute("popup-alterar", true);
            List<StatusAprovacao> statuses = statusdao.listarTodosStatusAprovacao();
            req.setAttribute("statuses", statuses);
            req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
        }
        else if (action == 1) {
            String status = req.getParameter("status");
            String motivoRejeicao = req.getParameter("motivoRejeicao");
            // atualizações
            if (statusID.getStatus() != status.charAt(0)) {
                if (statusdao.alterarStatusStatusAprovacao(id, status.charAt(0))) req.setAttribute("erro", "Atualizado com sucesso");
                else req.setAttribute("erro", "Erro ao atualizar status!");}
            if (!statusID.getMotivoRejeicao().equals(motivoRejeicao) && status.charAt(0) == 'r') {
                if (statusdao.alterarMotivoStatusAprovacao(id, motivoRejeicao)) req.setAttribute("erro", "Atualizado com sucesso");
                else req.setAttribute("erro", "Erro ao atualizar motivo!");}
            }

            java.util.List<StatusAprovacao> statuses = statusdao.listarTodosStatusAprovacao();
            req.setAttribute("statuses", statuses);
            req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
        }
    }

