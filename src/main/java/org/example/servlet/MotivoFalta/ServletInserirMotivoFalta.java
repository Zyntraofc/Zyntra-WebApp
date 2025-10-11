package org.example.servlet.MotivoFalta;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;

@WebServlet("/InserirMotivoFalta")
public class ServletInserirMotivoFalta extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        req.setAttribute("popup-inserir", true);
        req.getRequestDispatcher("ListarMotivosFalta").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        String motivo = req.getParameter("motivo");
        MotivoFalta motivoFalta = new MotivoFalta(motivo);
        MotivoFaltaDAO motivodao = new MotivoFaltaDAO();
        motivodao.inserirMotivoFalta(motivoFalta);
        req.getRequestDispatcher("ListarMotivosFalta").forward(req, resp);
    }
}
