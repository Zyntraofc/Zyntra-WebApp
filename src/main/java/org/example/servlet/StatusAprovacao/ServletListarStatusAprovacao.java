package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.StatusAprovacao;
import org.example.model.Empresa;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.example.utils.filtros.FiltrosStatusAprovacao;
@WebServlet("/private/ListarStatusAprovacao")
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
        Character statusParaOrdenar = req.getParameter("ordenarStatus") != null && !req.getParameter("ordenarStatus").isEmpty() ? req.getParameter("ordenarStatus").charAt(0) : null;
        boolean ordenarStatus = statusParaOrdenar != null && (statusParaOrdenar == 'a' || statusParaOrdenar == 'p' || statusParaOrdenar == 'r');
        String atualizacoesOrdenarString = req.getParameter("ordenarAtualizacoes");
        boolean ordenarAtualizacoes = false;
        boolean recente = false;
        if(atualizacoesOrdenarString != null && !atualizacoesOrdenarString.isEmpty()){
            ordenarAtualizacoes = true;
            recente = atualizacoesOrdenarString.equals("2");
        }

        FiltrosStatusAprovacao filtrar = new FiltrosStatusAprovacao();

        List<StatusAprovacao> statuses = filtrar.ordenarStatusAprovacao(statusdao.listarTodosStatusAprovacao(), ordenarStatus, statusParaOrdenar, ordenarAtualizacoes, recente);
        req.setAttribute("statuses", statuses);

        EmpresaDAO empresadao = new EmpresaDAO();
        Map<Integer, String> nomesEmpresas = new HashMap<>();
        for (StatusAprovacao s : statuses) {
            Empresa empresa = empresadao.listarEmpresaPorIdStatusAprovacao(s.getId());
            if (empresa != null) {
                nomesEmpresas.put(s.getId(), empresa.getNome());
            } else {
                nomesEmpresas.put(s.getId(), "N/A");
            }
        }
        req.setAttribute("nomesEmpresas", nomesEmpresas);

        req.getRequestDispatcher("/WEB-INF/view/CrudStatusAprovacao.jsp").forward(req, resp);
    }
    public void destroy(){
        ConexaoManager.desconectar();
    }
}