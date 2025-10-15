package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;
import java.util.List;

@WebServlet("/ListarEmpresas")
public class ServletListarEmpresas extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        TipoEmpresaDAO tipodao = new TipoEmpresaDAO();
        req.setAttribute("tipos", tipodao.listarTiposEmpresa());
        IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();
        req.setAttribute("statuses", indicedao.listarIndicesClassificacao());
        EmpresaDAO empresadao = new EmpresaDAO();
        List<Empresa> empresas = empresadao.listarEmpresas();
        req.setAttribute("empresas", empresas);
        req.getRequestDispatcher("view/CrudEmpresa.jsp").forward(req, resp);
    }

}
