package org.example.servlet.TipoEmpresa;

///Classe criada com objetivo de realizar a alteração de registros da tabela TipoEmpresa através da interface JSP

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

//ENDPOINT privado e com filtro do servlet (Área restrita)
@WebServlet("/private/AlterarTipoEmpresa")

//Abertura da classe de Servlet
public class ServletAlterarTipoEmpresa extends HttpServlet {

    //Metodo doPost com 2 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Recebe qual ação será realizada
            int action = Integer.parseInt(req.getParameter("action"));

            //Recebe o id do registro que será modificado
            int id = Integer.parseInt(req.getParameter("id"));

            //Criando objeto com metodo responsáveis por ações na tabela TipoEmpresa do banco de dados
            TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

            //Lista o tipoEmpresa pelo id do input
            TipoEmpresa tipoEmpresa = tipoempresadao.listarTipoEmpresaPorId(id);

            //Primeira ação (Abre o pop-up de alteração)
            if (action == 0) {
                //Seta o tipoEmpresa
                req.setAttribute("tipoEmpresa", tipoEmpresa);

                //Ativa o pop-up de alteração
                req.setAttribute("popup-alterar", true);

                //Carrega dados no CRUD com pop-up ativo
                req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);

            } 
            //Segunda ação (Altera tipoEmpresa do banco de dados)
            else if (action == 1) {
                //Recebe valores dos parâmetros do formulário
                String nome = req.getParameter("nome");
                String descricao = req.getParameter("descricao");

                //Atualização do nome com validação
                if (nome != "" && !tipoEmpresa.getNome().equals(nome)) {
                    tipoempresadao.alterarNomeTipoEmpresa(id, nome);
                }

                //Atualização da descrição
                if (!descricao.equals(tipoEmpresa.getDescricao())) {
                    tipoempresadao.alterarDescricaoTipoEmpresa(id, descricao);
                }

                //Retorna mensagem de sucesso
                req.setAttribute("erro", "Tipo empresa atualizado com sucesso!");
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

    //Metodo com ações para ser executadas antes do fim do Servlet
    public void destroy() {

        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}