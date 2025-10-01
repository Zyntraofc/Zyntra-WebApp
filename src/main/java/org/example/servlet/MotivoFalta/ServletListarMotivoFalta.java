package org.example.servlet.MotivoFalta;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;

import java.util.List;

@WebServlet("/ListarMotivosFalta")
public class ServletListarMotivoFalta extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        MotivoFaltaDAO motivodao = new MotivoFaltaDAO();
        List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
        req.setAttribute("motivos", motivos);
        req.getRequestDispatcher("view/CrudMotivoFalta.jsp").forward(req, resp);
    }

}
