package org.example.servlet.Adm;

///Classe criada com objetivo de encaminha Listagem de administradores para a listagem do CRUD no JSP

//Importações
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.AdministradorDAO;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.model.Administrador;
import java.util.List;

//ENDPOINT privado e com filtro do Servlet (Área restrita)
@WebServlet("/private/ListarAdministradores")

//Abertura da classe de Servlet
public class ServletListarAdm extends HttpServlet {

    //Metodo doGet que carregará requisições doGet no doPost
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Usa metodo doPost
        doPost(req, resp);
    }

    //Metodo doPost que pode ativar pop-ups de alteração e encaminha administradores para o JSP
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try{
            //Objeto com metodo de ações no banco de dados
        AdministradorDAO administradorDAO = new AdministradorDAO();

        //Lista os administradores do banco de dados
        List<Administrador> administradores = administradorDAO.listarAdministradores();

        //Seta administradores do banco de dados no JSP
        req.setAttribute("administradores", administradores);

        //Recebe se o id e pop-up de alteraçao já estão ativos
        String popupAlterar = req.getParameter("popup-alterar");
        String id = req.getParameter("id");

        //Verifica se o pop-up de alteração está ativo
        if ("true".equals(popupAlterar) && id != null) {

            //Ativa pop-up de alteração
            req.setAttribute("popup-alterar", true);

            //Lista administrador por id
            Administrador admin = administradorDAO.listarAdministradorPorId(Integer.parseInt(id));

            //Insere administrador listado no JSP para carregar valores no pop-up
            req.setAttribute("administrador", admin);
        }

        //Envia dados para o CRUD do adm
        req.getRequestDispatcher("/WEB-INF/view/CrudAdm.jsp").forward(req, resp);

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