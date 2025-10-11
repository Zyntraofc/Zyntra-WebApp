package org.example.servlet.StatusAprovacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.dao.StatusAprovacaoDAO;
import org.example.dao.EmpresaDAO;
import org.example.dao.TipoEmpresaDAO;
import org.example.model.StatusAprovacao;
import org.example.model.Empresa;
import java.util.List;

@WebServlet("/AlterarStatusAprovacao")
public class ServletAlterarStatusAprovacao extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int action = Integer.parseInt(req.getParameter("action"));
        int id = Integer.parseInt(req.getParameter("id"));
        StatusAprovacaoDAO statusdao = new StatusAprovacaoDAO();
        StatusAprovacao statusID = statusdao.listarStatusAprovacaoPorID(id);
        if (action == 0){
            StatusAprovacao status = statusdao.listarStatusAprovacaoPorID(id);
            req.setAttribute("alterarStatus", status);
            req.setAttribute("popup-alterar", true);
            List<StatusAprovacao> statuses = statusdao.listarTodosStatusAprovacao();
            req.setAttribute("statuses", statuses);
            req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
        }
        else if (action == 1) {
            String status = req.getParameter("status");
            String motivoRejeicao = req.getParameter("motivoRejeicao");

            //Status atual antes da mudança
            char statusAtual = statusID.getStatus();
            char novoStatus = status.charAt(0);

//            // atualizações
//            if (statusAtual != novoStatus) {
//                boolean sucesso = statusdao.alterarStatusStatusAprovacao(id, novoStatus);
//                if (sucesso){
//                    EmpresaDAO empresadao = new EmpresaDAO();
//                    Empresa empresa = empresadao.listarEmpresaPorIdStatusAprovacao(id);
//                    int idTipoEmpresa = empresa.getIdTipoEmpresa();
//                    TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
//                    // Se o novo status for ativo:
//                    if (novoStatus == 'a') {
//                        // Verificar se já existe alguma empresa ativa com esse tipo de empresa
////                        ...
//
//                        if (!existeAtiva) {
//                            tipoempresadao.alterarStatusTipoEmpresa(idTipoEmpresa, "a");
//                        }
//                    }
//
//                    // Se o status anterior era ativo e o novo não é:
//                    if (statusAtual == 'a') {
//                        TipoEmpresaDAO tipoempresadao = new TipoEmpresaDAO();
//                        // Verificar se ainda há outras empresas ativas desse tipo
////                        ...
//
//                        // Se não houver, tornar o tipo de empresa inativo
//                        if (!existeOutraAtiva) {
//                            tipoempresadao.alterarStatusTipoEmpresa(idTipoEmpresa, 'i');
//                        }
//                    }
//                }
//            }
//        }
//                if (statusdao.alterarStatusStatusAprovacao(id, status.charAt(0))) req.setAttribute("erro", "Atualizado com sucesso");
//                else req.setAttribute("erro", "Erro ao atualizar status!");}
//            if (!statusID.getMotivoRejeicao().equals(motivoRejeicao) && status.charAt(0) == 'r') {
//                if (statusdao.alterarMotivoStatusAprovacao(id, motivoRejeicao)) req.setAttribute("erro", "Atualizado com sucesso");
//                else req.setAttribute("erro", "Erro ao atualizar motivo!");}
//            }

            java.util.List<StatusAprovacao> statuses = statusdao.listarTodosStatusAprovacao();
            req.setAttribute("statuses", statuses);
            req.getRequestDispatcher("view/CrudStatusAprovacao.jsp").forward(req, resp);
        }
    }

