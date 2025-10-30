package org.example.servlet.TipoEmpresa;

///Classe criada com objetivo de encaminhar Listagem de tipos de empresa para a listagem do CRUD no JSP

//Importações
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;
import org.example.utils.filtros.FiltrosTipoEmpresa;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/ListarTipoEmpresa")

//Abertura da classe de Servlet
public class ServletListarTipoEmpresa extends HttpServlet {

    //Metodo doGet que carregará requisições doGet no doPost
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Usa metodo doPost
        doPost(req, resp);
    }

    //Metodo doPost que encaminha tipos de empresa para o JSP
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //Objeto com metodo de ações no banco de dados
            TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

            //Lista os tipos de empresa do banco de dados
            List<TipoEmpresa> tiposEmpresa = tipoempresadao.listarTiposEmpresa();

            //Processa filtro por status
            Character statusParaOrdenar = req.getParameter("ordenarStatus") != null && !req.getParameter("ordenarStatus").isEmpty() ? req.getParameter("ordenarStatus").charAt(0) : null;
            boolean ordenarStatus = statusParaOrdenar != null && (statusParaOrdenar == 'a' || statusParaOrdenar == 'i');

            //Processa filtro por atualizações
            String atualizacoesOrdenarString = req.getParameter("ordenarAtualizacoes");
            boolean ordenarAtualizacoes = false;
            boolean recente = false;
            if (atualizacoesOrdenarString != null && !atualizacoesOrdenarString.isEmpty()) {
                ordenarAtualizacoes = true;
                recente = atualizacoesOrdenarString.equals("2");
            }

            //Objeto para aplicar filtros nos tipos de empresa
            FiltrosTipoEmpresa filtrar = new FiltrosTipoEmpresa();

            //Aplica filtros e ordenações nos tipos de empresa
            req.setAttribute("tiposEmpresa", filtrar.ordenarTipoEmpresa(tiposEmpresa, ordenarStatus, statusParaOrdenar, ordenarAtualizacoes, recente));

            //Envia dados para o CRUD
            req.getRequestDispatcher("/WEB-INF/view/CrudTipoEmpresa.jsp").forward(req, resp);

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