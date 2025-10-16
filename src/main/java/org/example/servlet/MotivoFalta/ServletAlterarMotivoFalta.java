package org.example.servlet.MotivoFalta;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;

@WebServlet("/private/AlterarMotivoFalta")
public class ServletAlterarMotivoFalta extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        MotivoFaltaDAO motivodao = new MotivoFaltaDAO();
        if (action == 0){
            MotivoFalta motivo = motivodao.listarMotivoFaltaPorID(id);
            req.setAttribute("motivo", motivo);
            req.setAttribute("popup-alterar", true);
            req.getRequestDispatcher("private/ListarMotivosFalta").forward(req, resp);
        }
        else if (action == 1) {
            String motivo = req.getParameter("motivo");

            // atualizações
            if(motivodao.alterarMotivoMotivoFalta(id, motivo)) req.setAttribute("erro", "Adm atualizado com sucesso");
            else req.setAttribute("erro", "Erro!");
            java.util.List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
            req.setAttribute("motivos", motivos);
            req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);
        }
        ConexaoManager.desconectar();
    }
}