package org.example.servlet.Adm;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.dao.EmpresaDAO;
import org.example.model.Administrador;
import org.example.model.Empresa;
import java.util.List;

@WebServlet("/ListarEmpresas")
public class ServletListarAdm extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        AdministradorDAO administradorDAO = new AdministradorDAO();
        List<Administrador> administradores = administradorDAO.listarAdministradores();
        req.setAttribute("administradores", administradores);
        req.getRequestDispatcher("view/CrudAdm.jsp").forward(req, resp);
    }

}
