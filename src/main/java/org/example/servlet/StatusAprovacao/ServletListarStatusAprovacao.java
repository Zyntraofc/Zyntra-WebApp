package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.StatusAprovacao;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

        EmpresaDAO empresadao = new EmpresaDAO();
        // cria um mapa onde a chave é o id do status e o valor é o nome da empresa
        Map<Integer, String> nomesEmpresas = new HashMap<>();
        for (StatusAprovacao s : statuses) {
            String nomeEmpresa = empresadao.listarEmpresaPorIdStatusAprovacao(s.getId()).getNome();
            nomesEmpresas.put(s.getId(), nomeEmpresa);
        }
        req.setAttribute("nomesEmpresas", nomesEmpresas);

        req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
    }
}
