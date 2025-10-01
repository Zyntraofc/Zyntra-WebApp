package org.example.servlet.IndiceClassificacao;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;

@WebServlet("/InserirIndiceClassificacao")
public class ServletInserirIndiceClassificacao extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("view/InserirIndiceClassificacao.jsp").forward(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String preocupacao = req.getParameter("preocupacao");
        double porcentagemMinima = Double.parseDouble(req.getParameter("porcentagemMinima"));
        double porcentagemMaxima = Double.parseDouble(req.getParameter("porcentagemMaxima"));
        String recomendacao = req.getParameter("recomendacao");
        boolean existenciaMinima = false;
        boolean existenciaMaxima = false;
        IndiceClassificacaoDAO indicesdao = new IndiceClassificacaoDAO();
        List<IndiceClassificacao> indicesClassificacao = indicesdao.listarIndicesClassificacao();
        for(int i = 0; i < indicesClassificacao.size(); i++){
            if(indicesClassificacao.get(i).getPorcentagemMinima() == porcentagemMinima){
                existenciaMinima = true;
            }
            if(indicesClassificacao.get(i).getPorcentagemMaxima() == porcentagemMaxima){
                existenciaMaxima = true;
            }
        }

        if(!existenciaMinima){
            if(!existenciaMaxima){
                IndiceClassificacao indiceClassificacao = new IndiceClassificacao(recomendacao, preocupacao, porcentagemMinima, porcentagemMaxima);
                indicesdao.inserirIndiceClassificacao(indiceClassificacao);
                req.setAttribute("erro", "Indice classificação inserido com sucesso!");
                req.getRequestDispatcher("ListarIndiceClassificacao").forward(req, resp);
            }else{
                req.setAttribute("erro", "Porcentagem máxima já existe");
                req.getRequestDispatcher("InserirIndiceClassificacao").forward(req, resp);
            }
        }else{
            req.setAttribute("erro", "Porcentagem máxima inserida com sucesso");
            req.getRequestDispatcher("InserirIndiceClassificacao").forward(req, resp);
        }


    }
}
