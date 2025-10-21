package org.example.utils.filtros;

import org.example.model.TipoEmpresa;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FiltrosTipoEmpresa {

    public List<TipoEmpresa> ordenarAtualizacoesTipoEmpresa(List<TipoEmpresa> tipoEmpresas, boolean recente){
        if (tipoEmpresas == null) {
            return new LinkedList<>();
        }

        List<TipoEmpresa> listaValida = new ArrayList<>();
        for (TipoEmpresa tipo : tipoEmpresas) {
            if (tipo != null && tipo.getUltimaAtualizacao() != null) {
                listaValida.add(tipo);
            }
        }

        for(int i = 0; i < listaValida.size(); i++){
            for(int j = i + 1; j < listaValida.size(); j++){
                TipoEmpresa tipoI = listaValida.get(i);
                TipoEmpresa tipoJ = listaValida.get(j);

                if (tipoI == null || tipoJ == null) continue;
                if (tipoI.getUltimaAtualizacao() == null || tipoJ.getUltimaAtualizacao() == null) continue;

                if(recente){
                    if(tipoI.getUltimaAtualizacao().compareTo(tipoJ.getUltimaAtualizacao()) > 0){
                        TipoEmpresa apoio = listaValida.get(i);
                        listaValida.set(i, listaValida.get(j));
                        listaValida.set(j, apoio);
                    }
                }else{
                    if(tipoI.getUltimaAtualizacao().compareTo(tipoJ.getUltimaAtualizacao()) < 0){
                        TipoEmpresa apoio = listaValida.get(i);
                        listaValida.set(i, listaValida.get(j));
                        listaValida.set(j, apoio);
                    }
                }
            }
        }
        return listaValida;
    }

    public List<TipoEmpresa> ordenarTipoEmpresaPorStatus(List<TipoEmpresa> tipoEmpresas, char status){
        if (tipoEmpresas == null) {
            return new LinkedList<>();
        }

        List<TipoEmpresa> tipoEmpresasFiltrados = new LinkedList<>();
        for(int i = 0; i < tipoEmpresas.size(); i++){
            TipoEmpresa current = tipoEmpresas.get(i);
            if (current == null) continue;
            if(status == 'a' || status == 'i'){
                if(current.getStatus() == status){
                    tipoEmpresasFiltrados.add(current);
                }
            } else {
                tipoEmpresasFiltrados.add(current);
            }
        }
        return tipoEmpresasFiltrados;
    }

    public List<TipoEmpresa> ordenarTipoEmpresa(List<TipoEmpresa> tiposEmpresa , boolean ordenarStatus, Character status, boolean ordenarAtualizacoes, Boolean recente){
        if (tiposEmpresa == null) {
            return new LinkedList<>();
        }

        List<TipoEmpresa> tiposEmpresaOrdenados = new ArrayList<>(tiposEmpresa);

        if(ordenarStatus && status != null){
            tiposEmpresaOrdenados = ordenarTipoEmpresaPorStatus(tiposEmpresaOrdenados, status);
        }
        if(ordenarAtualizacoes && recente != null){
            tiposEmpresaOrdenados = ordenarAtualizacoesTipoEmpresa(tiposEmpresaOrdenados, recente);
        }
        return tiposEmpresaOrdenados;
    }
}