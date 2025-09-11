package org.example.dao;

import org.example.model.MotivoFalta;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;


public class MotivoFaltaDAO {

    // Metodo para inserir um motivo de falta no banco de dados
    public boolean inserirMotivoFalta(MotivoFalta motivoFalta){
        Conexao conexao = new Conexao();//Abertura da conexão segura
        String comandoInserir = "insert into motivo_falta (motivo) values (?)";//Comando SQL para inserir motivo
        Connection conn = conexao.conectar();//Conexão aberta com o banco
        int linhasAfetadas = 0;//Controle de linhas afetadas
        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){//PreparedStatement com retorno de chaves geradas
            pstmt.setString(1, motivoFalta.getMotivo());//Substitui o '?' pelo motivo
            linhasAfetadas = pstmt.executeUpdate();//Executa o comando e retorna quantidade de linhas afetadas

            if(linhasAfetadas > 0){//Verificação se inseriu corretamente
                try(ResultSet rs = pstmt.getGeneratedKeys()){//Obtém a chave primária gerada
                    if(rs.next()){
                        motivoFalta.setId(rs.getInt(1));//Atribui o id_motivo_falta ao objeto
                    }
                }
                return true;
            }
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            conexao.desconectar(conn);//Desconexão do banco
        }
    }

    // Metodo para buscar motivo de falta pelo ID
    public MotivoFalta listarMotivoFaltaPorID(int id){
        Conexao conexao = new Conexao();
        String comandoListar = "select * from motivo_falta where id_motivo_falta = ?";//Consulta por ID
        Connection conn = conexao.conectar();
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            pstmt.setInt(1,id);//Substitui o '?' pelo ID recebido
            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){
                MotivoFalta motivoFaltaTemporario = new MotivoFalta(
                        rs.getString("motivo")
                );
                motivoFaltaTemporario.setId(rs.getInt("id_motivo_falta"));//Seta o ID vindo do banco
                return motivoFaltaTemporario;
            }
            return null;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }finally{
            conexao.desconectar(conn);
        }
    }

    // Metodo para listar todos os motivos de falta
    public List<MotivoFalta> listarMotivosFalta(){
        Conexao conexao = new Conexao();
        String comandoListar = "select * from motivo_falta";//Consulta geral
        Connection conn = conexao.conectar();
        List<MotivoFalta> motivosFalta = new ArrayList<>();
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(comandoListar);
            while(rs.next()){
                MotivoFalta motivoFaltaTemporario = new MotivoFalta(
                        rs.getString("motivo")
                );
                motivoFaltaTemporario.setId(rs.getInt("id_motivo_falta"));//Seta o ID em cada objeto
                motivosFalta.add(motivoFaltaTemporario);
            }
            return motivosFalta;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return motivosFalta;
        }finally{
            conexao.desconectar(conn);
        }
    }

    // Metodo para alterar motivo a partir do ID
    public boolean alterarMotivoMotivoFalta(int id, String motivo){
        Conexao conexao = new Conexao();
        String comandoListar = "update motivo_falta set motivo = ? where id_motivo_falta = ?";//Update com base no ID
        Connection conn = conexao.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            pstmt.setString(1, motivo);//Novo motivo
            pstmt.setInt(2, id);//ID alvo da alteração
            linhasAfetadas = pstmt.executeUpdate();//Executa atualização
            return linhasAfetadas > 0;//Retorna true se alguma linha foi atualizada
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            conexao.desconectar(conn);
        }
    }

    // Metodo para deletar motivo de falta pelo ID
    public boolean deletarMotivoFalta(int id){
        Conexao conexao = new Conexao();
        String comandoDeletar = "delete from motivo_falta where id_motivo_falta = ?";//Delete com base no ID
        Connection conn = conexao.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){
            pstmt.setInt(1, id);//ID alvo do delete
            linhasAfetadas = pstmt.executeUpdate();//Executa comando
            return linhasAfetadas > 0;//Retorna true se houve exclusão
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;//Retorna que deu errado
        }finally{//Fechando conexao com dataBase
            conexao.desconectar(conn);
        }
    }
}
