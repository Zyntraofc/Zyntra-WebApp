package org.example.utils.filtros;

///  Classe para filtragem da listagem de statuses de aprovação do banco de dados

//Importações
import org.example.model.StatusAprovacao;
import java.util.LinkedList;
import java.util.List;

//Abertura da classe
public class FiltrosStatusAprovacao {

    //Metodo para ordenar Status aprovação pelo status de aprovação
    public List<StatusAprovacao> ordenarStatusAprovacaoPorStatus(List<StatusAprovacao> statusesAprovacao, char status){

        //Se a lista de StatusAprovação for nula, já retorna uma lista vazia
        if (statusesAprovacao == null) {
            return new LinkedList<>();
        }

        List<StatusAprovacao> statusAprovacaoCorretos = new LinkedList<>();

        //FOR que percorre os StatusAprovacao, se o status for igual ao selecionado, adiciona na lista
        for(int i = 0; i < statusesAprovacao.size(); i++){
            StatusAprovacao apoio = statusesAprovacao.get(i);
            if(apoio != null && apoio.getStatus() == status){
                statusAprovacaoCorretos.add(apoio);
            }
        }

        //Retorna statusesAprovacao filtrados
        return statusAprovacaoCorretos;
    }


    //Metodo para ordenar atualizações de StatusAprovação, ou das mais recentes (decrescencia) ou mais antigas (crescencia)
    public List<StatusAprovacao> ordenarAtualizacoesStatusAprovacao(List<StatusAprovacao> statusAprovacoes, boolean recente){
        //Se StatusesAprovacao forem nulor retorna lista vazia
        if (statusAprovacoes == null) {
            return new LinkedList<>();
        }


        List<StatusAprovacao> listaValida = new LinkedList<>();

        //FOR para percorrer StatusAprovacoes, verigica se não é n
        for (StatusAprovacao status : statusAprovacoes) {
            if (status != null && status.getDataSolicitacao() != null) {
                listaValida.add(status);
            }
        }

        //FOR encadeado que percorre duas posições da lista simutaneamente para compara-las
        for(int i = 0; i < listaValida.size(); i++){
            for(int j = i + 1; j < listaValida.size(); j++){
                StatusAprovacao statusI = listaValida.get(i);
                StatusAprovacao statusJ = listaValida.get(j);

                //Verificando se a data de solicitação de nenhum dos status é nula, ou se nenhum deles é nulo para continuar a ação do loop atual
                if (statusI != null && statusJ != null
                        && statusI.getDataSolicitacao() != null
                        && statusJ.getDataSolicitacao() != null) {
                    //Se a opção recente for recebida como true ordena de trás para frente (pela data de solicitação)
                    if(recente){
                        if(statusI.getDataSolicitacao().compareTo(statusJ.getDataSolicitacao()) > 0){
                            StatusAprovacao apoio = listaValida.get(i);
                            listaValida.set(i, listaValida.get(j));
                            listaValida.set(j, apoio);
                        }
                    }
                    //Se não ordena normalmente (pela data de solicitação)
                    else{
                        if(statusI.getDataSolicitacao().compareTo(statusJ.getDataSolicitacao()) < 0){
                            StatusAprovacao apoio = listaValida.get(i);
                            listaValida.set(i, listaValida.get(j));
                            listaValida.set(j, apoio);
                        }
                    }

                }


            }
        }
        //Retorna lista válida
        return listaValida;
    }


    //Metodo para ordena os filtros na lista de statuses de aprovação
    public List<StatusAprovacao> ordenarStatusAprovacao(List<StatusAprovacao> statusesAprovacao ,boolean ordenarStatus, Character status, boolean ordenarAtualizacoes, Boolean recente){

        //Valida se a lista é nula, para não atribuir null a lista que vamos retornar
        if (statusesAprovacao == null) {
            return new LinkedList<>();
        }


        List<StatusAprovacao> statusesAprovacaoOrdenados = new LinkedList<>(statusesAprovacao);

        // Aplicar filtro de status independentemente de outros filtros
        if (ordenarStatus && status != null) {
            statusesAprovacaoOrdenados = ordenarStatusAprovacaoPorStatus(statusesAprovacaoOrdenados, status);
        }

        // Aplicar filtro de atualizações independentemente de outros filtros
        if (ordenarAtualizacoes && recente != null) {
            statusesAprovacaoOrdenados = ordenarAtualizacoesStatusAprovacao(statusesAprovacaoOrdenados, recente);
        }

        //Retorna os statuses de aprovação ordenados
        return statusesAprovacaoOrdenados;
    }
}