package org.example.utils.filtros;

import org.example.model.Empresa;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;

public class FiltrosEmpresa {

    public void ordenarNomeEmpresa(List<Empresa> empresas) {
        // Corrigido: limite do laço interno estava errado (+1 causava IndexOutOfBounds)
        for (int i = 0; i < empresas.size(); i++) {
            for (int j = i + 1; j < empresas.size(); j++) {
                // Troca se o nome da empresa i vier depois do nome da empresa j
                if (empresas.get(i).getNome().compareToIgnoreCase(empresas.get(j).getNome()) > 0) {
                    Empresa apoio = empresas.get(i);
                    empresas.set(i, empresas.get(j));
                    empresas.set(j, apoio);
                }
            }
        }
    }

    public List<Empresa> ordenarEmpresaPorTipoEmpresa(List<Empresa> empresas, Integer idTipoEmpresa) {
        List<Empresa> empresasOrdenadas = new ArrayList<>();

        // Evita erro se idTipoEmpresa for null
        if (idTipoEmpresa == null) return empresas;

        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getIdTipoEmpresa() == idTipoEmpresa) {
                empresasOrdenadas.add(empresas.get(i));
            }
        }

        return empresasOrdenadas;
    }

    public List<Empresa> ordenarEmpresa(List<Empresa> empresas, boolean ordenarNome, boolean ordenarTipoEmpresa, Integer idTipoEmpresa) {
        List<Empresa> empresasOrdenadas = new ArrayList<>(empresas);

        // Corrige verificação: só lança exceção se tiver idTipoEmpresa sem pedir ordenação por tipo
        if (!ordenarTipoEmpresa && idTipoEmpresa != null) {
            throw new InvalidParameterException("IdTipoEmpresa recebido sem autorização de ordenar o tipoEmpresa");
        }

        // Ordenar por nome, se solicitado
        if (ordenarNome) {
            ordenarNomeEmpresa(empresasOrdenadas);
        }

        // Filtrar por tipo, se solicitado
        if (ordenarTipoEmpresa && idTipoEmpresa != null) {
            empresasOrdenadas = ordenarEmpresaPorTipoEmpresa(empresasOrdenadas, idTipoEmpresa);
        }

        return empresasOrdenadas;
    }
}
