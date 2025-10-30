package org.example.servlet.Adm;

///Classe criada com objetivo de realizar a alteração de registros da tabela Administrador através da interface JSP

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
import org.example.model.Administrador;
import org.example.utils.regex.ValidacaoEmail;
import java.util.List;

//ENDPOINT privado e com filtro do servlet (Área restrita)
@WebServlet("/private/AlterarAdm")

//Abertura da classe de Servlet
public class ServletAlterarAdm extends HttpServlet {

    //Metodo doPost com 2 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try{
            //Recebe qual ação será realizada
        int action = Integer.parseInt(req.getParameter("action"));

        //Recebe o id do registro que será modificado
        int id = Integer.parseInt(req.getParameter("id"));

        //Variável de controle e erros e respostas
        int resposta = 0;

        //Criando objeto com metodo responsáveis por ações na tabela Administrador do banco de dados
        AdministradorDAO admdao = new AdministradorDAO();

        //Objeto de validação de email
        ValidacaoEmail valemail = new ValidacaoEmail();

        //Primeira ação (Abre o pop-up de alteração)
        if (action == 0) {
            //Objeto administrador com valores do registro retornado pelo metodo "listarAdministradorPorId"
            Administrador adm = admdao.listarAdministradorPorId(id);

            //Seta o administrador
            req.setAttribute("administrador", adm);

            //Ativa o pop-up de alteração
            req.setAttribute("popup-alterar", true);

            //Carrega dados no CRUD com pop-up ativo
            req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);

        } 
        //Segunda ação (Altera administrador do banco de dados)
        else if (action == 1) {

            //Recebe valor do email do administrador
            String email = req.getParameter("email");

            //Lista o administrador pelo id do input
            Administrador adm = admdao.listarAdministradorPorId(id);

            // Verifica se houve alteração no email, se sim altera
            if (!email.equals(adm.getEmail())) {

                //Valida o email antes de alterar
                if (valemail.validarEmail(email)) {
                    //Atualiza email
                    admdao.alterarEmailAdministrador(id, email);
                } 
                //Se não for válido retorna erro e aumenta controle da resposta
                else {
                    req.setAttribute("erro", "Email inválido");
                    resposta++;
                }
            }

            
            //Novo objeto com o email já atualizado se tiver sido
            Administrador admAtualizado = admdao.listarAdministradorPorId(id);

            //Modifica o atributo administrador para o administrador atualizado
            req.setAttribute("administrador", admAtualizado);
            if (resposta == 0) {
                //Retorna que o administrador foi alterado com sucesso
                req.setAttribute("erro", "Adm atualizado com sucesso");
            }

            //Lista todos os administradores
            List<Administrador> administradores = admdao.listarAdministradores();

            //Manda todos os administradores para serem listados no CRUD
            req.setAttribute("administradores", administradores);

            //Carrega dados no CRUD com pop-up desativado
            req.getRequestDispatcher("/WEB-INF/view/CrudAdm.jsp").forward(req, resp);
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


    //Metodo com ações para ser executadas antes do fim do Servlet
    public void destroy() {

        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}
