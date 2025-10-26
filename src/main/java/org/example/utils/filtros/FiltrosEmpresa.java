package org.example.utils.filtros;

import org.example.model.Empresa;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;

public class FiltrosEmpresa {

    public void ordenarNomeEmpresa(List<Empresa> empresas) {

        for (int i = 0; i < empresas.size(); i++) {
            for (int j = i + 1; j < empresas.size(); j++) {

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

    public List<Empresa> ordenarEmpresaPorIndiceClassificacao(List<Empresa> empresas, Integer idIndiceClassificacao){
        List<Empresa> empresasOrdenadas = new ArrayList<>();
        for(int i = 0; i < empresas.size(); i++){
            if(empresas.get(i).getIdIndiceClassificacao() == idIndiceClassificacao){
                empresasOrdenadas.add(empresas.get(i));
            }
        }
        return empresasOrdenadas;
    }

    public List<Empresa> ordenarEmpresaPorStatusAprovacao(List<Empresa> empresas, Character status) {
        FiltrosStatusAprovacao filtroStatus = new FiltrosStatusAprovacao();
        StatusAprovacaoDAO statusDao = new StatusAprovacaoDAO();
        List<StatusAprovacao> statuses = filtroStatus.ordenarStatusAprovacaoPorStatus(
                statusDao.listarTodosStatusAprovacao(), status
        );

        List<Empresa> empresasOrdenadas = new ArrayList<>();

        for (StatusAprovacao st : statuses) {
            for (Empresa e : empresas) {
                if (e.getIdStatusAprovacao() == st.getId()) {
                    empresasOrdenadas.add(e);
                }
            }
        }

        return empresasOrdenadas;
    }


    public List<Empresa> ordenarEmpresa(List<Empresa> empresas, boolean ordenarNome, boolean ordenarTipoEmpresa, Integer idTipoEmpresa, boolean ordenarIndiceClassificacao, Integer idIndiceClassificacao, boolean ordenarStatus, Character status) {
        List<Empresa> empresasOrdenadas = new ArrayList<>(empresas);

        // Corrige verificação: só lança exceção se tiver idTipoEmpresa sem pedir ordenação por tipo
        if (!ordenarTipoEmpresa && idTipoEmpresa != null) {
            throw new InvalidParameterException("IdTipoEmpresa recebido sem autorização de ordenar o tipoEmpresa");
        }


        // Filtrar por tipo, se solicitado
        if (ordenarTipoEmpresa && idTipoEmpresa != null) {
            empresasOrdenadas = ordenarEmpresaPorTipoEmpresa(empresasOrdenadas, idTipoEmpresa);
        }

        if(ordenarIndiceClassificacao && idIndiceClassificacao != null){
            empresasOrdenadas = ordenarEmpresaPorIndiceClassificacao(empresasOrdenadas, idIndiceClassificacao);
        }

        if(ordenarStatus && status != null){
            empresasOrdenadas = ordenarEmpresaPorStatusAprovacao(empresasOrdenadas, status);
        }

        // Ordenar por nome, se solicitado
        if (ordenarNome) {
            ordenarNomeEmpresa(empresasOrdenadas);
        }

        return empresasOrdenadas;
    }
}
