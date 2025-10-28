package org.example.servlet.TipoEmpresa;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.TipoEmpresa;
import org.example.utils.filtros.FiltrosTipoEmpresa;

@WebServlet("/private/ListarTipoEmpresa")
public class ServletListarTipoEmpresa extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
        List<TipoEmpresa> tiposEmpresa = tipoempresadao.listarTiposEmpresa();
        Character statusParaOrdenar = req.getParameter("ordenarStatus") != null && !req.getParameter("ordenarStatus").isEmpty() ? req.getParameter("ordenarStatus").charAt(0) : null;
        boolean ordenarStatus = statusParaOrdenar != null && (statusParaOrdenar == 'a' || statusParaOrdenar == 'i');
        String atualizacoesOrdenarString = req.getParameter("ordenarAtualizacoes");
        boolean ordenarAtualizacoes = false;
        boolean recente = false;
        if (atualizacoesOrdenarString != null && !atualizacoesOrdenarString.isEmpty()) {
            ordenarAtualizacoes = true;
            recente = atualizacoesOrdenarString.equals("2");
        }
        FiltrosTipoEmpresa filtrar = new FiltrosTipoEmpresa();

        req.setAttribute("tiposEmpresa", filtrar.ordenarTipoEmpresa(tiposEmpresa, ordenarStatus, statusParaOrdenar, ordenarAtualizacoes, recente));
        req.getRequestDispatcher("/WEB-INF/view/CrudTipoEmpresa.jsp").forward(req, resp);
    }

    public void destroy() {
        ConexaoManager.desconectar();
    }
}
