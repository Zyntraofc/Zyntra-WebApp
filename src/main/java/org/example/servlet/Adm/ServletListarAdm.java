package org.example.servlet.Adm;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.AdministradorDAO;
import org.example.model.Administrador;
import java.util.List;

@WebServlet("/private/ListarAdministradores")
public class ServletListarAdm extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        AdministradorDAO administradorDAO = new AdministradorDAO();
        List<Administrador> administradores = administradorDAO.listarAdministradores();
        req.setAttribute("administradores", administradores);

        //Verificação para direcionar se for um cancelamento de alteração na senha
        // Lendo parâmetros
        String popupAlterar = req.getParameter("popup-alterar");
        String id = req.getParameter("id");
        if ("true".equals(popupAlterar) && id != null) {
            req.setAttribute("popup-alterar", true);
            Administrador admin = administradorDAO.listarAdministradorPorId(Integer.parseInt(id));
            req.setAttribute("administrador", admin);
        }

        req.getRequestDispatcher("/WEB-INF/view/CrudAdm.jsp").forward(req, resp);
        ConexaoManager.desconectar();
    }

}
