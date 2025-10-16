package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;
import org.example.model.Empresa;
import java.time.LocalDate;
import java.util.InputMismatchException;

import org.example.utils.regex.ValidacaoEmail;
import org.example.utils.regex.ValidacaoTelefone;

@WebServlet("/InserirEmpresa")
public class ServletInserirEmpresa extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        // Pega o parâmetro enviado via GET para redirecionar após o processo no servlet
        String caminho = req.getParameter("caminho");
        req.setAttribute("caminho", caminho);
        req.setAttribute("popup-inserir", true);
        req.getRequestDispatcher("Listar"+caminho).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        try{
            int idTipoEmpresa = Integer.parseInt(req.getParameter("idTipoEmpresa"));
            int idIndiceClassificacao = Integer.parseInt(req.getParameter("idIndiceClassificacao"));
            String nome = req.getParameter("nome");
            String cnpj = req.getParameter("cnpj");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");

            ValidacaoEmail valemail = new ValidacaoEmail();
            ValidacaoTelefone valefone = new ValidacaoTelefone();

            if(!valemail.validarEmail(email)){
                req.setAttribute("erro", "Digite o email corretamente");
                req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                return;
            } if(!valefone.validarTelefone(telefone)){
                req.setAttribute("erro", "Digite os telefone");
                req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                return;
            }
            if(cnpj.length() != 14){
                req.setAttribute("erro", "Digite o cnpj corretamente");
                req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                return;
            }

            StatusAprovacaoDAO statusDao = new StatusAprovacaoDAO();
            StatusAprovacao status = new StatusAprovacao(LocalDate.now());
            int idStatusAprovacao = statusDao.inserirStatusAprovacao(status);

            if (idStatusAprovacao <= 0) {
                req.setAttribute("erro", "Falha ao criar o status de aprovação.");
                req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                return;
            }

            Empresa empresaNova = new Empresa(idTipoEmpresa, idIndiceClassificacao, idStatusAprovacao, nome, cnpj, email, telefone);
            EmpresaDAO dao = new EmpresaDAO();
            if(!dao.inserirEmpresa(empresaNova)){
                req.setAttribute("erro", "Falha ao inserir a empresa.");
                req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                return;
            }
            ConexaoManager.desconectar();
            req.setAttribute("erro", "Empresa e Status inseridos com sucesso");
            String caminho = req.getParameter("caminho");
            req.getRequestDispatcher("Listar" + caminho).forward(req, resp);
        }catch(InputMismatchException ime){
            req.setAttribute("erro", "Digite os dados corretamente corretamente");
            req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
        } catch (Exception e){
            e.printStackTrace();
            req.setAttribute("erro", "Erro interno do sistema");
            req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
        }
    }
}