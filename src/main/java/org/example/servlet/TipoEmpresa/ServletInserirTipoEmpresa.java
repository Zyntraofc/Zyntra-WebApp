package org.example.servlet.TipoEmpresa;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;

@WebServlet("/private/InserirTipoEmpresa")
public class ServletInserirTipoEmpresa extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("popup-inserir", true);
        req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String descricao = req.getParameter("descricao");
        TipoEmpresa tipoEmpresaNovo;
        if (descricao == null || descricao.trim().isEmpty()) {
            tipoEmpresaNovo = new TipoEmpresa(nome);
        } else {
            tipoEmpresaNovo = new TipoEmpresa(nome, descricao);
        }
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
        if (tipoempresadao.inserirTipoEmpresa(tipoEmpresaNovo)) {
            req.setAttribute("erro", "Tipo empresa inserido com sucesso!");
            req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
        } else {
            req.setAttribute("erro", "Não foi possível inserir tipo empresa");
            req.getRequestDispatcher("/private/ListarTipoEmpresa").forward(req, resp);
        }
    }

    public void destroy() {
        ConexaoManager.desconectar();
    }
}
