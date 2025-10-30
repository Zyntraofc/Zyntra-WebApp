package org.example.servlet.MotivoFalta;

///Classe criada com objetivo de realizar a alteração de registros da tabela MotivoFalta através da interface JSP

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

//ENDPOINT privado e com filtro do servlet (Área restrita)
@WebServlet("/private/AlterarMotivoFalta")

//Abertura da classe de Servlet
public class ServletAlterarMotivoFalta extends HttpServlet {

    //Metodo doPost com 2 ações diferentes, não manda informações pela URL
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Recebe qual ação será realizada
            int action = Integer.parseInt(req.getParameter("action"));

            //Recebe o id do registro que será modificado
            int id = Integer.parseInt(req.getParameter("id"));

            //Criando objeto com metodo responsáveis por ações na tabela MotivoFalta do banco de dados
            MotivoFaltaDAO motivodao = new MotivoFaltaDAO();

            //Primeira ação (Abre o pop-up de alteração)
            if (action == 0) {
                //Objeto motivo com valores do registro retornado pelo metodo "listarMotivoFaltaPorID"
                MotivoFalta motivo = motivodao.listarMotivoFaltaPorID(id);

                //Seta o motivo
                req.setAttribute("motivo", motivo);

                //Ativa o pop-up de alteração
                req.setAttribute("popup-alterar", true);

                //Carrega dados no CRUD com pop-up ativo
                req.getRequestDispatcher("/private/ListarMotivosFalta").forward(req, resp);

            } 
            //Segunda ação (Altera motivo falta do banco de dados)
            else if (action == 1) {
                //Recebe valor do motivo do parâmetro
                String motivo = req.getParameter("motivo");

                //Altera o motivo no banco de dados e verifica sucesso
                if (motivodao.alterarMotivoMotivoFalta(id, motivo)) {
                    //Retorna que o motivo foi alterado com sucesso
                    req.setAttribute("erro", "Motivo atualizado com sucesso");
                } else {
                    //Retorna erro genérico
                    req.setAttribute("erro", "Erro!");
                }

                //Lista todos os motivos
                List<MotivoFalta> motivos = motivodao.listarMotivosFalta();

                //Manda todos os motivos para serem listados no CRUD
                req.setAttribute("motivos", motivos);

                //Carrega dados no CRUD
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

    //Metodo com ações para ser executadas antes do fim do Servlet
    public void destroy() {

        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}