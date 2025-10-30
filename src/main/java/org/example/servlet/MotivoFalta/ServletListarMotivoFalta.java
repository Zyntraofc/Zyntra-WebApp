package org.example.servlet.MotivoFalta;

///Classe criada com objetivo de encaminhar Listagem de motivos de falta para a listagem do CRUD no JSP

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

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/ListarMotivosFalta")

//Abertura da classe de Servlet
public class ServletListarMotivoFalta extends HttpServlet {

    //Metodo doGet que carregará requisições doGet no doPost
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Usa metodo doPost
        doPost(req, resp);
    }

    //Metodo doPost que encaminha motivos de falta para o JSP
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            //Objeto com metodo de ações no banco de dados
            MotivoFaltaDAO motivodao = new MotivoFaltaDAO();

            //Lista os motivos de falta do banco de dados
            List<MotivoFalta> motivos = motivodao.listarMotivosFalta();

            //Seta motivos de falta do banco de dados no JSP
            req.setAttribute("motivos", motivos);

            //Envia dados para o CRUD
            req.getRequestDispatcher("/WEB-INF/view/CrudMotivoFalta.jsp").forward(req, resp);

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