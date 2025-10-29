package org.example.servlet.controle;

/// Classe criada com objetivo de autorizar e fazer o login para área restrita na página "login.jsp"

//Importações
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.AdministradorDAO;
import org.example.utils.autenticacao.HashSenha;
import org.example.model.Administrador;

//ENDPOINT do login (Não é uma página restrita)
@WebServlet("/LoginAdministrador")

//Abertura da classe de Servlet
public class ServletLogin extends HttpServlet {

    //Metodo doGet que carregar página de login
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }


    //Metodo doPost que autorizará o login com base na senha e email do usuário
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Atributos de senha e email que receberam parametros dos inputs "email" e "senha"
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        //Gerando atributo de administradores listados através do email
        AdministradorDAO adminDao = new AdministradorDAO();
        Administrador listagem = adminDao.listarAdministradorPorEmail(email);

        //Se a listagem pelo email não for nula, verifica a senha, se for, envia erro para página de login
        if (listagem != null) {
            //Gera o HashSenha da senha e converte para String para realizar a comparação
            String senhaCriptografada = String.valueOf(new HashSenha(senha));
            //Se o hashSenha da listagem for igual ao HashSenha: Inicia a nova sessão, loga o usuário e passa para a página de CRUD
            if (listagem.getHashSenha().equals(senhaCriptografada)) {
                //Inicia nova sessão e loga o usuário
                HttpSession session = req.getSession(true);
                session.setAttribute("usuario", listagem.getEmail());

                //Envia para a página de CRUD de empresas
                req.getRequestDispatcher("private/ListarEmpresas").forward(req, resp);
            }
            //Se não, seta erro na página de login
            else {
                req.setAttribute("erroLogin", "Usuário ou senha incorretos!");

                //Insere o email e a senha que já foram escritos, para o valor continuar nos inputs
                req.setAttribute("emailDigitado", email);
                req.setAttribute("senhaDigitada", senha);

                //Envia para a página de login
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("erroLogin", "Usuário ou senha incorretos!");

            //Insere o email e a senha que já foram escritos, para o valor continuar nos inputs
            req.setAttribute("emailDigitado", email);
            req.setAttribute("senhaDigitada", senha);

            //Envia para a página de login
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    //Metodo destroy que desconectará do banco de dados antes de finalizar o Servlet
    public void destroy(){
        ConexaoManager.desconectar();
    }
}


