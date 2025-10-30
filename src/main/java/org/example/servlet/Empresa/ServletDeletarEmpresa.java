package org.example.servlet.Empresa;

//Classe criada com objetivo de realizar deleções de registros da tabela Empresa através de interface JSP

//Importações
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/DeletarEmpresa")

//Abertura da classe de Servlet
public class ServletDeletarEmpresa extends HttpServlet {

    //Metodo doPost com 3 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Recebe qual ação será feita
            int action = Integer.parseInt(req.getParameter("action"));

            //Objeto com metodos responsáveis por realizar ações na tabela empresa do banco de dados
            EmpresaDAO empresadao = new EmpresaDAO();

            //Recebe o caminho para redirecionamento
            String caminho = req.getParameter("caminho");

            //Primeira ação (Abre pop-up)
            if (action == 0) {
                //Seta o caminho para redirecionamento
                req.setAttribute("caminho", caminho);

                //Objeto empresa para deleção
                Empresa empresa = null;

                //Verifica se foi passado idStatus ou id direto
                if (req.getParameter("idStatus") != null && !req.getParameter("idStatus").isEmpty()) {
                    //Lista o registro com determinado idStatus da empresa do banco de dados
                    empresa = empresadao.listarEmpresaPorIdStatusAprovacao(Integer.parseInt(req.getParameter("idStatus")));
                } else {
                    //Lista o registro com determinado id da empresa do banco de dados
                    int id = Integer.parseInt(req.getParameter("id"));
                    empresa = empresadao.listarEmpresaPorId(id);
                }

                //Seta a empresa no CRUD
                req.setAttribute("empresa", empresa);

                //Ativa o pop-up de deleção
                req.setAttribute("popup-deletar", true);

                //Envia dados para o CRUD da tabela Empresa
                req.getRequestDispatcher("Listar" + caminho).forward(req, resp);
            } 
            //Segunda ação (Confirma deleção)
            else if (action == 1) {
                //Recebe o id da empresa e id do status
                int id = Integer.parseInt(req.getParameter("id"));
                int idStatus = Integer.parseInt(req.getParameter("idStatus"));

                //Obtém o id do tipo de empresa para verificação posterior
                int idTipoEmpresa = empresadao.listarEmpresaPorId(id).getIdTipoEmpresa();

                //Objeto para operações na tabela status aprovação
                StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();

                //Deleta a empresa pelo id
                empresadao.deletarEmpresa(id);

                //Deleta o status de aprovação pelo id
                statusdao.deletarStatusAprovacao(idStatus);

                //Verifica se o tipo de empresa ficará inativo após a deleção
                boolean inativo = true;
                for (Empresa e : empresadao.listarEmpresaPorIdTipoEmpresa(idTipoEmpresa)) {
                    if (statusdao.listarStatusAprovacaoPorID(e.getIdStatusAprovacao()).getStatus() == 'a') inativo = false;
                }

                //Se não há mais empresas ativas no tipo, inativa o tipo de empresa
                if (inativo) {
                    TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
                    tipoempresadao.alterarStatusTipoEmpresa(idTipoEmpresa, 'i');
                    tipoempresadao.alterarUltimaAtualizacaoTipoEmpresa(idTipoEmpresa, LocalDate.now());
                }

                //Mensagem de sucesso
                req.setAttribute("erro", "Empresa e Status deletados com sucesso");

                //Envia dados para o CRUD da tabela Empresa
                req.getRequestDispatcher("Listar" + caminho).forward(req, resp);
            } 
            //Terceira ação (Cancela deleção)
            else if (action == 2) {
                //Cancela a deleção e redireciona
                req.getRequestDispatcher("Listar" + caminho).forward(req, resp);
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