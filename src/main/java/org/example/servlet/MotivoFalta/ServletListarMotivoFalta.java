package org.example.servlet.MotivoFalta;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;

import java.util.List;

@WebServlet("/private/ListarMotivosFalta")
public class ServletListarMotivoFalta extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        MotivoFaltaDAO motivodao = new MotivoFaltaDAO();
        List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
        req.setAttribute("motivos", motivos);
        req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);
    }
    public void destroy(){
        ConexaoManager.desconectar();
    }

}
