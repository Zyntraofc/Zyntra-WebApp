package org.example.servlet.TipoEmpresa;

///Classe criada com objetivo de inserir novos registros no banco de dados através da interface em JSP

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

//ENDPOINT privado com filtro do Servlet
@WebServlet("/private/InserirTipoEmpresa")

//Abertura da classe de Servlet
public class ServletInserirTipoEmpresa extends HttpServlet {

    //Metodo doGet para carregar pop-up de inserir
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //Ativa o pop-up de inserção
        req.setAttribute("popup-inserir", true);

        //Envia dados para o CRUD
        req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
    }

    //Metodo doPost para realizar inserção de tipoEmpresa no banco de dados
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            //Recebe parâmetros do formulário
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");

            //Objeto tipoEmpresa para inserção
            TipoEmpresa tipoEmpresaNovo;

            //Verifica se a descrição foi fornecida
            if (descricao == null || descricao.trim().isEmpty()) {
                //Cria objeto tipoEmpresa apenas com nome
                tipoEmpresaNovo = new TipoEmpresa(nome);
            } else {
                //Cria objeto tipoEmpresa com nome e descrição
                tipoEmpresaNovo = new TipoEmpresa(nome, descricao);
            }

            //Objeto de acesso a tabela no banco de dados
            TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

            //Insere e verifica se deu certo
            if (tipoempresadao.inserirTipoEmpresa(tipoEmpresaNovo)) {
                //Retorna que inserção deu certo
                req.setAttribute("erro", "Tipo empresa inserido com sucesso!");
            } else {
                //Retorna mensagem de erro ao inserir tipoEmpresa
                req.setAttribute("erro", "Não foi possível inserir tipo empresa");
            }

            //Envia dados para o CRUD
            req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);

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