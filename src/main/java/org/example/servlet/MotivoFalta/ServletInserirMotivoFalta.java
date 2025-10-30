package org.example.servlet.MotivoFalta;

///Classe criada com objetivo de inserir novos registros no banco de dados através da interface em JSP

//Importações
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado com filtro do Servlet
@WebServlet("/private/InserirMotivoFalta")

//Abertura da classe de Servlet
public class ServletInserirMotivoFalta extends HttpServlet {

    //Metodo doGet para carregar pop-up de inserir
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //Ativa o pop-up de inserção
        req.setAttribute("popup-inserir", true);

        //Envia dados para o CRUD
        req.getRequestDispatcher("/private/ListarMotivosFalta").forward(req, resp);
    }

    //Metodo doPost para realizar inserção de motivoFalta no banco de dados
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        try {
            //Recebe motivo do input
            String motivo = req.getParameter("motivo");

            //Objeto com motivo do motivoFalta
            MotivoFalta motivoFalta = new MotivoFalta(motivo);

            //Objeto de acesso a tabela no banco de dados
            MotivoFaltaDAO motivodao = new MotivoFaltaDAO();

            //Insere motivoFalta no banco de dados
            motivodao.inserirMotivoFalta(motivoFalta);

            //Envia dados para o CRUD
            req.getRequestDispatcher("/private/ListarMotivosFalta").forward(req, resp);

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
            //Insere erro e exceção na página de erros
            req.setAttribute("erro", "Erro ao desfazer ação no banco de dados");
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