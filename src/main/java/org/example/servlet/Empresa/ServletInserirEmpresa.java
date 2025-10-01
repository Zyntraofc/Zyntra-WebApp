package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.model.Empresa;

import java.util.InputMismatchException;
import java.util.List;
import org.example.regex.*;

@WebServlet("/InserirEmpresa")
public class ServletInserirEmpresa extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        try{
            int idTipoEmpresa = Integer.parseInt(req.getParameter("idTipoEmpresa"));
            int idIndiceClassificacao = Integer.parseInt(req.getParameter("idIndiceClassificacao"));
            int idStatusAprovacao = Integer.parseInt(req.getParameter("idStatusAprovacao"));
            String nome = req.getParameter("nome");
            String cnpj = req.getParameter("cnpj");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");
            ValidacaoEmail valemail = new ValidacaoEmail();
            ValidacaoTelefone valefone = new ValidacaoTelefone();
            if(valemail.validarEmail(email)){
                if(valefone.validarTelefone(telefone)){
                    if(cnpj.length() == 14){
                        Empresa empresaNova = new Empresa(idTipoEmpresa, idIndiceClassificacao, idStatusAprovacao, nome, cnpj, email, telefone);
                        EmpresaDAO dao = new EmpresaDAO();
                        if(dao.inserirEmpresa(empresaNova)){
                            req.setAttribute("erro", "Empresa inserida com sucesso");
                            req.getRequestDispatcher("ListarEmpresas").forward(req, resp);
                        }else{
                            req.setAttribute("erro", "Erro ao inserir a empresa");
                            req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                        }
                    }else{
                        req.setAttribute("erro", "Digite o cnpj corretamente");
                        req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                    }
                }else{
                    req.setAttribute("erro", "Digite os telefone");
                    req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
                }
            }else{
                req.setAttribute("erro", "Digite o email corretamente");
                req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
            }

        }catch(InputMismatchException ime){
            req.setAttribute("erro", "Digite os dados corretamente corretamente");
            req.getRequestDispatcher("view/InserirEmpresa.jsp").forward(req, resp);
        }
    }


}
