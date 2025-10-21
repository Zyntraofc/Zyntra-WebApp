package org.example.utils.filtros;

import org.example.model.StatusAprovacao;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

public class FiltrosStatusAprovacao {
    public List<StatusAprovacao> ordenarStatusAprovacaoPorStatus(List<StatusAprovacao> statusAprovacao, char status){
        if (statusAprovacao == null) {
            return new LinkedList<>();
        }

        List<StatusAprovacao> statusAprovacaoCorretos = new LinkedList<>();
        for(int i = 0; i < statusAprovacao.size(); i++){
            StatusAprovacao current = statusAprovacao.get(i);
            if (current == null) continue;
            if(current.getStatus() == status){
                statusAprovacaoCorretos.add(current);
            }
        }
        return statusAprovacaoCorretos;
    }

    public List<StatusAprovacao> ordenarAtualizacoesStatusAprovacao(List<StatusAprovacao> statusAprovacoes, boolean recente){
        if (statusAprovacoes == null) {
            return new LinkedList<>();
        }

        List<StatusAprovacao> listaValida = new LinkedList<>();
        for (StatusAprovacao status : statusAprovacoes) {
            if (status != null && status.getDataSolicitacao() != null) {
                listaValida.add(status);
            }
        }

        for(int i = 0; i < listaValida.size(); i++){
            for(int j = i + 1; j < listaValida.size(); j++){
                StatusAprovacao statusI = listaValida.get(i);
                StatusAprovacao statusJ = listaValida.get(j);

                if (statusI == null || statusJ == null) continue;
                if (statusI.getDataSolicitacao() == null || statusJ.getDataSolicitacao() == null) continue;

                if(recente){
                    if(statusI.getDataSolicitacao().compareTo(statusJ.getDataSolicitacao()) > 0){
                        StatusAprovacao apoio = listaValida.get(i);
                        listaValida.set(i, listaValida.get(j));
                        listaValida.set(j, apoio);
                    }
                }else{
                    if(statusI.getDataSolicitacao().compareTo(statusJ.getDataSolicitacao()) < 0){
                        StatusAprovacao apoio = listaValida.get(i);
                        listaValida.set(i, listaValida.get(j));
                        listaValida.set(j, apoio);
                    }
                }
            }
        }
        return listaValida;
    }

    public List<StatusAprovacao> ordenarStatusAprovacao(List<StatusAprovacao> statusesAprovacao ,boolean ordenarStatus, Character status, boolean ordenarAtualizacoes, Boolean recente){
        if (statusesAprovacao == null) {
            return new LinkedList<>();
        }

        List<StatusAprovacao> statusesAprovacaoOrdenados = new LinkedList<>(statusesAprovacao);

        // Aplicar filtro de status independentemente de outros filtros
        if(ordenarStatus && status != null){
            statusesAprovacaoOrdenados = ordenarStatusAprovacaoPorStatus(statusesAprovacaoOrdenados, status);
        }

        // Aplicar filtro de atualizações independentemente de outros filtros
        if(ordenarAtualizacoes && recente != null){
            statusesAprovacaoOrdenados = ordenarAtualizacoesStatusAprovacao(statusesAprovacaoOrdenados, recente);
        }

        return statusesAprovacaoOrdenados;
    }
}