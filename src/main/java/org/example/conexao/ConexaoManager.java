package org.example.conexao;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

public class ConexaoManager {

    private static Connection conn;

    public static Connection conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                Conexao conexao = new Conexao();
                conn = conexao.getConnection();
                conn.setAutoCommit(false);
            }
        } catch (SQLException sqle) {
            throw new FailedConnectionException("Erro ao obter a conexão: " + sqle.getMessage(), sqle);
        }
        return conn;
    }

    public static void commit() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.commit();
            }
        } catch (SQLException sqle) {
            throw new FailedCommitException("Erro ao comitar comando no banco: " + sqle.getMessage(), sqle);
        }
    }

    public static void rollback() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
        } catch (SQLException sqle) {
            throw new RollbackException("Erro no rollback: " + sqle.getMessage(), sqle);
        }
    }

    public static void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            conn = null;
        }
    }
}
