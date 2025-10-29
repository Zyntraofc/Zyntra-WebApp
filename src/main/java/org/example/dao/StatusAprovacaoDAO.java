package org.example.dao;

///Classe criada com objetivo de armazenar os metodos responsáveis por ações na tabela "status_aprovacao" do banco de dados

//Importações
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import org.example.conexao.ConexaoManager;
import org.example.model.StatusAprovacao;

//Abertura da classe
public class StatusAprovacaoDAO {

    // Metodo para inserir status aprovacao no banco de dados
    public int inserirStatusAprovacao(StatusAprovacao statusAprovacao) {

        //Comando de inserção em SQL
        String comandoInserir = "insert into status_aprovacao (motivo_rejeicao, status, data_solicitacao, data_aprovacao) values (?,?,?,?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = ConexaoManager.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL, executará o comando e retornará chaves geradas pelo banco de dados
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {

            //Setando valores nos '?' do comando
            // Motivo rejeição (pode ser null)
            if (statusAprovacao.getMotivoRejeicao() != null) {
                pstmt.setString(1, statusAprovacao.getMotivoRejeicao());
            } else {
                pstmt.setNull(1, Types.VARCHAR);
            }

            // Status (assumindo que não pode ser null)
            pstmt.setString(2, String.valueOf(statusAprovacao.getStatus()));

            // Data solicitação (NÃO pode ser null)
            pstmt.setDate(3, Date.valueOf(statusAprovacao.getDataSolicitacao()));

            // Data aprovação (pode ser null)
            if (statusAprovacao.getDataAprovacao() != null) {
                pstmt.setDate(4, Date.valueOf(statusAprovacao.getDataAprovacao()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            //Executa o comando e armazena o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se ação foi bem sucedida
            if (linhasAfetadas > 0) {
                //Atributi chave gerada ao id do objeto
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    //Se houver alguma chave gerada atribui ela ao id
                    if (rs.next()) {
                        //Commita ação e retorna id caso tenha dado certo
                        ConexaoManager.commit();
                        return rs.getInt(1);
                    }
                }
            }

            //Se não der certo desfaz a ação e retorna 0
            ConexaoManager.rollback();
            return 0;

        } 
        //Em casos de erro no banco de dados desfaz a ação
        catch (SQLException sqle) {

            //Lista os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna -1
            return -1;
        }
    }

    // Metodo para buscar status aprovacao pelo ID
    public StatusAprovacao listarStatusAprovacaoPorID(int id) {

        //Comando de listagem por ID no banco de dados
        String comandoListar = "select * from status_aprovacao where id_status_aprovacao = ?";

        //Conecta com o banco de dados
        Connection conn = ConexaoManager.conectar();

        //Iniciando executor que: Atribuirá valores ao comando sql e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            
            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, id);

            //Executa a consulta e atribui o valor retornado a variável de resultSet
            ResultSet rs = pstmt.executeQuery();

            //Verifica se a consulta retornou algo
            if (rs.next()) {

                //Define objeto StatusAprovacao com valores do banco de dados
                // Tratamento seguro para campos nulos
                String motivoRejeicao = rs.getString("motivo_rejeicao");
                if (rs.wasNull()) motivoRejeicao = null;

                char status = rs.getString("status").charAt(0);

                // Data solicitação (NÃO pode ser null)
                LocalDate dataSolicitacao = rs.getDate("data_solicitacao").toLocalDate();

                // Data aprovação (pode ser null)
                LocalDate dataAprovacao = null;
                Date dataAprovacaoSql = rs.getDate("data_aprovacao");
                if (dataAprovacaoSql != null) {
                    dataAprovacao = dataAprovacaoSql.toLocalDate();
                }

                StatusAprovacao statusAprovacaoTemporario = new StatusAprovacao(
                        motivoRejeicao,
                        status,
                        dataSolicitacao,
                        dataAprovacao
                );
                //Seta o ID vindo do banco
                statusAprovacaoTemporario.setId(rs.getInt("id_status_aprovacao"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return statusAprovacaoTemporario;
            }

            //Só commita consulta no banco de dados e retorna null
            ConexaoManager.commit();
            return null;

        } 
        //Em casos de erros no banco de dados desfaz ação
        catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna null
            return null;
        }
    }

    // Metodo para listar todos os status aprovacao do banco de dados
    public List<StatusAprovacao> listarTodosStatusAprovacao() {

        //Comando de listagem SQL
        String comandoListar = "select * from status_aprovacao order by 1";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de StatusAprovacao
        List<StatusAprovacao> statusAprovacao = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (Statement stmt = conn.createStatement()) {

            //Executa consulta e armazena resultado em variável resultSet
            ResultSet rs = stmt.executeQuery(comandoListar);

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                // Tratamento seguro para campos nulos
                String motivoRejeicao = rs.getString("motivo_rejeicao");
                if (rs.wasNull()) motivoRejeicao = null;

                char status = rs.getString("status").charAt(0);

                // Data solicitação (NÃO pode ser null)
                LocalDate dataSolicitacao = rs.getDate("data_solicitacao").toLocalDate();

                // Data aprovação (pode ser null)
                LocalDate dataAprovacao = null;
                Date dataAprovacaoSql = rs.getDate("data_aprovacao");
                if (dataAprovacaoSql != null) {
                    dataAprovacao = dataAprovacaoSql.toLocalDate();
                }

                StatusAprovacao statusAprovacaoTemporario = new StatusAprovacao(
                        motivoRejeicao,
                        status,
                        dataSolicitacao,
                        dataAprovacao
                );

                //Seta o id no objeto
                statusAprovacaoTemporario.setId(rs.getInt("id_status_aprovacao"));

                //Adiciona a lista
                statusAprovacao.add(statusAprovacaoTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de status aprovacao
            return statusAprovacao;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna lista de status aprovacao (vazia)
            return statusAprovacao;
        }
    }

    // Metodo para alterar status a partir do ID
    public boolean alterarStatusStatusAprovacao(int id, char status) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar;

        if (status == 'a') {
            // Se status for 'a', atualiza status e data_aprovacao (se for nula) e limpa motivo_rejeicao
            comandoAtualizar = "update status_aprovacao set status = ?, data_aprovacao = COALESCE(data_aprovacao, CURRENT_DATE), motivo_rejeicao = NULL where id_status_aprovacao = ?";
        } else if (status == 'p') {
            // Se status for 'p', atualiza status, limpa motivo_rejeicao e data_aprovacao
            comandoAtualizar = "update status_aprovacao set status = ?, motivo_rejeicao = NULL, data_aprovacao = NULL where id_status_aprovacao = ?";
        } else {
            // Para outros status, só atualiza o status
            comandoAtualizar = "update status_aprovacao set status = ? where id_status_aprovacao = ?";
        }

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta status no primeiro '?' do comando SQL
            pstmt.setString(1, String.valueOf(status));
            //Seta o id no segundo '?' do comando SQL
            pstmt.setInt(2, id);

            //Executa ação no banco de dados e guarda o total de linhas que foram afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;
        } 
        //Em casos de erros no banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();
            //Desfaz a ação
            ConexaoManager.rollback();
            //Retorna false
            return false;
        }
    }

    // Metodo para alterar motivo a partir do ID
    public boolean alterarMotivoStatusAprovacao(int id, String motivoRejeicao) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update status_aprovacao set motivo_rejeicao = ? where id_status_aprovacao = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta motivo rejeicao no primeiro '?' do comando SQL
            pstmt.setString(1, String.valueOf(motivoRejeicao));
            //Seta o id no segundo '?' do comando SQL
            pstmt.setInt(2, id);

            //Executa ação no banco de dados e guarda o total de linhas que foram afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;
        } 
        //Em casos de erros no banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();
            //Desfaz a ação
            ConexaoManager.rollback();
            //Retorna false
            return false;
        }
    }

    // Metodo para deletar status aprovacao pelo ID
    public boolean deletarStatusAprovacao(int id) {

        //Comando de deleção SQL com base no id
        String comandoDeletar = "delete from status_aprovacao where id_status_aprovacao = ?";

        //Abre conexão com banco de dados e armazena-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)) {

            //Seta Id no comando SQL
            pstmt.setInt(1, id);

            //Executa comando SQL e guarda o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se a ação tiver sido concluida corretamente, commita ela e retorna true
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }

            //Se a ação der errado desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;
        } 
        //Em casos de erro no banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();
        
            //Retorna false
            return false;
        }
    }
}