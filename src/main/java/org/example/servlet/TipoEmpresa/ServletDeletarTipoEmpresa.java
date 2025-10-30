package org.example.servlet.TipoEmpresa;

//Classe criada com objetivo de realizar deleções de registros da tabela TipoEmpresa através de interface JSP

//Importações
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/DeletarTipoEmpresa")

//Abertura da classe de Servlet
public class ServletDeletarTipoEmpresa extends HttpServlet {

    //Metodo doPost com 3 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Recebe qual ação será feita
            int action = Integer.parseInt(req.getParameter("action"));

            //Recebe o id do registro no banco de dados
            int id = Integer.parseInt(req.getParameter("id"));

            //Objeto com metodos responsáveis por realizar ações na tabela tipoEmpresa do banco de dados
            TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

            //Primeira ação (Abre pop-up)
            if (action == 0) {
                //Lista o registro com determinado id do tipoEmpresa do banco de dados
                TipoEmpresa tipoempresa = tipoempresadao.listarTipoEmpresaPorId(id);

                //Seta o tipoEmpresa no CRUD
                req.setAttribute("tipoEmpresa", tipoempresa);

                //Ativa o pop-up de deleção
                req.setAttribute("popup-deletar", true);

                //Envia dados para o CRUD da tabela TipoEmpresa
                req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
            } 
            //Segunda ação (Confirma deleção)
            else if (action == 1) {
                //Deleta o tipoEmpresa pelo id
                tipoempresadao.deletarTipoEmpresa(id);

                //Mensagem de sucesso
                req.setAttribute("erro", "Tipo empresa deletada com sucesso");

                //Envia dados para o CRUD da tabela TipoEmpresa
                req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
            } 
            //Terceira ação (Cancela deleção)
            else if (action == 2) {
                //Cancela a deleção e redireciona para o CRUD
                req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
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