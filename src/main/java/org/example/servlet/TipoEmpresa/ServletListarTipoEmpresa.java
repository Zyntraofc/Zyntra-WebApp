package org.example.servlet.TipoEmpresa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;
import org.example.regex.*;

@WebServlet("/ListarTipoEmpresa")
public class ServletListarTipoEmpresa extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
        List<TipoEmpresa> tiposEmpresa = tipoempresadao.listarTiposEmpresa();
        req.setAttribute("tiposEmpresa", tiposEmpresa);
        req.getRequestDispatcher("view/CrudTipoEmpresa.jsp").forward(req, resp);
    }
}
