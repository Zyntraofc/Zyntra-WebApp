package org.example.servlet.IndiceClassificacao;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;

@WebServlet("/private/DeletarIndiceClassificacao")
public class ServletDeletarIndiceClassificacao extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();
        if(action == 0){
            IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);
            req.setAttribute("popup-deletar", true);
            req.setAttribute("indiceClassificacao", indiceClassificacao);
            req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
        }else if(action == 1){
            indicedao.deletarIndiceClassificacao(id);
            req.setAttribute("erro", "Indice classificação deletado com sucesso!");
            req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
        }else if(action == 2){
            req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
        }
        ConexaoManager.desconectar();
    }
}
