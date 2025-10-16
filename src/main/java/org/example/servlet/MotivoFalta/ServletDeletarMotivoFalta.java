package org.example.servlet.MotivoFalta;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;

@WebServlet("/private/DeletarMotivoFalta")
public class ServletDeletarMotivoFalta extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        MotivoFaltaDAO motivodao = new MotivoFaltaDAO();
        if(action == 0){
            MotivoFalta motivo = motivodao.listarMotivoFaltaPorID(id);
            req.setAttribute("motivo", motivo);
            req.setAttribute("popup-deletar", true);
            req.getRequestDispatcher("private/ListarMotivosFalta").forward(req, resp);
        }else if(action == 1){
            motivodao.deletarMotivoFalta(id);
            java.util.List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
            req.setAttribute("motivos", motivos);
            req.setAttribute("erro", "Motivo deletado com sucesso");
            req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);
        }else if(action == 2) {
            java.util.List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
            req.setAttribute("motivos", motivos);
            req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);
        }
        ConexaoManager.desconectar();
    }
}