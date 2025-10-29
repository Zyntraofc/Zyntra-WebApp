package org.example.servlet.Empresa;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.StatusAprovacao;
import org.example.model.Empresa;

import java.time.LocalDate;
import java.util.InputMismatchException;

import org.example.utils.regex.*;

import org.example.utils.regex.ValidacaoEmail;
import org.example.utils.regex.ValidacaoTelefone;

@WebServlet("/private/InserirEmpresa")
public class ServletInserirEmpresa extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String caminho = req.getParameter("caminho");
        req.setAttribute("caminho", caminho);
        req.setAttribute("popup-inserir", true);
        req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String caminho = req.getParameter("caminho");

        try {
            String idTipoEmpresaStr = req.getParameter("idTipoEmpresa");
            String nome = req.getParameter("nome");
            String cnpj = req.getParameter("cnpj");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");

            if (idTipoEmpresaStr == null || idTipoEmpresaStr.isEmpty() ||
                    nome == null || nome.isEmpty() ||
                    cnpj == null || cnpj.isEmpty() ||
                    email == null || email.isEmpty() ||
                    telefone == null || telefone.isEmpty()) {
                req.setAttribute("erro", "Preencha todos os campos obrigatórios");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            int idTipoEmpresa = Integer.parseInt(idTipoEmpresaStr);
            int idIndiceClassificacao = 1;

            ValidacaoEmail valemail = new ValidacaoEmail();
            ValidacaoTelefone valefone = new ValidacaoTelefone();
            ValidacaoCnpj valecnpj = new ValidacaoCnpj();

            if (!valemail.validarEmail(email)) {
                req.setAttribute("erro", "Digite o email corretamente");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }
            if (!valefone.validarTelefone(telefone)) {
                req.setAttribute("erro", "Digite o telefone corretamente");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }
            if (!valecnpj.isCNPJValido(cnpj)) {
                req.setAttribute("erro", "O CNPJ digitado não existe");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            StatusAprovacaoDAO statusDao = new StatusAprovacaoDAO();
            StatusAprovacao status = new StatusAprovacao(LocalDate.now());
            int idStatusAprovacao = statusDao.inserirStatusAprovacao(status);

            if (idStatusAprovacao <= 0) {
                req.setAttribute("erro", "Falha ao criar o status de aprovação.");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            Empresa empresaNova = new Empresa(idTipoEmpresa, idIndiceClassificacao, idStatusAprovacao, nome, cnpj, email, telefone);
            EmpresaDAO dao = new EmpresaDAO();
            if (!dao.inserirEmpresa(empresaNova)) {
                req.setAttribute("erro", "Falha ao inserir a empresa.");
                req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
                return;
            }

            req.setAttribute("erro", "Empresa e Status inseridos com sucesso");
            req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);

        } catch (InputMismatchException ime) {
            req.setAttribute("erro", "Digite os dados corretamente");
            req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro interno do sistema");
            req.getRequestDispatcher("/private/Listar" + caminho).forward(req, resp);
        }
    }

    public void destroy() {
        ConexaoManager.desconectar();
    }
}