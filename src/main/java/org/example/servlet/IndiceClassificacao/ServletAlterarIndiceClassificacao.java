package org.example.servlet.IndiceClassificacao;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;

@WebServlet("/AlterarIndiceClassificacao")
public class ServletAlterarIndiceClassificacao extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        int resposta = 0;

        IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();

        if(action == 0){
            IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);
            req.setAttribute("indiceClassificacao", indiceClassificacao);
            req.getRequestDispatcher("view/AlterarIndiceClassificacao.jsp").forward(req, resp);
        }else if(action == 1){
            IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);
            String preocupacao = req.getParameter("preocupacao");
            Double porcentagemMinima = Double.parseDouble(req.getParameter("porcentagemMinima"));
            Double porcentagemMaxima = Double.parseDouble(req.getParameter("porcetagemMaxima"));
            String recomendacao = req.getParameter("recomendacao");
            if(!indiceClassificacao.getPreocupacao().equals(preocupacao)){
                indicedao.alterarPreocupacaoIndiceClassificacao(id, preocupacao);
            }
            if(indiceClassificacao.getPorcentagemMinima() != porcentagemMinima){
                indicedao.alterarPorcentagemMinimaIndiceClassificacao(id, porcentagemMinima);
            }
            if(indiceClassificacao.getPorcentagemMaxima() != porcentagemMaxima){
                indicedao.alterarPorcentagemMaximaIndiceClassificacao(id, porcentagemMaxima);
            }
            if(!indiceClassificacao.getRecomendacao().equals(recomendacao)){
                indicedao.alterarRecomendacaoIndiceClassificacao(id, recomendacao);
            }

            req.setAttribute("erro", "Indice classificação alterado com sucesso");
            req.getRequestDispatcher("ListarIndiceClassificacao").forward(req, resp);

        }
    }
}
