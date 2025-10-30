package org.example.servlet.Adm;

//Classe criada com objetivo de realizar deleções de registros da tabela Administrador através de inteface JSP

//Importações
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.AdministradorDAO;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;
import org.example.model.Administrador;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/DeletarAdm")

//Abertura da classe de Servlet
public class ServletDeletarAdm extends HttpServlet {

    //Metodo doPost com 3 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try{
         //Recebe qual ação será feita
        int action = Integer.parseInt(req.getParameter("action"));

        //Recebe o id do registro no banco de dados
        int id = Integer.parseInt(req.getParameter("id"));

        //Objeto com metodos responsáveis por realizar ações na tabela administrador do banco de dados
        AdministradorDAO admdao = new AdministradorDAO();

        //Primeira ação (Abre pop-up)
        if (action == 0) {
            //Lista o registro com determinado id do administrador do banco de dados
            Administrador adm = admdao.listarAdministradorPorId(id);

            //Seta o administrador no CRUD
            req.setAttribute("administrador", adm);

            //Ativa o pop-up de deleção
            req.setAttribute("popup-deletar", true);

            //Envia dados para o CRUD da tabela Administrador
            req.getRequestDispatcher("/private/ListarAdministradores").forward(req, resp);
        } 
        //Segunda ação (Confirma deleção)
        else if (action == 1) {

            //Deleta o administrador pelo id
            admdao.deletarAdministrador(id);

            //Lista todos os administradores da tabela e seta no JSP
            List<Administrador> administradores = admdao.listarAdministradores();
            req.setAttribute("administradores", administradores);

            //Mensagem de sucesso
            req.setAttribute("erro", "Adm deletado com sucesso");

            //Manda dados para o CRUD da tabela Administrador
            req.getRequestDispatcher("/WEB-INF/view/CrudAdm.jsp").forward(req, resp);
        } 
        //Terceira ação (Cancela deleção)
        else if (action == 2) {
            //Lista todos os administradores da tabela e seta no CRUD da tabela Administrador
            List<Administrador> administradores = admdao.listarAdministradores();
            req.setAttribute("administradores", administradores);
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


    //Metodo com ações para ser executadas no fim do servlet
    public void destroy() {
        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}
