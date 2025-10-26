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
        IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();
        req.setAttribute("indices", indicedao.listarIndicesClassificacao());
        if (req.getAttribute("popup-alterar")!=null || req.getAttribute("popup-inserir")!=null){
            req.setAttribute("tipos", tipodao.listarTiposEmpresa());
        }

        String idTipoParam = req.getParameter("idTipoEmpresaFiltro");
        String idIndiceParam = req.getParameter("idIndiceClassificacaoFiltro");
        String statusParam = req.getParameter("ordenarStatus");
        boolean ordenarNome;

        if (req.getParameter("ordenarNome") != null){
            ordenarNome = true;
            req.setAttribute("ordenarNome", true);
        }else {
            ordenarNome= false;
        }
        boolean ordenarIndiceClassificacao = idIndiceParam != null && !idIndiceParam.isEmpty();
        boolean ordenarTipoEmpresa = idTipoParam != null && !idTipoParam.isEmpty();
        boolean ordenarStatusAprovacao = statusParam != null && !statusParam.isEmpty();
        Integer idTipoEmpresaOrdenacao;
        Integer idIndiceClassificacaoOrdenacao;
        Character statusFiltro;
        if(idTipoParam != null && !idTipoParam.isEmpty()){
            idTipoEmpresaOrdenacao = Integer.parseInt(idTipoParam);
            req.setAttribute("idTipoEmpresaFiltro", idTipoEmpresaOrdenacao);
        } else{
            idTipoEmpresaOrdenacao = null;
        }


        if(idIndiceParam != null && !idIndiceParam.isEmpty()){
            idIndiceClassificacaoOrdenacao = Integer.parseInt(idIndiceParam);
            req.setAttribute("idIndiceClassificacaoFiltro", idIndiceClassificacaoOrdenacao);
        }else{
            idIndiceClassificacaoOrdenacao = null;
        }


        if(statusParam != null && !statusParam.isEmpty()){
            statusFiltro = statusParam.charAt(0);
            req.setAttribute("ordenarStatus", statusFiltro);
        }else{
            statusFiltro = null;
        }


        FiltrosEmpresa filtrar = new FiltrosEmpresa();
        EmpresaDAO empresadao = new EmpresaDAO();
        List<Empresa> empresas = empresadao.listarEmpresas();
        req.setAttribute("empresas", filtrar.ordenarEmpresa(empresas, ordenarNome, ordenarTipoEmpresa, idTipoEmpresaOrdenacao, ordenarIndiceClassificacao, idIndiceClassificacaoOrdenacao, ordenarStatusAprovacao, statusFiltro));

        Map<Integer, String> tiposEmpresa = new HashMap<>();
        for (Empresa e : empresas) {
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