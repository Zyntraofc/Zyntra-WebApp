package org.example.servlet.Adm;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.dao.HashSenha;
import org.example.model.Administrador;
import org.example.regex.*;

@WebServlet("/AlterarSenha")
public class ServletAlterarSenha extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        int resposta = 0;
        AdministradorDAO admdao = new AdministradorDAO();
        if (action == 0){
            Administrador adm = admdao.listarAdministradorPorId(id);
            req.setAttribute("administrador", adm);
            req.setAttribute("popup-senha", true);
            req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
        }
        else if (action == 1) {
            String senhaAtual = req.getParameter("senhaAtual");
            String senhaNova = req.getParameter("senhaNova");
            Administrador adm = admdao.listarAdministradorPorId(id);

            // atualizações
            //
            HashSenha hashAtual = new HashSenha(senhaAtual);
            if (adm.getHashSenha().equals(hashAtual.getHashSenha())){
                if (ValidacaoSenha.validarSenha(senhaNova)){
                    HashSenha hs = new HashSenha(senhaNova);
                    admdao.alterarSenhaAdministrador(id, hs.getHashSenha());
                } else {
                    req.setAttribute("erroSenha", "Senha inválida");
                    resposta++;
                }
            }

            if(resposta ==0){
                req.setAttribute("erroSenha", "Senha atualizada com sucesso");
            }
            Administrador administrador = admdao.listarAdministradorPorId(id);
            req.setAttribute("administrador", administrador);
            req.setAttribute("popup-alterar", true);
            req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
        }
    }
}

