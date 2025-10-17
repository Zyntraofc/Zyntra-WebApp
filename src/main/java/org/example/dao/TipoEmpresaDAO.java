package org.example.dao;

import org.example.conexao.ConexaoManager;
import org.example.model.TipoEmpresa;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class TipoEmpresaDAO {

    // Metodo para inserir um tipo de empresa no banco de dados
    public boolean inserirTipoEmpresa(TipoEmpresa tipoEmpresa){
        String comandoInserir = "insert into tipo_empresa (nome, descricao) values (?,?)";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;

        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){
            if(tipoEmpresa.getDescricao() != null){
                pstmt.setString(2, tipoEmpresa.getDescricao());
            } else {
                pstmt.setNull(2, Types.VARCHAR);//Define como NULL no banco
            }
            pstmt.setString(1,tipoEmpresa.getNome());


            linhasAfetadas = pstmt.executeUpdate();

            if(linhasAfetadas > 0){
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if(rs.next()){
                        tipoEmpresa.setId(rs.getInt(1));
                    }
                }
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para listar tipo de empresa pelo ID
    public TipoEmpresa listarTipoEmpresaPorId(int id){
        String comandoListar = "select * from tipo_empresa where id_tipo_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                TipoEmpresa tipoEmpresa = new TipoEmpresa(
                        rs.getString("nome"),
                        rs.getString("status").charAt(0),
                        rs.getDate("ultima_atualizacao").toLocalDate(),
                        rs.getString("descricao")
                );
                tipoEmpresa.setId(rs.getInt("id_tipo_empresa"));
                ConexaoManager.commit();
                return tipoEmpresa;
            }
            ConexaoManager.commit();
            return null;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return null;
        }
    }

    // Metodo para listar todos os tipos de empresa
    public List<TipoEmpresa> listarTiposEmpresa(){
        String comandoListar = "select * from tipo_empresa";
        Connection conn = ConexaoManager.conectar();
        List<TipoEmpresa> tiposEmpresa = new ArrayList<>();
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(comandoListar);
            while (rs.next()) {
                TipoEmpresa tipoEmpresaTemporario = new TipoEmpresa(
                        rs.getString("nome"),
                        rs.getString("status").charAt(0),
                        rs.getDate("ultima_atualizacao").toLocalDate(),
                        rs.getString("descricao")
                );
                tipoEmpresaTemporario.setId(rs.getInt("id_tipo_empresa"));
                tiposEmpresa.add(tipoEmpresaTemporario);
            }
            ConexaoManager.commit();
            return tiposEmpresa;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return new ArrayList<>(); // <- retorna lista vazia em vez de null
        }
    }

    // Metodo para alterar nome do tipo de empresa
    public boolean alterarNomeTipoEmpresa(int id, String nome){
        String comandoAtualizar = "update tipo_empresa set nome = ? where id_tipo_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, nome);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para alterar status do tipo de empresa
    public boolean alterarStatusTipoEmpresa(int id, char status){
        String comandoAtualizar = "update tipo_empresa set status = ? where id_tipo_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, String.valueOf(status));
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para alterar data de última atualização
    public boolean alterarUltimaAtualizacaoTipoEmpresa(int id, LocalDate ultimaAtualizacao){
        String comandoAtualizar = "update tipo_empresa set ultima_atualizacao = ? where id_tipo_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setDate(1, Date.valueOf(ultimaAtualizacao));
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para alterar descrição do tipo de empresa
    public boolean alterarDescricaoTipoEmpresa(int id, String descricao){
        String comandoAtualizar = "update tipo_empresa set descricao = ? where id_tipo_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, descricao);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }

    // Metodo para deletar tipo de empresa pelo ID
    public boolean deletarTipoEmpresa(int id){
        String comandoDeletar = "delete from tipo_empresa where id_tipo_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){
            pstmt.setInt(1, id);
            linhasAfetadas = pstmt.executeUpdate();
            if(linhasAfetadas > 0){
                ConexaoManager.commit();
                return true;
            }
            ConexaoManager.rollback();
            return false;
        }catch(SQLException sqle){
            sqle.printStackTrace();
            ConexaoManager.rollback();
            return false;
        }
    }
}