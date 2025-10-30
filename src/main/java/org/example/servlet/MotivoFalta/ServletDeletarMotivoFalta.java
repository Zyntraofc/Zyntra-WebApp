package org.example.servlet.MotivoFalta;

//Classe criada com objetivo de realizar deleções de registros da tabela MotivoFalta através de interface JSP

//Importações
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.MotivoFaltaDAO;
import org.example.model.MotivoFalta;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/DeletarMotivoFalta")

//Abertura da classe de Servlet
public class ServletDeletarMotivoFalta extends HttpServlet {

    //Metodo doPost com 3 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Recebe qual ação será feita
            int action = Integer.parseInt(req.getParameter("action"));

            //Recebe o id do registro no banco de dados
            int id = Integer.parseInt(req.getParameter("id"));

            //Objeto com metodos responsáveis por realizar ações na tabela motivoFalta do banco de dados
            MotivoFaltaDAO motivodao = new MotivoFaltaDAO();

            //Primeira ação (Abre pop-up)
            if (action == 0) {
                //Lista o registro com determinado id do motivoFalta do banco de dados
                MotivoFalta motivo = motivodao.listarMotivoFaltaPorID(id);

                //Seta o motivo no CRUD
                req.setAttribute("motivo", motivo);

                //Ativa o pop-up de deleção
                req.setAttribute("popup-deletar", true);

                //Envia dados para o CRUD da tabela MotivoFalta
                req.getRequestDispatcher("/private/ListarMotivosFalta").forward(req, resp);
            } 
            //Segunda ação (Confirma deleção)
            else if (action == 1) {
                //Deleta o motivoFalta pelo id
                motivodao.deletarMotivoFalta(id);

                //Lista todos os motivosFalta da tabela e seta no JSP
                List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
                req.setAttribute("motivos", motivos);

                //Mensagem de sucesso
                req.setAttribute("erro", "Motivo deletado com sucesso");

                //Manda dados para o CRUD da tabela MotivoFalta
                req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);
            } 
            //Terceira ação (Cancela deleção)
            else if (action == 2) {
                //Lista todos os motivosFalta da tabela e seta no CRUD da tabela MotivoFalta
                List<MotivoFalta> motivos = motivodao.listarMotivosFalta();
                req.setAttribute("motivos", motivos);

                //Cancela a deleção e redireciona para o CRUD
                req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);
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