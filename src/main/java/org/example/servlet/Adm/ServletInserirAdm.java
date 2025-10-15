package org.example.servlet.Adm;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.utils.autenticacao.HashSenha;
import org.example.model.Administrador;
import java.util.InputMismatchException;

import org.example.utils.regex.ValidacaoEmail;
import org.example.utils.regex.ValidacaoSenha;

@WebServlet("/InserirAdm")
public class ServletInserirAdm extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        req.setAttribute("popup-inserir", true);
        req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        try{
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");
            ValidacaoEmail valemail = new ValidacaoEmail();
            ValidacaoSenha valesenha = new ValidacaoSenha();
            if(valemail.validarEmail(email)){
                if (valesenha.validarSenha(senha)) {
                    HashSenha hs = new HashSenha(senha);
                    Administrador adm = new Administrador(email, hs.getHashSenha());
                    AdministradorDAO dao = new AdministradorDAO();
                    if (dao.inserirAdministrador(adm)) {
                        req.setAttribute("erro", "Administrador inserido com sucesso");
                        req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
                    } else {
                        req.setAttribute("erro", "Erro ao inserir adm");
                        req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
                    }
                } else {
                    req.setAttribute("erro", "Senha: mínimo 8 caracteres, com maiúscula, minúscula e símbolo especial.");
                    req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
                }
            }else{
                req.setAttribute("erro", "Digite o email corretamente");
                req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
            }

        }catch(InputMismatchException ime){
            req.setAttribute("erro", "Digite os dados corretamente ");
            req.getRequestDispatcher("ListarAdministradores").forward(req, resp);
        }
    }
}

