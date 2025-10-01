package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.model.Empresa;

@WebServlet("/DeletarEmpresa")
public class ServletDeletarEmpresa extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        EmpresaDAO empresadao = new EmpresaDAO();

        if(action == 0){
            Empresa empresa = empresadao.listarEmpresaPorId(id);
            req.setAttribute("empresa", empresa);
            req.getRequestDispatcher("view/DeletarEmpresa.jsp").forward(req, resp);
        }else if(action == 1){

            empresadao.deletarEmpresa(id);
            req.setAttribute("erro", "Empresa deletada com sucesso");
            req.getRequestDispatcher("ListarEmpresas").forward(req, resp);
        }else if(action == 2){
            req.getRequestDispatcher("ListarEmpresas").forward(req, resp);
        }
    }

}
