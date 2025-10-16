package org.example.servlet.Adm;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.AdministradorDAO;
import org.example.model.Administrador;

@WebServlet("/private/DeletarAdm")
public class ServletDeletarAdm extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        AdministradorDAO admdao = new AdministradorDAO();
        if(action == 0){
            Administrador adm = admdao.listarAdministradorPorId(id);
            req.setAttribute("administrador", adm);
            req.setAttribute("popup-deletar", true);
            req.getRequestDispatcher("private/ListarAdministradores").forward(req, resp);
        }else if(action == 1){
            admdao.deletarAdministrador(id);
            List<Administrador> administradores = admdao.listarAdministradores();
            req.setAttribute("administradores", administradores);
            req.setAttribute("erro", "Adm deletado com sucesso");
            req.getRequestDispatcher("/WEB-INF/view/CrudAdm.jsp").forward(req, resp);
        }else if(action == 2) {
            java.util.List<Administrador> administradores = admdao.listarAdministradores();
            req.setAttribute("administradores", administradores);
            req.getRequestDispatcher("/WEB-INF/view/CrudAdm.jsp").forward(req, resp);
        }
        ConexaoManager.desconectar();
    }
}
