package org.example.servlet.TipoEmpresa;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;

@WebServlet("/private/ListarTipoEmpresa")
public class ServletListarTipoEmpresa extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
        List<TipoEmpresa> tiposEmpresa = tipoempresadao.listarTiposEmpresa();
        req.setAttribute("tiposEmpresa", tiposEmpresa);
        req.getRequestDispatcher("/WEB-INF/view/CrudTipoEmpresa.jsp").forward(req, resp);
        ConexaoManager.desconectar();
    }
}
