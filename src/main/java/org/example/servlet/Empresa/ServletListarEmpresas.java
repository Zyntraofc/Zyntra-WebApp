package org.example.servlet.Empresa;

///Classe criada com objetivo de encaminhar Listagem de empresas para a listagem do CRUD no JSP

//Importações
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;
import org.example.utils.filtros.FiltrosEmpresa;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/ListarEmpresas")

//Abertura da classe de Servlet
public class ServletListarEmpresas extends HttpServlet {

    //Metodo doGet que carregará requisições doGet no doPost
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Usa metodo doPost
        doPost(req, resp);
    }

    //Metodo doPost que pode ativar pop-ups e encaminha empresas para o JSP
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Objetos com metodos de ações no banco de dados
            TipoEmpresaDAO tipodao = new TipoEmpresaDAO();
            IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();

            //Seta índices de classificação no JSP
            req.setAttribute("indices", indicedao.listarIndicesClassificacao());

            //Verifica se pop-ups de alteração ou inserção estão ativos
            if (req.getAttribute("popup-alterar") != null || req.getAttribute("popup-inserir") != null) {
                //Seta tipos de empresa no JSP para pop-ups
                req.setAttribute("tipos", tipodao.listarTiposEmpresa());
            }

            //Recebe parâmetros de filtro do formulário
            String idTipoParam = req.getParameter("idTipoEmpresaFiltro");
            String idIndiceParam = req.getParameter("idIndiceClassificacaoFiltro");
            String statusParam = req.getParameter("ordenarStatus");

            //Variável para ordenação por nome
            boolean ordenarNome;

            //Verifica se deve ordenar por nome
            if (req.getParameter("ordenarNome") != null) {
                ordenarNome = true;
                req.setAttribute("ordenarNome", true);
            } else {
                ordenarNome = false;
            }

            //Define flags de ordenação baseadas nos parâmetros
            boolean ordenarIndiceClassificacao = idIndiceParam != null && !idIndiceParam.isEmpty();
            boolean ordenarTipoEmpresa = idTipoParam != null && !idTipoParam.isEmpty();
            boolean ordenarStatusAprovacao = statusParam != null && !statusParam.isEmpty();

            //Variáveis para armazenar valores de ordenação
            Integer idTipoEmpresaOrdenacao;
            Integer idIndiceClassificacaoOrdenacao;
            Character statusFiltro;

            //Processa filtro por tipo de empresa
            if (idTipoParam != null && !idTipoParam.isEmpty()) {
                idTipoEmpresaOrdenacao = Integer.parseInt(idTipoParam);
                req.setAttribute("idTipoEmpresaFiltro", idTipoEmpresaOrdenacao);
            } else {
                idTipoEmpresaOrdenacao = null;
            }

            //Processa filtro por índice de classificação
            if (idIndiceParam != null && !idIndiceParam.isEmpty()) {
                idIndiceClassificacaoOrdenacao = Integer.parseInt(idIndiceParam);
                req.setAttribute("idIndiceClassificacaoFiltro", idIndiceClassificacaoOrdenacao);
            } else {
                idIndiceClassificacaoOrdenacao = null;
            }

            //Processa filtro por status de aprovação
            if (statusParam != null && !statusParam.isEmpty()) {
                statusFiltro = statusParam.charAt(0);
                req.setAttribute("ordenarStatus", statusFiltro);
            } else {
                statusFiltro = null;
            }

            //Objeto para aplicar filtros nas empresas
            FiltrosEmpresa filtrar = new FiltrosEmpresa();
            
            //Objeto de acesso a tabela no banco de dados
            EmpresaDAO empresadao = new EmpresaDAO();

            //Lista as empresas do banco de dados
            List<Empresa> empresas = empresadao.listarEmpresas();

            //Aplica filtros e ordenações nas empresas
            req.setAttribute("empresas", filtrar.ordenarEmpresa(empresas, ordenarNome, ordenarTipoEmpresa, idTipoEmpresaOrdenacao, ordenarIndiceClassificacao, idIndiceClassificacaoOrdenacao, ordenarStatusAprovacao, statusFiltro));

            //Mapeia tipos de empresa para cada empresa
            Map<Integer, String> tiposEmpresa = new HashMap<>();
            for (Empresa e : empresas) {
                String tipoEmpresa = tipodao.listarTipoEmpresaPorId(e.getIdTipoEmpresa()).getNome();
                tiposEmpresa.put(e.getId(), tipoEmpresa);
            }

            //Seta dados no JSP para exibição
            req.setAttribute("tiposEmpresa", tiposEmpresa);
            req.setAttribute("tiposFiltro", tipodao.listarTiposEmpresa());

            //Envia dados para o CRUD
            req.getRequestDispatcher("/WEB-INF/view/CrudEmpresa.jsp").forward(req, resp);

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

    }

    //Metodo com ações que serão feitas antes do fim do servlet
    public void destroy() {
        //Desconecta do banco de dados
        ConexaoManager.desconectar();
    }
}