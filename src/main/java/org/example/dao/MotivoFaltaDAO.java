package org.example.dao;

import org.example.conexao.ConexaoManager;
import org.example.model.MotivoFalta;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;


public class MotivoFaltaDAO {

    // Metodo para inserir um motivo de falta no banco de dados
    public boolean inserirMotivoFalta(MotivoFalta motivoFalta) {
        String comandoInserir = "insert into motivo_falta (motivo) values (?)";//Comando SQL para inserir motivo
        Connection conn = ConexaoManager.conectar();//Conexão aberta com o banco
        int linhasAfetadas = 0;//Controle de linhas afetadas
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {//PreparedStatement com retorno de chaves geradas
            pstmt.setString(1, motivoFalta.getMotivo());//Substitui o '?' pelo motivo
            linhasAfetadas = pstmt.executeUpdate();//Executa o comando e retorna quantidade de linhas afetadas

            if (linhasAfetadas > 0) {//Verificação se inseriu corretamente
                try (ResultSet rs = pstmt.getGeneratedKeys()) {//Obtém a chave primária gerada
                    if (rs.next()) {
                        motivoFalta.setId(rs.getInt(1));//Atribui o id_motivo_falta ao objeto
                    }
                }
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para buscar motivo de falta pelo ID
    public MotivoFalta listarMotivoFaltaPorID(int id) {
        String comandoListar = "select * from motivo_falta where id_motivo_falta = ?";//Consulta por ID
        Connection conn = ConexaoManager.conectar(); // CORREÇÃO: Uso direto do método estático
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            pstmt.setInt(1, id);//Substitui o '?' pelo ID recebido
            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if (rs.next()) {
                MotivoFalta motivoFaltaTemporario = new MotivoFalta(
                        rs.getString("motivo")
                );
                motivoFaltaTemporario.setId(rs.getInt("id_motivo_falta"));//Seta o ID vindo do banco
                ConexaoManager.commit();
                return motivoFaltaTemporario;
            }
            ConexaoManager.commit();
            return null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return null;
        }
    }

    // Metodo para listar todos os motivos de falta
    public List<MotivoFalta> listarMotivosFalta() {
        String comandoListar = "select * from motivo_falta order by 1";//Consulta geral
        Connection conn = ConexaoManager.conectar(); // CORREÇÃO: Uso direto do método estático
        List<MotivoFalta> motivosFalta = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(comandoListar);
            while (rs.next()) {
                MotivoFalta motivoFaltaTemporario = new MotivoFalta(
                        rs.getString("motivo")
                );
                motivoFaltaTemporario.setId(rs.getInt("id_motivo_falta"));//Seta o ID em cada objeto
                motivosFalta.add(motivoFaltaTemporario);
            }
            ConexaoManager.commit();
            return motivosFalta;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return motivosFalta;
        }
    }

    // Metodo para alterar motivo a partir do ID
    public boolean alterarMotivoMotivoFalta(int id, String motivo) {
        String comandoListar = "update motivo_falta set motivo = ? where id_motivo_falta = ?";//Update com base no ID
        Connection conn = ConexaoManager.conectar(); // CORREÇÃO: Uso direto do método estático
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            pstmt.setString(1, motivo);//Novo motivo
            pstmt.setInt(2, id);//ID alvo da alteração
            linhasAfetadas = pstmt.executeUpdate();//Executa atualização
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para deletar motivo de falta pelo ID
    public boolean deletarMotivoFalta(int id) {
        String comandoDeletar = "delete from motivo_falta where id_motivo_falta = ?";//Delete com base no ID
        Connection conn = ConexaoManager.conectar(); // CORREÇÃO: Uso direto do método estático
        int linhasAfetadas = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)) {
            pstmt.setInt(1, id);//ID alvo do delete
            linhasAfetadas = pstmt.executeUpdate();//Executa comando
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }
}