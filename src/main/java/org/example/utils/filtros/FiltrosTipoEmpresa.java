package org.example.utils.filtros;

///  Classe para filtragem da listagem de tipos de empresa do banco de dados

//Importações
import org.example.model.TipoEmpresa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Abertura da classe
public class FiltrosTipoEmpresa {

    //Metodo para ordenar atualizações de TipoEmpresa, ou das mais recentes (decrescente) ou mais antigas (crescente)
    public List<TipoEmpresa> ordenarAtualizacoesTipoEmpresa(List<TipoEmpresa> tipoEmpresas, boolean recente) {
        //Se TipoEmpresas for nulo retorna lista vazia
        if (tipoEmpresas == null) {
            return new LinkedList<>();
        }

        List<TipoEmpresa> listaValida = new LinkedList<>();

        //FOR para percorrer TipoEmpresas e verificar se não é nula e possui data de atualização válida
        for (TipoEmpresa tipo : tipoEmpresas) {
            if (tipo != null && tipo.getUltimaAtualizacao() != null) {
                listaValida.add(tipo);
            }
        }

        //FOR encadeado que percorre duas posições da lista simultaneamente para compará-las
        for (int i = 0; i < listaValida.size(); i++) {
            for (int j = i + 1; j < listaValida.size(); j++) {
                TipoEmpresa tipoI = listaValida.get(i);
                TipoEmpresa tipoJ = listaValida.get(j);

                //Verificando se os objetos e suas datas não são nulos antes de realizar a comparação
                if (tipoI != null && tipoJ != null
                        && tipoI.getUltimaAtualizacao() != null
                        && tipoJ.getUltimaAtualizacao() != null) {

                    //Se a opção recente for recebida como true, ordena de trás para frente (pela data de atualização)
                    if (recente) {
                        if (tipoI.getUltimaAtualizacao().compareTo(tipoJ.getUltimaAtualizacao()) > 0) {
                            TipoEmpresa apoio = listaValida.get(i);
                            listaValida.set(i, listaValida.get(j));
                            listaValida.set(j, apoio);
                        }
                    }
                    //Se não, ordena normalmente (pela data de atualização)
                    else {
                        if (tipoI.getUltimaAtualizacao().compareTo(tipoJ.getUltimaAtualizacao()) < 0) {
                            TipoEmpresa apoio = listaValida.get(i);
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


    //Metodo para filtrar tipos de empresas pelo Status de atividade (ativa se tiver alguma aprovada no banco de dados, inativa se não)
    public List<TipoEmpresa> ordenarTipoEmpresaPorStatus(List<TipoEmpresa> tipoEmpresas, char status){

        //Se a lista de tipos de empresa for vazia, retorna uma lista vazia
        if (tipoEmpresas == null) {
            return new LinkedList<>();
        }

        List<TipoEmpresa> tipoEmpresasFiltrados = new LinkedList<>();

        //FOR para percorrer lista de tipos de empresa
        for(int i = 0; i < tipoEmpresas.size(); i++){
            TipoEmpresa apoio = tipoEmpresas.get(i);
            //Faz verificações de se o elemento atual não é nulo e se ele está nas regras de negócio ('a' ou 'i')
            if (apoio != null){
                if(status == 'a' || status == 'i'){
                    //Se o status do elemento atual for igual ao Status selecionado adiciona a lista de filtrados
                    if(apoio.getStatus() == status){
                        tipoEmpresasFiltrados.add(apoio);
                    }
                }
            }

        }

        //Retorna os tipos de empresa filtrados
        return tipoEmpresasFiltrados;
    }


    //Metodo para ordenar os filtros na lista de tipos de empresa
    public List<TipoEmpresa> ordenarTipoEmpresa(List<TipoEmpresa> tiposEmpresa , boolean ordenarStatus, Character status, boolean ordenarAtualizacoes, Boolean recente){

        //Se a lista de tipos de empresa for nula já retorna lista vazia
        if (tiposEmpresa == null) {
            return new LinkedList<>();
        }

        List<TipoEmpresa> tiposEmpresaOrdenados = new ArrayList<>(tiposEmpresa);

        //Ordena por status e atividade, se solicitado
        if(ordenarStatus && status != null){
            tiposEmpresaOrdenados = ordenarTipoEmpresaPorStatus(tiposEmpresaOrdenados, status);
        }

        //Ordena por atualizações, se solicitado
        if(ordenarAtualizacoes && recente != null){
            tiposEmpresaOrdenados = ordenarAtualizacoesTipoEmpresa(tiposEmpresaOrdenados, recente);
        }

        //Retorna tipos de empresa ordenados e filtrados corretamente
        return tiposEmpresaOrdenados;
    }
}