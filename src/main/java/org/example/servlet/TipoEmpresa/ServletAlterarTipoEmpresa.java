package org.example.servlet.TipoEmpresa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;

@WebServlet("/AlterarTipoEmpresa")
public class ServletAlterarTipoEmpresa extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

        TipoEmpresa tipoEmpresa = tipoempresadao.listarTipoEmpresaPorId(id);

        if (action == 0) {
            // prepara atributos para a página de alteração
            TipoEmpresa tipoempresa = tipoempresadao.listarTipoEmpresaPorId(id);
            req.setAttribute("tipoEmpresa", tipoempresa);

            // garante que dataFormatada exista no request (formato yyyy-MM-dd para input[type=date])
            if (tipoempresa.getUltimaAtualizacao() != null) {
                req.setAttribute("dataFormatada",
                        tipoempresa.getUltimaAtualizacao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
                req.setAttribute("dataFormatada", "");
            }

            req.getRequestDispatcher("view/AlterarTipoEmpresa.jsp").forward(req, resp);

        } else if (action == 1) {
            String nome = req.getParameter("nome");

            char status = "Ativo".equals(req.getParameter("status")) ? 'a' : 'i';

            String dataParam = req.getParameter("ultima_atualizacao");
            LocalDate ultimaAtualizacao = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (dataParam == null || dataParam.trim().isEmpty()) {
                req.setAttribute("erro", "Data é obrigatória.");
                req.getRequestDispatcher("/view/InserirTipoEmpresa.jsp").forward(req, resp);
                return;
            }

            try {
                ultimaAtualizacao = LocalDate.parse(dataParam, formatter);
            } catch (DateTimeParseException e) {
                try {
                    ultimaAtualizacao = LocalDate.parse(dataParam, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException ex) {
                    req.setAttribute("erro", "Formato de data inválido. Use yyyy-MM-dd (input[type=date]) ou dd/MM/yyyy.");
                    req.getRequestDispatcher("/view/InserirTipoEmpresa.jsp").forward(req, resp);
                    return;
                }
            }

            String descricao = req.getParameter("descricao");

            // comparações null-safe: só altera quando realmente mudou
            if (tipoEmpresa.getNome() == null || !tipoEmpresa.getNome().equals(nome)) {
                tipoempresadao.alterarNomeTipoEmpresa(id, nome);
            }

            if (tipoEmpresa.getStatus() != status) {
                tipoempresadao.alterarStatusTipoEmpresa(id, status);
            }

            if (tipoEmpresa.getUltimaAtualizacao() == null || !ultimaAtualizacao.equals(tipoEmpresa.getUltimaAtualizacao())) {
                tipoempresadao.alterarUltimaAtualizacaoTipoEmpresa(id, ultimaAtualizacao);
            }

            if (descricao != null && !descricao.trim().isEmpty()) {
                if (tipoEmpresa.getDescricao() == null || !descricao.equals(tipoEmpresa.getDescricao())) {
                    tipoempresadao.alterarDescricaoTipoEmpresa(id, descricao);
                }
            }

            req.setAttribute("erro", "Tipo empresa atualizado com sucesso!");
            req.getRequestDispatcher("ListarTipoEmpresa").forward(req, resp);
        }
    }
}
