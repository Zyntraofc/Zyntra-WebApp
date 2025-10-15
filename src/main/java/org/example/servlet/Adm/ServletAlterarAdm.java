package org.example.servlet.Adm;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.model.Administrador;
import org.example.utils.regex.ValidacaoEmail;

@WebServlet("/AlterarAdm")
public class ServletAlterarAdm extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        int resposta = 0;
        AdministradorDAO admdao = new AdministradorDAO();
        ValidacaoEmail valemail = new ValidacaoEmail();
        if (action == 0){
            Administrador adm = admdao.listarAdministradorPorId(id);
            req.setAttribute("administrador", adm);
            req.setAttribute("popup-alterar", true);
            req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
        }
        else if (action == 1) {
            String email = req.getParameter("email");
            Administrador adm = admdao.listarAdministradorPorId(id);

            // atualizações
            if (!email.equals(adm.getEmail())) {
                if (valemail.validarEmail(email)) {
                    admdao.alterarEmailAdministrador(id, email);
                } else {
                    req.setAttribute("erro", "Email inválido");
                    resposta++;
                }
            }

            Administrador admAtualizado = admdao.listarAdministradorPorId(id);
            req.setAttribute("administrador", admAtualizado);
            if(resposta ==0){
                req.setAttribute("erro", "Adm atualizado com sucesso");
            }
            java.util.List<Administrador> administradores = admdao.listarAdministradores();
            req.setAttribute("administradores", administradores);
            req.getRequestDispatcher("view/CrudAdm.jsp").forward(req, resp);
        }
    }
}
