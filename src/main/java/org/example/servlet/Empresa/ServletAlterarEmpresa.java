package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.EmpresaDAO;
import org.example.model.Empresa;
import org.example.regex.*;

@WebServlet("/AlterarEmpresa")
public class ServletAlterarEmpresa extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        int resposta = 0;

        EmpresaDAO empresadao = new EmpresaDAO();
        ValidacaoEmail valemail = new ValidacaoEmail();
        ValidacaoTelefone valfone = new ValidacaoTelefone();

        if (action == 0) {
            Empresa empresa = empresadao.listarEmpresaPorId(id);
            req.setAttribute("empresa", empresa);
            req.setAttribute("popup-alterar", true);
            req.getRequestDispatcher("ListarEmpresas").forward(req, resp);

        } else if (action == 1) {
            int idTipoEmpresa = Integer.parseInt(req.getParameter("idTipoEmpresa"));
            int idIndiceClassificacao = Integer.parseInt(req.getParameter("idIndiceClassificacao"));
            int idStatusAprovacao = Integer.parseInt(req.getParameter("idStatusAprovacao"));
            String nome = req.getParameter("nome");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");

            Empresa empresa = empresadao.listarEmpresaPorId(id);

            // atualizações
            if (idTipoEmpresa != empresa.getIdTipoEmpresa()) {
                empresadao.alterarIdTipoEmpresaEmpresa(id, idTipoEmpresa);
            }
            if (idIndiceClassificacao != empresa.getIdIndiceClassificacao()) {
                empresadao.alterarIdIndiceClassificacaoEmpresa(id, idIndiceClassificacao);
            }
            if (idStatusAprovacao != empresa.getIdStatusAprovacao()) {
                empresadao.alterarIdStatusAprovacaoEmpresa(id, idStatusAprovacao);
            }
            if (!nome.equals(empresa.getNome())) {
                empresadao.alterarNomeEmpresa(id, nome);
            }
            if (!email.equals(empresa.getEmail())) {
                if (valemail.validarEmail(email)) {
                    empresadao.alterarEmailEmpresa(id, email);
                } else {
                    req.setAttribute("erro", "Email inválido");
                    req.getRequestDispatcher("view/AlterarEmpresa.jsp").forward(req, resp);
                    resposta++;
                }
            }
            if (!telefone.equals(empresa.getTelefone())) {
                if (valfone.validarTelefone(telefone)) {
                    empresadao.alterarTelefoneEmpresa(id, telefone);
                } else {
                    req.setAttribute("erro", "Telefone inválido");
                    req.getRequestDispatcher("view/AlterarEmpresa.jsp").forward(req, resp);
                    resposta++;
                }
            }

            Empresa empresaAtualizada = empresadao.listarEmpresaPorId(id);
            req.setAttribute("empresa", empresaAtualizada);
            if(resposta ==0){
                req.setAttribute("erro", "Empresa atualizada com sucesso");
                req.getRequestDispatcher("ListarEmpresas").forward(req, resp);
            }

        }
    }
}
