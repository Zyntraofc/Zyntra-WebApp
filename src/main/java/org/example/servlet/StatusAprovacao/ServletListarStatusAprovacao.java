package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.StatusAprovacao;
import java.util.List;

@WebServlet("/ListarStatusAprovacao")
public class ServletListarStatusAprovacao extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        if (req.getAttribute("popup-inserir")!=null){
            TipoEmpresaDAO tipodao = new TipoEmpresaDAO();
            req.setAttribute("tipos", tipodao.listarTiposEmpresa());
        }
        StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
        List<StatusAprovacao> statuses = statusdao.listarTodosStatusAprovacao();
        req.setAttribute("statuses", statuses);
        req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
    }
}
