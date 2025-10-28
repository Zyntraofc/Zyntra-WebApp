package org.example.utils.filtros;

/// Classe para filtragem da listagem de empresas do banco de dados

//Importações
import org.example.model.Empresa;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;
import org.example.dao.StatusAprovacaoDAO;
import org.example.model.StatusAprovacao;

//Abertura da classe
public class FiltrosEmpresa {

    //Metodo de ordenação alfabética pelo nome das empresas
    public void ordenarNomeEmpresa(List<Empresa> empresas) {

        //FOR encadeado para realizar algoritmo de troca
        for (int i = 0; i < empresas.size(); i++) {
            for (int j = i + 1; j < empresas.size(); j++) {
                //Comparando nomes
                if (empresas.get(i).getNome().compareToIgnoreCase(empresas.get(j).getNome()) > 0) {
                    //Objeto Empresa de apoio para a troca
                    Empresa apoio = empresas.get(i);
                    empresas.set(i, empresas.get(j));
                    empresas.set(j, apoio);
                }
            }
        }
    }


    //Metodo para filtrar somente empresas com determinado idTipoEmpresa
    public List<Empresa> ordenarEmpresaPorTipoEmpresa(List<Empresa> empresas, Integer idTipoEmpresa) {
        List<Empresa> empresasOrdenadas = new ArrayList<>();

        // Evita erro se idTipoEmpresa for null
        if (idTipoEmpresa == null) {
            return empresas;
        }

        //FOR com verificação para adicionar a lista ordenada somente empresas com tal idTipoEmpresa
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getIdTipoEmpresa() == idTipoEmpresa) {
                empresasOrdenadas.add(empresas.get(i));
            }
        }

        //Retornando empresas ordenadas
        return empresasOrdenadas;
    }

    //Metodo para filtrar apenas empresas com determinado idIndiceClassificacao
    public List<Empresa> ordenarEmpresaPorIndiceClassificacao(List<Empresa> empresas, Integer idIndiceClassificacao){
        List<Empresa> empresasOrdenadas = new ArrayList<>();
        //for com verificação de se o idIndiceClassificacao da empresa é igual ao passado, se for adiciona às empresas filtradas
        for(int i = 0; i < empresas.size(); i++){
            if(empresas.get(i).getIdIndiceClassificacao() == idIndiceClassificacao){
                empresasOrdenadas.add(empresas.get(i));
            }
        }

        //Retorna as empresas ordenadas já
        return empresasOrdenadas;
    }

    //Metodo para filtrar apenas empresas com determinado status de aprovação
    public List<Empresa> ordenarEmpresaPorStatusAprovacao(List<Empresa> empresas, Character status) {
        //Abrindo classe de filtros de StatusAprovacao
        FiltrosStatusAprovacao filtroStatus = new FiltrosStatusAprovacao();

        StatusAprovacaoDAO statusDao = new StatusAprovacaoDAO();
        //Definindo listaStatus com a aplicação do filtro já feito em StatusAprovacao, através da listagem dos Status exis
        List<StatusAprovacao> statuses = filtroStatus.ordenarStatusAprovacaoPorStatus(
                statusDao.listarTodosStatusAprovacao(), status
        );

        List<Empresa> empresasOrdenadas = new ArrayList<>();
        //ForEach encadeado para verificar idStatusAprovacao de cada empresa
        for (StatusAprovacao st : statuses) {
            for (Empresa e : empresas) {
                if (e.getIdStatusAprovacao() == st.getId()) {
                    empresasOrdenadas.add(e);
                }
            }
        }

        //Retornando empresas ordenadas
        return empresasOrdenadas;
    }


    //Metodo para ordenar os filtros na lista de empresas
    public List<Empresa> ordenarEmpresa(List<Empresa> empresas, boolean ordenarNome, boolean ordenarTipoEmpresa, Integer idTipoEmpresa, boolean ordenarIndiceClassificacao, Integer idIndiceClassificacao, boolean ordenarStatus, Character status) {
        List<Empresa> empresasOrdenadas = new ArrayList<>(empresas);

        // Só lança exceção se tiver idTipoEmpresa sem pedir ordenação por tipo
        if (!ordenarTipoEmpresa && idTipoEmpresa != null) {
            throw new InvalidParameterException("IdTipoEmpresa recebido sem autorização de ordenar o tipoEmpresa");
        }


        // Filtrar por tipo, se solicitado
        if (ordenarTipoEmpresa && idTipoEmpresa != null) {
            empresasOrdenadas = ordenarEmpresaPorTipoEmpresa(empresasOrdenadas, idTipoEmpresa);
        }

        //Ordenar por indice de classificação, se solicitado
        if(ordenarIndiceClassificacao && idIndiceClassificacao != null){
            empresasOrdenadas = ordenarEmpresaPorIndiceClassificacao(empresasOrdenadas, idIndiceClassificacao);
        }

        //Ordenar por status de aprovação, se solicitado
        if(ordenarStatus && status != null){
            empresasOrdenadas = ordenarEmpresaPorStatusAprovacao(empresasOrdenadas, status);
        }

        // Ordenar por nome, se solicitado (Por ultimo pois se não os outros métodos alteram ordenação da lista)
        if (ordenarNome) {
            ordenarNomeEmpresa(empresasOrdenadas);
        }

        //Retornando lista final ordenada
        return empresasOrdenadas;
    }
}
