package org.example.servlet.Adm;

///Classe criada com objetivo de inserir novos registros no banco de dados através da interface em JSP

//Importações
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.AdministradorDAO;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;
import org.example.utils.autenticacao.HashSenha;
import org.example.model.Administrador;
import org.example.utils.regex.ValidacaoEmail;
import org.example.utils.regex.ValidacaoSenha;

//ENDPOINT privado com filtro do Servlet
@WebServlet("/private/InserirAdm")

//Abertura da classe de Servlet
public class ServletInserirAdm extends HttpServlet {

    //Metodo doGet para carregar pop-up de inserir
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //Ativa o pop-up de inserção
        req.setAttribute("popup-inserir", true);

        //Envia dados para o CRUD
        req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
    }

    //Metodo doPost para realizar inserção de administrador no banco de dados
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            //Recebe email do input
            String email = req.getParameter("email");
            //Recebe senha do input
            String senha = req.getParameter("senha");

            //Objeto para validar formato do email
            ValidacaoEmail valemail = new ValidacaoEmail();

            //Se o email for valido continua a inserção
            if (valemail.validarEmail(email)) {
                //Se a senha for válida insere adm
                if (ValidacaoSenha.validarSenha(senha)) {

                    //Criptografa e gera hash da senha
                    HashSenha hs = new HashSenha(senha);

                    //Objeto com email e hashsenha do administrador
                    Administrador adm = new Administrador(email, hs.getHashSenha());

                    //Objeto de acesso a tabela no banco de dados
                    AdministradorDAO dao = new AdministradorDAO();

                    //Insere e verifica se deu certo
                    if (dao.inserirAdministrador(adm)) {

                        //Retorna que inserção deu certo
                        req.setAttribute("erro", "Administrador inserido com sucesso");

                        //Envia dados para o CRUD
                        req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
                    } 
                    //Em caso de erro ao inserir
                    else {
                        //Retorna mensagem de erro ao inserir administrador
                        req.setAttribute("erro", "Erro ao inserir adm");

                        //Envia dados para o CRUD
                        req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
                    }
                } 
                //Em caso de erros na senha, informa
                else {
                    req.setAttribute("erro", "Senha: mínimo 8 caracteres, com maiúscula, minúscula e símbolo especial.");
                    req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
                }
            } 
            //Em caso de erros na validação do email, retorna que o email é inválido
            else {
                req.setAttribute("erro", "Email inválido");
                req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
            }

        } 
        
    //No caso de erros ao commitar ação no banco de dados
    catch(FailedCommitException fce){
        //Insere erro e exceção na página de erros
        req.setAttribute("erro", "Erro interno ao executar ação");
        req.setAttribute("exception", fce);
        //Envia dados para página de erros
        req.getRequestDispatcher("/WEB-INF/view/ErrorPage.jsp").forward(req, resp);
    }

    //No caso de erros ao conectar com o banco de dados
    catch(FailedConnectionException fce){
        //Insere erro e exceção na página de erros
        req.setAttribute("erro", "Erro ao conectar com banco de dados");
        req.setAttribute("exception", fce);
        //Envia dados para página de erro
        req.getRequestDispatcher("/WEB-INF/view/ErrorPage.jsp").forward(req, resp);
    }

    //Em casos de erros ao desfazer ações no banco de dados
    catch(RollbackException re){
        ////Insere erro e exceção na página de erros
        req.setAttribute("erro", "Erro iao desfazer ação no banco de dados");
        req.setAttribute("exception", re);
        //Envia dados para página de erro
        req.getRequestDispatcher("/WEB-INF/view/ErrorPage.jsp").forward(req, resp);
    }
    }

    public void destroy() {
        ConexaoManager.desconectar();
    }
}

