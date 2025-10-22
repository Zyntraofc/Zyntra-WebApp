package org.example.servlet.Empresa;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.EmpresaDAO;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.Empresa;
import org.example.model.StatusAprovacao;
import org.example.utils.filtros.FiltrosEmpresa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/private/ListarEmpresas")
public class ServletListarEmpresas extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{

        TipoEmpresaDAO tipodao = new TipoEmpresaDAO();
        if (req.getAttribute("popup-alterar")!=null) {
            IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();
            req.setAttribute("statuses", indicedao.listarIndicesClassificacao());
        }
        if (req.getAttribute("popup-alterar")!=null || req.getAttribute("popup-inserir")!=null){
            req.setAttribute("tipos", tipodao.listarTiposEmpresa());
        }

        String idTipoParam = req.getParameter("idTipoEmpresa");

        boolean ordenarNome = req.getParameter("ordenarNome") != null;
        boolean ordenarTipoEmpresa = idTipoParam != null && !idTipoParam.isEmpty();

        Integer idTipoEmpresaOrdenacao = (idTipoParam != null && !idTipoParam.isEmpty())
                ? Integer.parseInt(idTipoParam)
                : null;

        FiltrosEmpresa filtrar = new FiltrosEmpresa();

        EmpresaDAO empresadao = new EmpresaDAO();
        List<Empresa> empresas = empresadao.listarEmpresas();
        List<Empresa> empresasFiltradas = filtrar.ordenarEmpresa(empresas, ordenarNome, ordenarTipoEmpresa, idTipoEmpresaOrdenacao);
        req.setAttribute("empresas", empresasFiltradas);
        
        Map<Integer, String> tiposEmpresa = new HashMap<>();
        for (Empresa e : empresasFiltradas) {
            String tipoEmpresa = tipodao.listarTipoEmpresaPorId(e.getIdTipoEmpresa()).getNome();
            tiposEmpresa.put(e.getId(), tipoEmpresa);
        }
        
        req.setAttribute("tiposEmpresa", tiposEmpresa);
        req.setAttribute("tiposFiltro", tipodao.listarTiposEmpresa());
        req.getRequestDispatcher("/WEB-INF/view/CrudEmpresa.jsp").forward(req, resp);
    }
    public void destroy(){
        ConexaoManager.desconectar();
    }
}
