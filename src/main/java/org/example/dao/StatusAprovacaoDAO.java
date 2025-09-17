package org.example.dao;

//Importações
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import org.example.conexao.Conexao;
import org.example.model.StatusAprovacao;

public class StatusAprovacaoDAO {

    //Metodo para inserir novo status de aprovacao
    public boolean inserirStatusAprovacao(StatusAprovacao statusAprovacao){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoInserir = "insert into status_aprovacao (motivo_rejeicao, status, data_solicitacao, data_aprovacao) values (?,?,?,?)";//Comando SQL para inserir status de aprovação (CORRIGIDO: syntax)
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar se a inserção foi bem-sucedida
        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){//Prepara o comando SQL com retorno de chaves geradas
            //Atribui valores aos parâmetros do comando SQL
            pstmt.setString(1, statusAprovacao.getMotivoRejeicao());
            pstmt.setString(2, String.valueOf(statusAprovacao.getStatus()));
            pstmt.setDate(3, Date.valueOf(statusAprovacao.getDataSolicitacao()));
            pstmt.setDate(4, Date.valueOf(statusAprovacao.getDataAprovacao()));

            linhasAfetadas = pstmt.executeUpdate();//Executa a inserção

            if(linhasAfetadas > 0){//Verifica se a inserção foi bem-sucedida
                try(ResultSet rs = pstmt.getGeneratedKeys()){//Obtém as chaves geradas
                    if(rs.next()){
                        statusAprovacao.setId(rs.getInt(1)); // Define o ID gerado no objeto
                    }
                }
                return true;//Retorna sucesso
            }
            return false;//Retorna falha
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para listar status de aprovacao por ID
    public StatusAprovacao listarStatusAprovacaoPorID(int id){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from status_aprovacao where id_status_aprovacao = ?";//Comando SQL para buscar por ID
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setInt(1,id);//Atribui o ID ao parâmetro

            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){//Se encontrar resultado
                StatusAprovacao statusAprovacao = new StatusAprovacao(//Cria objeto com dados do banco
                        rs.getString("motivo_rejeicao"),
                        rs.getString("status").charAt(0),
                        rs.getDate("data_solicitacao").toLocalDate(),
                        rs.getDate("data_aprovacao").toLocalDate()
                );
                statusAprovacao.setId(rs.getInt("id_status_aprovacao"));//Define o ID do banco
                return statusAprovacao;//Retorna o status encontrado
            }
            return null;//Retorna null se não encontrado
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return null;//Retorna null em caso de erro
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para listar todos os status de aprovacao
    public List<StatusAprovacao> listarTodosStatusAprovacao(){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from status_aprovacao";//Comando SQL para listar todos
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        List<StatusAprovacao> statusAprovacao = new ArrayList<>();//Lista para armazenar resultados
        try(Statement stmt = conn.createStatement()){//Cria statement para consulta
            ResultSet rs = stmt.executeQuery(comandoListar);//Executa a consulta
            while(rs.next()){//Percorre todos os resultados
                StatusAprovacao status = new StatusAprovacao(//Cria objeto com dados do banco
                        rs.getString("motivo_rejeicao"),
                        rs.getString("status").charAt(0),
                        rs.getDate("data_solicitacao").toLocalDate(),
                        rs.getDate("data_aprovacao").toLocalDate()
                );
                status.setId(rs.getInt("id_status_aprovacao"));//Define o ID do banco
                statusAprovacao.add(status);//Adiciona à lista
            }
            return statusAprovacao;//Retorna a lista completa
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return statusAprovacao;//Retorna lista vazia em caso de erro
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para alterar o status pelo ID
    public boolean alterarStatusStatusAprovacao(int id, char status){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update status_aprovacao set status = ? where id_status_aprovacao = ?";//Comando SQL para atualizar status (CORRIGIDO: nome da variável)
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setString(1, String.valueOf(status));//Atribui novo status
            pstmt.setInt(2, id);//Atribui ID do status

            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para deletar status de aprovacao por ID
    public boolean deletarStatusAprovacao(int id){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoDeletar = "delete from status_aprovacao where id_status_aprovacao = ?";//Comando SQL para deletar status
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar exclusão
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){//Prepara o comando SQL
            pstmt.setInt(1, id);//Atribui ID do status
            linhasAfetadas = pstmt.executeUpdate();//Executa a exclusão
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }
}