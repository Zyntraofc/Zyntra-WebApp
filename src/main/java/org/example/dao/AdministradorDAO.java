package org.example.dao;

//Importações

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.utils.autenticacao.HashSenha;
import org.example.conexao.ConexaoManager;
import org.example.model.Administrador;

public class AdministradorDAO {

    // Metodo para inserir administrador
    public boolean inserirAdministrador(Administrador administrador) {
        String comandoInserir = "INSERT INTO Administrador (email, hash_senha) VALUES (?, ?)";
        Connection conn = ConexaoManager.conectar();
        int linhasAfetadas = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, administrador.getEmail());
            pstmt.setString(2, administrador.getHashSenha());
            linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) administrador.setId(rs.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
            return false;
        }
    }

    // Metodo para buscar administrador por e-mail
    public Administrador listarAdministradorPorEmail(String email) {
        String comandoListar = "SELECT * FROM Administrador WHERE email = ?";
        Connection conn = ConexaoManager.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Administrador administrador = new Administrador(rs.getString("email"), rs.getString("hash_senha"));
                administrador.setId(rs.getInt("id_adm"));
                return administrador;
            }

            return null;

        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
            return null;
        }
    }

    // Metodo para buscar administrador por ID
    public Administrador listarAdministradorPorId(int id) {
        String comandoListar = "SELECT * FROM Administrador WHERE id_adm = ?";
        Connection conn = ConexaoManager.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Administrador administrador = new Administrador(rs.getString("email"), rs.getString("hash_senha"));
                administrador.setId(rs.getInt("id_adm"));
                return administrador;
            }

            return null;

        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
            return null;
        }
    }

    // Metodo para listar todos os administradores
    public List<Administrador> listarAdministradores() {
        List<Administrador> administradores = new ArrayList<>();
        String comandoListar = "SELECT * FROM Administrador order by 1";
        Connection conn = ConexaoManager.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Administrador adm = new Administrador(rs.getString("email"), rs.getString("hash_senha"));
                adm.setId(rs.getInt("id_adm"));
                administradores.add(adm);
            }

        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
        } finally {
            ConexaoManager.commit();
        }

        return administradores;
    }

    // Metodo para alterar e-mail
    public boolean alterarEmailAdministrador(int id, String emailNovo) {
        String comandoAtualizar = "UPDATE Administrador SET email = ? WHERE id_adm = ?";
        Connection conn = ConexaoManager.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {
            pstmt.setString(1, emailNovo);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
            return false;
        } finally {
            ConexaoManager.commit();
        }
    }

    // Mdtodo para alterar senha
    public boolean alterarSenhaAdministrador(int id, String senhaNova) {
        String comandoAtualizar = "UPDATE Administrador SET hash_senha = ? WHERE id_adm = ?";
        Connection conn = ConexaoManager.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {
            pstmt.setString(1, new HashSenha(senhaNova).toString());
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
            return false;
        } finally {
            ConexaoManager.commit();
        }
    }

    // Metodo para deletar administrador
    public boolean deletarAdministrador(int id) {
        String comandoDeletar = "DELETE FROM Administrador WHERE id_adm = ?";
        Connection conn = ConexaoManager.conectar();

        try (PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();
            return false;
        } finally {
            ConexaoManager.commit();
        }
    }
}