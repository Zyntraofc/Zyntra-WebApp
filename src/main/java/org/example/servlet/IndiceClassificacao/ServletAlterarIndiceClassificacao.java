package org.example.servlet.IndiceClassificacao;

///Classe criada com objetivo de realizar a alteração de registros da tabela IndiceClassificacao através da interface JSP

//Importações
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

//ENDPOINT privado e com filtro do servlet (Área restrita)
@WebServlet("/private/AlterarIndiceClassificacao")

//Abertura da classe de Servlet
public class ServletAlterarIndiceClassificacao extends HttpServlet {

    //Metodo doPost com 2 ações diferentes, não manda informações pela URL
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //Recebe qual ação será realizada
            int action = Integer.parseInt(req.getParameter("action"));

            //Recebe o id do registro que será modificado
            int id = Integer.parseInt(req.getParameter("id"));

            //Criando objeto com metodo responsáveis por ações na tabela IndiceClassificacao do banco de dados
            IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();

            //Primeira ação (Abre o pop-up de alteração)
            if (action == 0) {
                //Objeto indiceClassificacao com valores do registro retornado pelo metodo "listarIndiceClassificacaoPorId"
                IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);

                //Seta o indiceClassificacao
                req.setAttribute("indiceClassificacao", indiceClassificacao);

                //Ativa o pop-up de alteração
                req.setAttribute("popup-alterar", true);

                //Carrega dados no CRUD com pop-up ativo
                req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);

            } 
            //Segunda ação (Altera indiceClassificacao do banco de dados)
            else if (action == 1) {
                //Lista o indiceClassificacao pelo id do input
                IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);

                //Recebe valores dos parâmetros do formulário
                String preocupacao = req.getParameter("preocupacao");
                Double porcentagemMinima = Double.parseDouble(req.getParameter("porcentagemMinima"));
                Double porcentagemMaxima = Double.parseDouble(req.getParameter("porcentagemMaxima"));
                String recomendacao = req.getParameter("recomendacao");

                //Lista todos os índices de classificação existentes
                List<IndiceClassificacao> indicesExistentes = indicedao.listarIndicesClassificacao();
                
                //Variável para verificar sobreposição de faixas
                boolean sobrepoe = false;

                //Verifica se há sobreposição com outros índices existentes
                for (IndiceClassificacao i : indicesExistentes) {
                    double minExistente = i.getPorcentagemMinima();
                    double maxExistente = i.getPorcentagemMaxima();
                    
                    //Verifica se há sobreposição de faixas
                    if (!(porcentagemMaxima <= minExistente || porcentagemMinima >= maxExistente)) {
                        //Ignora o próprio índice que está sendo alterado
                        if (i.getId() != id) sobrepoe = true;
                        break;
                    }
                }

                //Verifica se há sobreposição de intervalos
                if (sobrepoe) {
                    req.setAttribute("erro", "O intervalo informado sobrepõe outro já existente!");
                    req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
                } 
                //Verifica se a porcentagem mínima é maior ou igual à máxima
                else if (porcentagemMinima >= porcentagemMaxima) {
                    req.setAttribute("erro", "A porcentagem mínima deve ser menor que a máxima!");
                    req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
                } 
                //Se não há erros, realiza as alterações
                else {
                    //Atualização da preocupação
                    if (!indiceClassificacao.getPreocupacao().equals(preocupacao)) {
                        indicedao.alterarPreocupacaoIndiceClassificacao(id, preocupacao);
                    }

                    //Atualização da porcentagem mínima
                    if (indiceClassificacao.getPorcentagemMinima() != porcentagemMinima) {
                        indicedao.alterarPorcentagemMinimaIndiceClassificacao(id, porcentagemMinima);
                    }

                    //Atualização da porcentagem máxima
                    if (indiceClassificacao.getPorcentagemMaxima() != porcentagemMaxima) {
                        indicedao.alterarPorcentagemMaximaIndiceClassificacao(id, porcentagemMaxima);
                    }

                    //Atualização da recomendação
                    if (!indiceClassificacao.getRecomendacao().equals(recomendacao)) {
                        indicedao.alterarRecomendacaoIndiceClassificacao(id, recomendacao);
                    }

                    //Retorna mensagem de sucesso
                    req.setAttribute("erro", "Indice classificação alterado com sucesso");
                    req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
                }
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