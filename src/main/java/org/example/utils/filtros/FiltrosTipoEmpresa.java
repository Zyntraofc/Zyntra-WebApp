package org.example.utils.filtros;

import org.example.model.StatusAprovacao;
import org.example.model.TipoEmpresa;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FiltrosTipoEmpresa {

    public List<TipoEmpresa> ordenarAtualizacoesTipoEmpresa(List<TipoEmpresa> tipoEmpresas, boolean recente){
        for(int i = 0; i < tipoEmpresas.size(); i++){
            for(int j = 0; j < tipoEmpresas.size() + 1; i++){
                if(recente){
                    if(tipoEmpresas.get(i).getUltimaAtualizacao().compareTo(tipoEmpresas.get(j).getUltimaAtualizacao()) > 0){
                        TipoEmpresa apoio = tipoEmpresas.get(i);
                        tipoEmpresas.set(i, tipoEmpresas.get(j));
                        tipoEmpresas.set(j, apoio);
                    }
                }else{
                    if(tipoEmpresas.get(i).getUltimaAtualizacao().compareTo(tipoEmpresas.get(j).getUltimaAtualizacao()) < 0){
                        TipoEmpresa apoio = tipoEmpresas.get(i);
                        tipoEmpresas.set(i, tipoEmpresas.get(j));
                        tipoEmpresas.set(j, apoio);
                    }
                }
            }
        }
        return tipoEmpresas;
    }

    public List<TipoEmpresa> ordenarTipoEmpresaPorStatus(List<TipoEmpresa> tipoEmpresas, char status){
        List<TipoEmpresa> tipoEmpresasAtivos = new LinkedList<>();
        if(status == 'a' || status == 'i'){
            for(int i = 0; i < tipoEmpresas.size(); i++){
                if(tipoEmpresas.get(i).getStatus() == status){
                    tipoEmpresasAtivos.add(tipoEmpresas.get(i));
                }
            }
        }
        return tipoEmpresasAtivos;
    }

    public List<TipoEmpresa> ordenarTipoEmpresa(List<TipoEmpresa> tiposEmpresa , boolean ordenarStatus, Character status, boolean ordenarAtualizacoes, Boolean recente){
        List<TipoEmpresa> tiposEmpresaOrdenados = tiposEmpresa;
        if(!ordenarStatus && status != null ) {
            throw new InvalidParameterException("Status recebido sem autorizalçao para ordenar Status");
        }
        if(!ordenarAtualizacoes && recente != null){
            throw new InvalidParameterException("Falta de autorização para ordenar atualizações");
        }
        if(ordenarStatus){
            tiposEmpresaOrdenados = ordenarTipoEmpresaPorStatus(tiposEmpresa, status.charValue());
        }
        if(ordenarAtualizacoes){
            tiposEmpresaOrdenados = ordenarAtualizacoesTipoEmpresa(tiposEmpresaOrdenados, recente);
        }
        return tiposEmpresaOrdenados;
    }


}
