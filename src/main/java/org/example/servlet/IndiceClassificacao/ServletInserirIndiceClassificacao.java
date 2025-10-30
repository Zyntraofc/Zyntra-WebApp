package org.example.servlet.IndiceClassificacao;

///Classe criada com objetivo de inserir novos registros no banco de dados através da interface em JSP

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

//ENDPOINT privado com filtro do Servlet
@WebServlet("/private/InserirIndiceClassificacao")

//Abertura da classe de Servlet
public class ServletInserirIndiceClassificacao extends HttpServlet {

    //Metodo doGet para carregar pop-up de inserir
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Ativa o pop-up de inserção
        req.setAttribute("popup-inserir", true);

        //Envia dados para o CRUD
        req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
    }

    //Metodo doPost para realizar inserção de indiceClassificacao no banco de dados
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            //Recebe parâmetros do formulário
            String preocupacao = req.getParameter("preocupacao");
            double porcentagemMinima = Double.parseDouble(req.getParameter("porcentagemMinima"));
            double porcentagemMaxima = Double.parseDouble(req.getParameter("porcentagemMaxima"));
            String recomendacao = req.getParameter("recomendacao");

            //Objeto de acesso a tabela no banco de dados
            IndiceClassificacaoDAO indicesdao = new IndiceClassificacaoDAO();

            //Lista todos os índices de classificação existentes
            List<IndiceClassificacao> indicesExistentes = indicesdao.listarIndicesClassificacao();

            //Variável para verificar sobreposição de faixas
            boolean sobrepoe = false;

            //Verifica se há sobreposição com outros índices existentes
            for (IndiceClassificacao i : indicesExistentes) {
                double minExistente = i.getPorcentagemMinima();
                double maxExistente = i.getPorcentagemMaxima();

                //Verifica se há sobreposição de faixas
                if (!(porcentagemMaxima <= minExistente || porcentagemMinima >= maxExistente)) {
                    sobrepoe = true;
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
            //Se não há erros, realiza a inserção
            else {
                //Cria objeto indiceClassificacao com os dados validados
                IndiceClassificacao novo = new IndiceClassificacao(recomendacao, preocupacao, porcentagemMinima, porcentagemMaxima);
                
                //Insere e verifica se deu certo
                if (indicesdao.inserirIndiceClassificacao(novo)) {
                    //Retorna que inserção deu certo
                    req.setAttribute("erro", "Índice de classificação inserido com sucesso!");
                } else {
                    //Retorna mensagem de erro ao inserir indiceClassificacao
                    req.setAttribute("erro", "Erro ao inserir índice");
                }

                //Envia dados para o CRUD
                req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
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