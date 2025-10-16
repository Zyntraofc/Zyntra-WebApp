package org.example.servlet.IndiceClassificacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;
import java.util.List;

@WebServlet("/private/ListarIndiceClassificacao")
public class ServletListarIndiceClassificacao extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        IndiceClassificacaoDAO indiceclassificacaodao = new IndiceClassificacaoDAO();
        List<IndiceClassificacao> indicesClassificacao = indiceclassificacaodao.listarIndicesClassificacao();
        req.setAttribute("indicesClassificacao", indicesClassificacao);
        req.getRequestDispatcher("/WEB-INF/view/CrudIndiceClassificacao.jsp").forward(req, resp);
        ConexaoManager.desconectar();
    }
}
