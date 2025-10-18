package org.example.utils.filtros;

import org.example.model.StatusAprovacao;
import org.example.model.TipoEmpresa;
import java.security.InvalidParameterException;
import java.util.ArrayList;
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


    public List<StatusAprovacao> ordenarAtualizacoesStatusAprovacao(List<StatusAprovacao> statusAprovacoes, boolean recente){
        for(int i = 0; i < statusAprovacoes.size(); i++){
            for(int j = 0; j < statusAprovacoes.size() + 1; i++){
                if(recente){
                    if(statusAprovacoes.get(i).getDataSolicitacao().compareTo(statusAprovacoes.get(j).getDataSolicitacao()) < 0){
                        StatusAprovacao apoio = statusAprovacoes.get(i);
                        statusAprovacoes.set(i, statusAprovacoes.get(j));
                        statusAprovacoes.set(j, apoio);
                    }
                }else{
                    if(statusAprovacoes.get(i).getDataSolicitacao().compareTo(statusAprovacoes.get(j).getDataSolicitacao()) > 0){
                        StatusAprovacao apoio = statusAprovacoes.get(i);
                        statusAprovacoes.set(i, statusAprovacoes.get(j));
                        statusAprovacoes.set(j, apoio);
                    }
                }
            }
        }
        return statusAprovacoes;
    }

    public List<StatusAprovacao> ordenarStatusAprovacao(List<StatusAprovacao> statusesAprovacao ,boolean ordenarStatus, Character status, boolean ordenarAtualizacoes, Boolean recente){
        List<StatusAprovacao> statusesAprovacaoOrdenados = statusesAprovacao;
        if(!ordenarStatus && status != null ) {
            throw new InvalidParameterException("Status recebido sem autorizalçao para ordenar Status");
        }
        if(!ordenarAtualizacoes && recente != null){
            throw new InvalidParameterException("Falta de autorização para ordenar atualizações");
        }
        if(ordenarStatus){
            statusesAprovacaoOrdenados = ordenarStatusAprovacaoPorStatus(statusesAprovacaoOrdenados, status.charValue());
        }
        if(ordenarAtualizacoes){
            statusesAprovacaoOrdenados = ordenarAtualizacoesStatusAprovacao(statusesAprovacaoOrdenados, recente);
        }
        return statusesAprovacaoOrdenados;
    }
}
