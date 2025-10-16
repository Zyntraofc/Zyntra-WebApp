package org.example.utils.filtros;

import org.example.model.Empresa;

import java.util.List;

public class FiltrosEmpresa {
    public List<Empresa> ordenarNomeEmpresa(List<Empresa> empresas){
        for(int i = 0; i < empresas.size(); i++){
            for(int j = 0; j < empresas.size() + 1; j++){
                if(empresas.get(i).getNome().compareTo(empresas.get(j).getNome()) < 0){
                    Empresa apoio = empresas.get(i);
                    empresas.set(i, empresas.get(j));
                    empresas.set(j, apoio);
                }
            }
        }
        return empresas;
    }

}
