package org.example.servlet.Empresa;

///Classe criada com objetivo de inserir novos registros no banco de dados através da interface em JSP

//Importações
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;
import org.example.model.Empresa;
import org.example.utils.regex.ValidacaoEmail;
import org.example.utils.regex.ValidacaoTelefone;
import org.example.utils.regex.ValidacaoCnpj;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.InvalidForeignKeyException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado com filtro do Servlet
@WebServlet("/private/InserirEmpresa")

//Abertura da classe de Servlet
public class ServletInserirEmpresa extends HttpServlet {

    //Metodo doGet para carregar pop-up de inserir
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //Recebe o caminho para redirecionamento
        String caminho = req.getParameter("caminho");
        
        //Seta o caminho para redirecionamento
        req.setAttribute("caminho", caminho);
        
        //Ativa o pop-up de inserção
        req.setAttribute("popup-inserir", true);

        //Envia dados para o CRUD
        req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
    }

    //Metodo doPost para realizar inserção de empresa no banco de dados
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        try {
            //Recebe o caminho para redirecionamento
            String caminho = req.getParameter("caminho");

            //Recebe parâmetros do formulário
            String idTipoEmpresaStr = req.getParameter("idTipoEmpresa");
            String nome = req.getParameter("nome");
            String cnpj = req.getParameter("cnpj");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");

            //Valida se todos os campos obrigatórios foram preenchidos
            if (idTipoEmpresaStr == null || idTipoEmpresaStr.isEmpty() ||
                    nome == null || nome.isEmpty() ||
                    cnpj == null || cnpj.isEmpty() ||
                    email == null || email.isEmpty() ||
                    telefone == null || telefone.isEmpty()) {
                req.setAttribute("erro", "Preencha todos os campos obrigatórios");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            //Converte idTipoEmpresa para inteiro
            int idTipoEmpresa = Integer.parseInt(idTipoEmpresaStr);
            
            //Define idIndiceClassificacao padrão
            int idIndiceClassificacao = 1;

            //Objetos para validação de dados
            ValidacaoEmail valemail = new ValidacaoEmail();
            ValidacaoTelefone valefone = new ValidacaoTelefone();

            //Valida formato do email
            if (!valemail.validarEmail(email)) {
                req.setAttribute("erro", "Digite o email corretamente");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }
            
            //Valida formato do telefone
            if (!valefone.validarTelefone(telefone)) {
                req.setAttribute("erro", "Digite o telefone corretamente");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            //Padronizando formato do cnpj
            String cnpjFormatado = ValidacaoCnpj.formatarCnpj(cnpj);


            //Valida CNPJ
            if (!ValidacaoCnpj.isCNPJValido(cnpjFormatado)) {
                req.setAttribute("erro", "O CNPJ digitado não existe");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            //Cria e insere status de aprovação
            StatusAprovacaoDAO statusDao = new StatusAprovacaoDAO();
            StatusAprovacao status = new StatusAprovacao(LocalDate.now());
            int idStatusAprovacao = statusDao.inserirStatusAprovacao(status);

            //Verifica se o status foi inserido com sucesso
            if (idStatusAprovacao <= 0) {
                req.setAttribute("erro", "Falha ao criar o status de aprovação.");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            //Cria objeto empresa com os dados validados
            Empresa empresaNova = new Empresa(idTipoEmpresa, idIndiceClassificacao, idStatusAprovacao, nome, cnpj, email, telefone);
            
            //Insere empresa no banco de dados
            EmpresaDAO dao = new EmpresaDAO();
            if (!dao.inserirEmpresa(empresaNova)) {
                req.setAttribute("erro", "Falha ao inserir a empresa.");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            //Retorna mensagem de sucesso
            req.setAttribute("erro", "Empresa e Status inseridos com sucesso");
            req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);

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

        //Em caso de erro de foreign key inexistente no banco de dados
        catch(InvalidForeignKeyException ifke){
            //Insere erro e exceção na página de erros
            req.setAttribute("erro", "Tipo de empresa ou índice de classificação inexistente");
            req.setAttribute("exception", ifke);
            //Envia dados para página de erros
            req.getRequestDispatcher("/WEB-INF/view/ErrorPage.jsp").forward(req, resp);
        }


    }

    //Metodo com ações para ser executadas no fim do servlet
    public void destroy() {
        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}