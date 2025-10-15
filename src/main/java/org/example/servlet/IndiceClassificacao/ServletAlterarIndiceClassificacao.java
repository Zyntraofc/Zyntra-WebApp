package org.example.servlet.IndiceClassificacao;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.conexao.ConexaoManager;
import org.example.dao.IndiceClassificacaoDAO;
import org.example.model.IndiceClassificacao;

@WebServlet("/AlterarIndiceClassificacao")
public class ServletAlterarIndiceClassificacao extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));

        IndiceClassificacaoDAO indicedao = new IndiceClassificacaoDAO();

        if(action == 0){
            IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);
            req.setAttribute("indiceClassificacao", indiceClassificacao);
            req.setAttribute("popup-alterar", true);
            req.getRequestDispatcher("ListarIndiceClassificacao").forward(req, resp);
        }else if(action == 1){
            IndiceClassificacao indiceClassificacao = indicedao.listarIndiceClassificacaoPorId(id);
            String preocupacao = req.getParameter("preocupacao");
            Double porcentagemMinima = Double.parseDouble(req.getParameter("porcentagemMinima"));
            Double porcentagemMaxima = Double.parseDouble(req.getParameter("porcentagemMaxima"));
            String recomendacao = req.getParameter("recomendacao");

            List<IndiceClassificacao> indicesExistentes = indicedao.listarIndicesClassificacao();
            boolean sobrepoe = false;
            for (IndiceClassificacao i : indicesExistentes) {
                double minExistente = i.getPorcentagemMinima();
                double maxExistente = i.getPorcentagemMaxima();
                // Verifica se há sobreposição de faixas
                if (!(porcentagemMaxima <= minExistente || porcentagemMinima >= maxExistente)) {
                    if (i.getId()!=id) sobrepoe = true;
                    break;
                }
            }
            if (sobrepoe) {
                req.setAttribute("erro", "O intervalo informado sobrepõe outro já existente!");
                req.getRequestDispatcher("view/InserirIndiceClassificacao.jsp").forward(req, resp);
            } else if (porcentagemMinima >= porcentagemMaxima) {
                req.setAttribute("erro", "A porcentagem mínima deve ser menor que a máxima!");
                req.getRequestDispatcher("view/InserirIndiceClassificacao.jsp").forward(req, resp);
            } else{
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
        ConexaoManager.desconectar();
    }
}
