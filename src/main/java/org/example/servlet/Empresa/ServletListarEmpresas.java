package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.model.Empresa;
import java.util.List;

@WebServlet("/ListarEmpresas")
public class ServletListarEmpresas extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        EmpresaDAO empresadao = new EmpresaDAO();
        List<Empresa> empresas = empresadao.listarEmpresas();
        req.setAttribute("empresas", empresas);
        req.getRequestDispatcher("view/CrudEmpresa.jsp").forward(req, resp);
    }

}
