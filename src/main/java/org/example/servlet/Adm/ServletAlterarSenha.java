package org.example.servlet.Adm;

///Classe Servlet criada com objetivo de realizar a ação de alterar a senha do registro na tabela Administrador através de uma interface JSP

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
import org.example.utils.regex.ValidacaoSenha;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/AlterarSenha")

//Abertura da classe de Servlet
public class ServletAlterarSenha extends HttpServlet {

    //Metodo doPost com 2 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try{
            //Recebe qual ação será realizada
        int action = Integer.parseInt(req.getParameter("action"));

        //Recebe o id do registro que será modificado
        int id = Integer.parseInt(req.getParameter("id"));

        //Variável de controle de erros e mensagens de resposta
        int resposta = 0;

        //Objeto com metodos responsáveis por realizar ações na tabela Administrador do banco de dados
        AdministradorDAO admdao = new AdministradorDAO();

        //Primeira ação (Abre o pop-up de alteração da senha)
        if (action == 0) {

            //Lista o registro com determinado id do administrador do banco de dados
            Administrador adm = admdao.listarAdministradorPorId(id);

            //Seta o administrador no CRUD
            req.setAttribute("administrador", adm);

            //Ativa o pop-up de senha
            req.setAttribute("popup-senha", true);

            //Seta todos os dados e encaminha para o CRUD dos administradores
            req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
        } 
        //Segunda ação (Realiza alteração da senha)
        else if (action == 1) {
            //Recebe a senha antiga (para autenticação), e a senha nova (para alteração)
            String senhaAtual = req.getParameter("senhaAtual");
            String senhaNova = req.getParameter("senhaNova");

            //Lista o administrador pelo id
            Administrador adm = admdao.listarAdministradorPorId(id);

            //Criptografa e gera o hash da senha para autenticar
            HashSenha hashAtual = new HashSenha(senhaAtual);

            //Compara os hashs
            if (adm.getHashSenha().equals(hashAtual.getHashSenha())) {

                //Se for igual valida a senha
                if (ValidacaoSenha.validarSenha(senhaNova)) {
                    //Criptografa e gera do hash da nova senha
                    HashSenha hs = new HashSenha(senhaNova);

                    //Atualiza a senha do banco de dados conforme o id 
                    admdao.alterarSenhaAdministrador(id, hs.getHashSenha());

                } 
                //Se a senha não for válida
                else {
                    req.setAttribute("erroSenha", "A senha precisa ter no mínimo 8 caractéres, 1 letra maiúscula, 1 minúscula e 1 caractére especial");
                    resposta++;
                }
            }

            //Se tudo der certo retorna que a senha foi alterada com sucesso
            if (resposta == 0) {
                req.setAttribute("erroSenha", "Senha atualizada com sucesso");
            }

            //Novo objeto com a senha já atualizada, se ela tiver sido
            Administrador administrador = admdao.listarAdministradorPorId(id);

            //Seta o administrador atualizado
            req.setAttribute("administrador", administrador);

            //Ativa o pop-up de alteração normal
            req.setAttribute("popup-alterar", true);

            //Carrega dados no CRUD com pop-up ativado
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


    //Metodo com ações para serem executadas antes do fim do Servlet
    public void destroy() {
        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}

