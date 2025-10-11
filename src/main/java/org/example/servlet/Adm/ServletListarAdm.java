package org.example.servlet.Adm;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.model.Administrador;
import java.util.List;

@WebServlet("/ListarAdministradores")
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
        req.getRequestDispatcher("view/CrudAdm.jsp").forward(req, resp);
    }

}
