package org.example.utils.filtros;

import org.example.model.StatusAprovacao;
import org.example.model.TipoEmpresa;

import java.util.LinkedList;
import java.util.List;

public class FiltrosStatusAprovacao {
    public List<StatusAprovacao> ordenarStatusAprovacaoPorStatus(List<StatusAprovacao> statusAprovacao, char status){
        List<StatusAprovacao> statusAprovacaoCorretos = new LinkedList<>();
        for(int i = 0; i < statusAprovacao.size(); i++){
            if(status == 'a' || status == 'p' || status == 'r'){
                if(statusAprovacao.get(i).getStatus() == status){
                    statusAprovacaoCorretos.add(statusAprovacao.get(i));
                }
            }
        }
        return statusAprovacaoCorretos;
    }


    public List<StatusAprovacao> ordenarAtualizacoesRecentesTipoEmpresa(List<StatusAprovacao> statusAprovacoes){
        for(int i = 0; i < statusAprovacoes.size(); i++){
            for(int j = 0; j < statusAprovacoes.size() + 1; i++){
                if(statusAprovacoes.get(i).getDataSolicitacao().compareTo(statusAprovacoes.get(j).getDataSolicitacao()) > 0){
                    StatusAprovacao apoio = statusAprovacoes.get(i);
                    statusAprovacoes.set(i, statusAprovacoes.get(j));
                    statusAprovacoes.set(j, apoio);
                }
            }
        }
        return statusAprovacoes;
    }


    public List<StatusAprovacao> ordenarAtualizacoesAntigosTipoEmpresa(List<StatusAprovacao> statusAprovacoes){
        for(int i = 0; i < statusAprovacoes.size(); i++){
            for(int j = 0; j < statusAprovacoes.size() + 1; i++){
                if(statusAprovacoes.get(i).getDataSolicitacao().compareTo(statusAprovacoes.get(j).getDataSolicitacao()) < 0){
                    StatusAprovacao apoio = statusAprovacoes.get(i);
                    statusAprovacoes.set(i, statusAprovacoes.get(j));
                    statusAprovacoes.set(j, apoio);
                }
            }
        }
        return statusAprovacoes;
    }


}
