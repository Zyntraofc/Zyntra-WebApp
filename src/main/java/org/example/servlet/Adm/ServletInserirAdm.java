package org.example.servlet.Adm;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.AdministradorDAO;
import org.example.dao.EmpresaDAO;
import org.example.dao.HashSenha;
import org.example.model.Administrador;
import org.example.model.Empresa;

import java.util.InputMismatchException;
import java.util.List;
import org.example.regex.*;

@WebServlet("/InserirAdm")
public class ServletInserirAdm extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        req.getRequestDispatcher("view/InserirAdm.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        try{
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");
            HashSenha hs = new HashSenha(senha);
            ValidacaoEmail valemail = new ValidacaoEmail();
            if(valemail.validarEmail(email)){
                Administrador adm = new Administrador(email, hs.getHashSenha());
                AdministradorDAO dao = new AdministradorDAO();
                if(dao.inserirAdministrador(adm)){
                    req.setAttribute("erro", "Empresa inserida com sucesso");
                    req.getRequestDispatcher("view/InserirAdm.jsp").forward(req, resp);
                }else{
                    req.setAttribute("erro", "Erro ao inserir a empresa");
                    req.getRequestDispatcher("view/InserirAdm.jsp").forward(req, resp);
                }
            }else{
                req.setAttribute("erro", "Digite o email corretamente");
                req.getRequestDispatcher("view/InserirAdm.jsp").forward(req, resp);
            }

        }catch(InputMismatchException ime){
            req.setAttribute("erro", "Digite os dados corretamente ");
            req.getRequestDispatcher("view/InserirAdm.jsp").forward(req, resp);
        }
    }
}

