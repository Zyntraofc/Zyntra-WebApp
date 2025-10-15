package org.example.dao;

//Importações
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import org.example.conexao.ConexaoManager;
import org.example.model.StatusAprovacao;

public class StatusAprovacaoDAO {

    public int inserirStatusAprovacao(StatusAprovacao statusAprovacao){
        String comandoInserir = "insert into status_aprovacao (motivo_rejeicao, status, data_solicitacao, data_aprovacao) values (?,?,?,?)";//Comando SQL para inserir status de aprovação
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar se a inserção foi bem-sucedida

        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){//Prepara o comando SQL com retorno de chaves geradas
            // Motivo rejeição (pode ser null)
            if(statusAprovacao.getMotivoRejeicao() != null){
                pstmt.setString(1, statusAprovacao.getMotivoRejeicao());
            } else {
                pstmt.setNull(1, Types.VARCHAR);//Define como NULL no banco
            }

            // Status (assumindo que não pode ser null)
            pstmt.setString(2, String.valueOf(statusAprovacao.getStatus()));

            // Data solicitação (NÃO pode ser null)
            pstmt.setDate(3, Date.valueOf(statusAprovacao.getDataSolicitacao()));

            // Data aprovação (pode ser null)
            if(statusAprovacao.getDataAprovacao() != null){
                pstmt.setDate(4, Date.valueOf(statusAprovacao.getDataAprovacao()));
            } else {
                pstmt.setNull(4, Types.DATE);//Define como NULL no banco
            }

            linhasAfetadas = pstmt.executeUpdate();//Executa a inserção

            if(linhasAfetadas > 0){//Verifica se a inserção foi bem-sucedida
                try(ResultSet rs = pstmt.getGeneratedKeys()){//Obtém as chaves geradas
                    if(rs.next()){
                        ConexaoManager.commitAndClose();
                        return rs.getInt(1); // retorna o ID gerado no objeto
                    }
                }
            }
            ConexaoManager.rollbackAndClose();
            return 0;
        } catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            ConexaoManager.rollbackAndClose();
            return -1;//Retorna falha
        }
    }

    //Metodo para listar status de aprovacao por ID
    public StatusAprovacao listarStatusAprovacaoPorID(int id){
        String comandoListar = "select * from status_aprovacao where id_status_aprovacao = ?";//Comando SQL para buscar por ID
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados

        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setInt(1,id);//Atribui o ID ao parâmetro

            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){//Se encontrar resultado
                // Tratamento seguro para campos nulos
                String motivoRejeicao = rs.getString("motivo_rejeicao");
                if(rs.wasNull()) motivoRejeicao = null;//Verifica se era NULL no banco

                char status = rs.getString("status").charAt(0);//Converte string para char

                // Data solicitação (NÃO pode ser null)
                LocalDate dataSolicitacao = rs.getDate("data_solicitacao").toLocalDate();

                // Data aprovação (pode ser null) - CORREÇÃO: usar rs.wasNull() corretamente
                LocalDate dataAprovacao = null;
                Date dataAprovacaoSql = rs.getDate("data_aprovacao");
                if(dataAprovacaoSql != null){
                    dataAprovacao = dataAprovacaoSql.toLocalDate();
                }

                // Cria objeto com tratamento de null
                StatusAprovacao statusAprovacao = new StatusAprovacao(//Cria objeto com dados do banco
                        motivoRejeicao,
                        status,
                        dataSolicitacao,
                        dataAprovacao
                );
                statusAprovacao.setId(rs.getInt("id_status_aprovacao"));//Define o ID do banco
                ConexaoManager.commitAndClose();
                return statusAprovacao;//Retorna o status encontrado
            }
            ConexaoManager.commitAndClose();
            return null;//Retorna null se não encontrado
        } catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            ConexaoManager.rollbackAndClose();
            return null;//Retorna null em caso de erro
        }
    }

    //Metodo para listar todos os status de aprovacao
    public List<StatusAprovacao> listarTodosStatusAprovacao(){
        String comandoListar = "select * from status_aprovacao";//Comando SQL para listar todos
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        List<StatusAprovacao> statusAprovacao = new ArrayList<>();//Lista para armazenar resultados

        try(Statement stmt = conn.createStatement()){//Cria statement para consulta
            ResultSet rs = stmt.executeQuery(comandoListar);//Executa a consulta

            while(rs.next()){//Percorre todos os resultados
                // Tratamento seguro para campos nulos
                String motivoRejeicao = rs.getString("motivo_rejeicao");
                if(rs.wasNull()) motivoRejeicao = null;//Verifica se era NULL no banco

                char status = rs.getString("status").charAt(0);//Converte string para char

                // Data solicitação (NÃO pode ser null)
                LocalDate dataSolicitacao = rs.getDate("data_solicitacao").toLocalDate();

                // Data aprovação (pode ser null) - CORREÇÃO: usar rs.wasNull() corretamente
                LocalDate dataAprovacao = null;
                Date dataAprovacaoSql = rs.getDate("data_aprovacao");
                if(dataAprovacaoSql != null){
                    dataAprovacao = dataAprovacaoSql.toLocalDate();
                }

                // Cria objeto com todos os campos (tratando nulls)
                StatusAprovacao statusObj = new StatusAprovacao(//Cria objeto com dados do banco
                        motivoRejeicao,
                        status,
                        dataSolicitacao,
                        dataAprovacao
                );
                statusObj.setId(rs.getInt("id_status_aprovacao"));//Define o ID do banco
                statusAprovacao.add(statusObj);//Adiciona à lista
            }
            ConexaoManager.commitAndClose();
            return statusAprovacao;//Retorna a lista completa
        } catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            ConexaoManager.rollbackAndClose();
            return statusAprovacao;//Retorna lista vazia em caso de erro
        }
    }

    //Metodo para alterar o status pelo ID
    public boolean alterarStatusStatusAprovacao(int id, char status) {
        Connection conn = ConexaoManager.conectar();
        String comandoAtualizar;

        if (status == 'a') {
            // Se status for 'a', atualiza status e data_aprovacao (se for nula) e limpa motivo_rejeicao
            comandoAtualizar = "update status_aprovacao set status = ?, data_aprovacao = COALESCE(data_aprovacao, CURRENT_DATE), motivo_rejeicao = NULL where id_status_aprovacao = ?";
        } else if (status == 'p') {
            // Se status for 'p', atualiza status, limpa motivo_rejeicao e data_aprovacao
            comandoAtualizar = "update status_aprovacao set status = ?, motivo_rejeicao = NULL, data_aprovacao = NULL where id_status_aprovacao = ?";
        }
        else {
            // Para outros status, só atualiza o status
            comandoAtualizar = "update status_aprovacao set status = ? where id_status_aprovacao = ?";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {
            pstmt.setString(1, String.valueOf(status));
            pstmt.setInt(2, id);

            int linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commitAndClose();
                return true;
            }
            ConexaoManager.rollbackAndClose();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollbackAndClose();
            return false;
        }
    }

    //Metodo para alterar o status pelo ID
    public boolean alterarMotivoStatusAprovacao(int id, String motivoRejeicao) {
        Connection conn = ConexaoManager.conectar();
        String comandoAtualizar = "update status_aprovacao set motivo_rejeicao = ? where id_status_aprovacao = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {
            pstmt.setString(1, String.valueOf(motivoRejeicao));
            pstmt.setInt(2, id);

            int linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commitAndClose();
                return true;
            }
            ConexaoManager.rollbackAndClose();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollbackAndClose();
            return false;
        }
    }

    //Metodo para deletar status de aprovacao por ID
    public boolean deletarStatusAprovacao(int id){
        String comandoDeletar = "delete from status_aprovacao where id_status_aprovacao = ?";//Comando SQL para deletar status
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar exclusão

        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){//Prepara o comando SQL
            pstmt.setInt(1, id);//Atribui ID do status
            linhasAfetadas = pstmt.executeUpdate();//Executa a exclusão
            if(linhasAfetadas > 0){
                ConexaoManager.commitAndClose();
                return true;
            }
            ConexaoManager.rollbackAndClose();
            return false;
        } catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            ConexaoManager.rollbackAndClose();
            return false;//Retorna falha
        }
    }
}