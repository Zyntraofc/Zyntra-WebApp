package org.example.servlet.Empresa;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;

@WebServlet("/DeletarEmpresa")
public class ServletDeletarEmpresa extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        int action = Integer.parseInt(req.getParameter("action"));
        EmpresaDAO empresadao = new EmpresaDAO();
        String caminho = req.getParameter("caminho");
        if(action == 0){
            req.setAttribute("caminho", caminho);
            Empresa empresa = null;
            if (req.getParameter("idStatus") != null && !req.getParameter("idStatus").isEmpty()) {empresa = empresadao.listarEmpresaPorIdStatusAprovacao(Integer.parseInt(req.getParameter("idStatus")));}
            else{
                int id = Integer.parseInt(req.getParameter("id"));
                empresa = empresadao.listarEmpresaPorId(id);
            }
            req.setAttribute("empresa", empresa);
            req.setAttribute("popup-deletar", true);
            req.getRequestDispatcher("Listar"+caminho).forward(req, resp);
        }else if(action == 1){
            int id = Integer.parseInt(req.getParameter("id"));
            int idStatus = Integer.parseInt(req.getParameter("idStatus"));
            int idTipoEmpresa = empresadao.listarEmpresaPorId(id).getIdTipoEmpresa();
            StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
            empresadao.deletarEmpresa(id);
            statusdao.deletarStatusAprovacao(idStatus);
            if(empresadao.listarEmpresaPorIdTipoEmpresa(idTipoEmpresa).size() == 0){
                TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
                tipoempresadao.alterarStatusTipoEmpresa(idTipoEmpresa, 'i');
                tipoempresadao.alterarUltimaAtualizacaoTipoEmpresa(idTipoEmpresa, LocalDate.now());
            }
            req.setAttribute("erro", "Empresa e Status deletados com sucesso");
            req.getRequestDispatcher("Listar"+caminho).forward(req, resp);
        }else if(action == 2){
            req.getRequestDispatcher("Listar"+caminho).forward(req, resp);
        }
    }

}
