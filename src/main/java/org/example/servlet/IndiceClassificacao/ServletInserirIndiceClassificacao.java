package org.example.servlet.IndiceClassificacao;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;

@WebServlet("/private/InserirIndiceClassificacao")
public class ServletInserirIndiceClassificacao extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("popup-inserir", true);
        req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String preocupacao = req.getParameter("preocupacao");
        double porcentagemMinima = Double.parseDouble(req.getParameter("porcentagemMinima"));
        double porcentagemMaxima = Double.parseDouble(req.getParameter("porcentagemMaxima"));
        String recomendacao = req.getParameter("recomendacao");
        IndiceClassificacaoDAO indicesdao = new IndiceClassificacaoDAO();
        List<IndiceClassificacao> indicesExistentes = indicesdao.listarIndicesClassificacao();

        boolean sobrepoe = false;
        for (IndiceClassificacao i : indicesExistentes) {
            double minExistente = i.getPorcentagemMinima();
            double maxExistente = i.getPorcentagemMaxima();

            if (!(porcentagemMaxima <= minExistente || porcentagemMinima >= maxExistente)) {
                sobrepoe = true;
                break;
            }
        }
        if (sobrepoe) {
            req.setAttribute("erro", "O intervalo informado sobrepõe outro já existente!");
            req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
        } else if (porcentagemMinima >= porcentagemMaxima) {
            req.setAttribute("erro", "A porcentagem mínima deve ser menor que a máxima!");
            req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
        } else {
            IndiceClassificacao novo = new IndiceClassificacao(recomendacao, preocupacao, porcentagemMinima, porcentagemMaxima);
            if (indicesdao.inserirIndiceClassificacao(novo))req.setAttribute("erro", "Índice de classificação inserido com sucesso!");
            else req.setAttribute("erro", "Erro ao inserir índice");

            req.getRequestDispatcher("/private/ListarIndiceClassificacao").forward(req, resp);
        }
    }
    public void destroy(){
        ConexaoManager.desconectar();
    }
}
