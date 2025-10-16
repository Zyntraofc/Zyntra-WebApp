package org.example.servlet.TipoEmpresa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;

@WebServlet("/private/AlterarTipoEmpresa")
public class ServletAlterarTipoEmpresa extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();

        TipoEmpresa tipoEmpresa = tipoempresadao.listarTipoEmpresaPorId(id);

        if (action == 0) {
            // prepara atributos para a página de alteração
            req.setAttribute("tipoEmpresa", tipoEmpresa);
            req.setAttribute("popup-alterar", true);
            req.getRequestDispatcher("private/ListarTipoEmpresa").forward(req, resp);

        } else if (action == 1) {
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");

            // comparações null-safe: só altera quando realmente mudou
            if (nome != "" && !tipoEmpresa.getNome().equals(nome)) {
                tipoempresadao.alterarNomeTipoEmpresa(id, nome);
            }
            if (!descricao.equals(tipoEmpresa.getDescricao())) {
                tipoempresadao.alterarDescricaoTipoEmpresa(id, descricao);
            }

            req.setAttribute("erro", "Tipo empresa atualizado com sucesso!");
            req.getRequestDispatcher("private/ListarTipoEmpresa").forward(req, resp);
        }
        ConexaoManager.desconectar();
    }
}
