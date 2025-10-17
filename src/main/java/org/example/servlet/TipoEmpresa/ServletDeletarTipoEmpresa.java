package org.example.servlet.TipoEmpresa;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;
import org.example.model.TipoEmpresa;

@WebServlet("/private/DeletarTipoEmpresa")
public class ServletDeletarTipoEmpresa extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

        if(action == 0){
            TipoEmpresa tipoempresa = tipoempresadao.listarTipoEmpresaPorId(id);
            req.setAttribute("tipoEmpresa", tipoempresa);
            req.setAttribute("popup-deletar", true);
            req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
        }else if(action == 1){
            tipoempresadao.deletarTipoEmpresa(id);
            req.setAttribute("erro", "Tipo empresa deletada com sucesso");
            req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
        }else if(action == 2){
            req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
        }
        ConexaoManager.desconectar();
    }
}
