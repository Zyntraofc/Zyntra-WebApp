package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.Empresa;

@WebServlet("/DeletarEmpresa")
public class ServletDeletarEmpresa extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        int action = Integer.parseInt(req.getParameter("action"));
        EmpresaDAO empresadao = new EmpresaDAO();
        if(action == 0){
            Empresa empresa = null;
            if (req.getParameter("idStatus") != null && !req.getParameter("idStatus").isEmpty()) {empresa = empresadao.listarEmpresaPorIdStatusAprovacao(Integer.parseInt(req.getParameter("idStatus")));}
            else{
                int id = Integer.parseInt(req.getParameter("id"));
                empresa = empresadao.listarEmpresaPorId(id);
            }
            req.setAttribute("empresa", empresa);
            req.getRequestDispatcher("view/DeletarEmpresa.jsp").forward(req, resp);
        }else if(action == 1){
            int id = Integer.parseInt(req.getParameter("id"));
            int idStatus = Integer.parseInt(req.getParameter("idStatus"));
            StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
            empresadao.deletarEmpresa(id);
            statusdao.deletarStatusAprovacao(idStatus);
            req.setAttribute("erro", "Empresa deletada com sucesso");
            req.getRequestDispatcher("ListarEmpresas").forward(req, resp);
        }else if(action == 2){
            req.getRequestDispatcher("ListarEmpresas").forward(req, resp);
        }
    }

}
