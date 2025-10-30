package org.example.servlet.Empresa;

///Classe criada com objetivo de realizar a alteração de registros da tabela Empresa através da interface JSP

//Importações
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;
import org.example.utils.regex.ValidacaoEmail;
import org.example.utils.regex.ValidacaoTelefone;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.InvalidForeignKeyException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado e com filtro do servlet (Área restrita)
@WebServlet("/private/AlterarEmpresa")

//Abertura da classe de Servlet
public class ServletAlterarEmpresa extends HttpServlet {

    //Metodo doPost com 2 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Recebe qual ação será realizada
            int action = Integer.parseInt(req.getParameter("action"));

            //Recebe o id do registro que será modificado
            int id = Integer.parseInt(req.getParameter("id"));

            //Variável de controle e erros e respostas
            int resposta = 0;

            //Criando objeto com metodo responsáveis por ações na tabela Empresa do banco de dados
            EmpresaDAO empresadao = new EmpresaDAO();

            //Objeto de validação de email
            ValidacaoEmail valemail = new ValidacaoEmail();

            //Objeto de validação de telefone
            ValidacaoTelefone valfone = new ValidacaoTelefone();

            //Primeira ação (Abre o pop-up de alteração)
            if (action == 0) {
                //Objeto empresa com valores do registro retornado pelo metodo "listarEmpresaPorId"
                Empresa empresa = empresadao.listarEmpresaPorId(id);

                //Seta a empresa
                req.setAttribute("empresa", empresa);

                //Ativa o pop-up de alteração
                req.setAttribute("popup-alterar", true);

                //Carrega dados no CRUD com pop-up ativo
                req.getRequestDispatcher("/private/ListarEmpresas").forward(req, resp);

            } 
            //Segunda ação (Altera empresa do banco de dados)
            else if (action == 1) {
                //Recebe valores dos parâmetros do formulário
                int idTipoEmpresa = Integer.parseInt(req.getParameter("idTipoEmpresa"));
                int idIndiceClassificacao = Integer.parseInt(req.getParameter("idIndiceClassificacao"));
                String nome = req.getParameter("nome");
                String email = req.getParameter("email");
                String telefone = req.getParameter("telefone");

                //Objetos DAO para operações no banco de dados
                TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
                StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();

                //Lista a empresa pelo id do input
                Empresa empresa = empresadao.listarEmpresaPorId(id);

                //Armazena o id do tipo de empresa antigo para verificação posterior
                int idTipoEmpresaAntigo = empresa.getIdTipoEmpresa();

                //Verifica se a empresa é ativa com base no status de aprovação
                StatusAprovacao status = statusdao.listarStatusAprovacaoPorID(empresa.getIdStatusAprovacao());
                boolean empresaAtiva = status.getStatus() == 'a';

                //Verifica se houve alteração no tipo de empresa
                if (idTipoEmpresa != empresa.getIdTipoEmpresa()) {
                    //Altera o tipo de empresa no banco de dados
                    boolean sucesso = empresadao.alterarIdTipoEmpresaEmpresa(id, idTipoEmpresa);
                    
                    //Se a empresa está ativa e a alteração foi bem sucedida
                    if (empresaAtiva && sucesso) {
                        //Verifica se o tipo de empresa antigo ficará inativo
                        boolean tipoAntigoInativo = true;
                        //Lista todas as empresas do tipo antigo
                        List<Empresa> empresasAntigoTipo = empresadao.listarEmpresaPorIdTipoEmpresa(idTipoEmpresaAntigo);
                        
                        //Percorre as empresas do tipo antigo
                        if (!empresasAntigoTipo.isEmpty()) {
                            for (Empresa e : empresasAntigoTipo) {
                                //Verifica se existe alguma empresa ativa no tipo antigo
                                if (statusdao.listarStatusAprovacaoPorID(e.getIdStatusAprovacao()).getStatus() == 'a')
                                    tipoAntigoInativo = false;
                            }
                        }
                        
                        //Se não há mais empresas ativas no tipo antigo, inativa o tipo
                        if (tipoAntigoInativo) {
                            tipoempresadao.alterarStatusTipoEmpresa(idTipoEmpresaAntigo, 'i');
                            tipoempresadao.alterarUltimaAtualizacaoTipoEmpresa(idTipoEmpresaAntigo, LocalDate.now());
                        }

                        //Conta quantas empresas ativas existem no novo tipo
                        int qntdEmpresas = 0;
                        List<Empresa> empresasNovoTipo = empresadao.listarEmpresaPorIdTipoEmpresa(idTipoEmpresa);
                        
                        //Percorre as empresas do novo tipo
                        if (!empresasNovoTipo.isEmpty()) {
                            for (Empresa e : empresasNovoTipo) {
                                //Conta empresas ativas no novo tipo
                                if (statusdao.listarStatusAprovacaoPorID(e.getIdStatusAprovacao()).getStatus() == 'a')
                                    qntdEmpresas += 1;
                            }
                        }
                        
                        //Se há apenas uma empresa ativa no novo tipo, ativa o tipo
                        if (qntdEmpresas == 1) {
                            tipoempresadao.alterarStatusTipoEmpresa(idTipoEmpresa, 'a');
                            tipoempresadao.alterarUltimaAtualizacaoTipoEmpresa(idTipoEmpresa, LocalDate.now());
                        }
                    }
                }

                //Atualização do índice de classificação
                if (idIndiceClassificacao != empresa.getIdIndiceClassificacao()) {
                    empresadao.alterarIdIndiceClassificacaoEmpresa(id, idIndiceClassificacao);
                }

                //Atualização do nome
                if (!nome.equals(empresa.getNome())) {
                    empresadao.alterarNomeEmpresa(id, nome);
                }

                //Atualização do email com validação
                if (!email.equals(empresa.getEmail())) {
                    if (valemail.validarEmail(email)) {
                        empresadao.alterarEmailEmpresa(id, email);
                    } else {
                        req.setAttribute("erro", "Email inválido");
                        resposta++;
                    }
                }

                //Atualização do telefone com validação
                if (!telefone.equals(empresa.getTelefone())) {
                    if (valfone.validarTelefone(telefone)) {
                        empresadao.alterarTelefoneEmpresa(id, telefone);
                    } else {
                        req.setAttribute("erro", "Telefone inválido");
                        resposta++;
                    }
                }

                //Retorna mensagem de sucesso se não houve erros
                if (resposta == 0) {
                    req.setAttribute("erro", "Empresa atualizada com sucesso");
                }

                //Carrega dados no CRUD
                req.getRequestDispatcher("/private/ListarEmpresas").forward(req, resp);
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

        //Em caso de erro de foreign key inexistente no banco de dados
        catch(InvalidForeignKeyException ifke){
            //Insere erro e exceção na página de erros
            req.setAttribute("erro", "Tipo de empresa ou índice de classificação inexistente");
            req.setAttribute("exception", ifke);
            //Envia dados para página de erros
            req.getRequestDispatcher("/WEB-INF/view/ErrorPage.jsp").forward(req, resp);
        }

    }

    //Metodo com ações para ser executadas antes do fim do Servlet
    public void destroy() {

        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}