package org.example.utils.filtros;

import org.example.model.TipoEmpresa;

import java.util.LinkedList;
import java.util.List;

public class FiltrosTipoEmpresa {

    public List<TipoEmpresa> ordenarAtualizacoesRecentesTipoEmpresa(List<TipoEmpresa> tipoEmpresas){
        for(int i = 0; i < tipoEmpresas.size(); i++){
            for(int j = 0; j < tipoEmpresas.size() + 1; i++){
                if(tipoEmpresas.get(i).getUltimaAtualizacao().compareTo(tipoEmpresas.get(j).getUltimaAtualizacao()) > 0){
                    TipoEmpresa apoio = tipoEmpresas.get(i);
                    tipoEmpresas.set(i, tipoEmpresas.get(j));
                    tipoEmpresas.set(j, apoio);
                }
            }
        }
        return tipoEmpresas;
    }


    public List<TipoEmpresa> ordenarAtualizacoesAntigasTipoEmpresa(List<TipoEmpresa> tipoEmpresas){
        for(int i = 0; i < tipoEmpresas.size(); i++){
            for(int j = 0; j < tipoEmpresas.size() + 1; i++){
                if(tipoEmpresas.get(i).getUltimaAtualizacao().compareTo(tipoEmpresas.get(j).getUltimaAtualizacao()) < 0){
                    TipoEmpresa apoio = tipoEmpresas.get(i);
                    tipoEmpresas.set(i, tipoEmpresas.get(j));
                    tipoEmpresas.set(j, apoio);
                }
            }
        }
        return tipoEmpresas;
    }

    public List<TipoEmpresa> ordenarSomenteTipoEmpresaAtivo(List<TipoEmpresa> tipoEmpresas){
        List<TipoEmpresa> tipoEmpresasAtivos = new LinkedList<>();
        for(int i = 0; i < tipoEmpresas.size(); i++){
            if(tipoEmpresas.get(i).getStatus() == 'a'){
                tipoEmpresasAtivos.add(tipoEmpresas.get(i));
            }
        }
        return tipoEmpresasAtivos;
    }

    public List<TipoEmpresa> ordenarSomenteTipoEmpresaInativo(List<TipoEmpresa> tipoEmpresas){
        List<TipoEmpresa> tipoEmpresasInativos = new LinkedList<>();
        for(int i = 0; i < tipoEmpresas.size(); i++){
            if(tipoEmpresas.get(i).getStatus() == 'i'){
                tipoEmpresasInativos.add(tipoEmpresas.get(i));
            }
        }
        return tipoEmpresasInativos;
    }


}
