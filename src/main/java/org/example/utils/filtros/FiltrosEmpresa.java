package org.example.utils.filtros;

import org.example.model.Empresa;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;

public class FiltrosEmpresa {
    public void ordenarNomeEmpresa(List<Empresa> empresas){
        for(int i = 0; i < empresas.size(); i++){
            for(int j = 0; j < empresas.size() + 1; j++){
                if(empresas.get(i).getNome().compareTo(empresas.get(j).getNome()) < 0){
                    Empresa apoio = empresas.get(i);
                    empresas.set(i, empresas.get(j));
                    empresas.set(j, apoio);
                }
            }
        }
    }

    public List<Empresa> ordenarEmpresaPorTipoEmpresa(List<Empresa> empresas, int idTipoEmpresa){
        List<Empresa> empresasOrdenadas = new ArrayList<>();
        for(int i = 0; i < empresas.size(); i++){
            if(empresas.get(i).getIdTipoEmpresa() == idTipoEmpresa){
                empresasOrdenadas.add(empresas.get(i));
            }
        }
        return empresasOrdenadas;
    }


    public List<Empresa> ordenarEmpresa(List<Empresa> empresas, boolean ordenarNome, boolean ordenarTipoEmpresa, Integer idTipoEmpresa){
        List<Empresa> empresasOrdenadas = empresas;
        if(!ordenarTipoEmpresa && idTipoEmpresa != null){
            throw new InvalidParameterException("IdTipoEmpresa recebido sem autorização de ordenar o tipoEmpresa");
        }
        if(ordenarNome){
            ordenarNomeEmpresa(empresasOrdenadas);
        }
        if(ordenarTipoEmpresa){
            empresasOrdenadas = ordenarEmpresaPorTipoEmpresa(empresasOrdenadas, idTipoEmpresa);
        }
        return empresasOrdenadas;
    }

}
