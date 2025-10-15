package org.example.conexao;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

public class ConexaoManager{

    private static final ThreadLocal<Connection> threadConexao = new ThreadLocal<>();

    public static Connection conectar(){
        Connection conn = threadConexao.get();
        Conexao conexao = new Conexao();
        try{

            if(conn == null || conn.isClosed()){
                conn = conexao.getConnection();
                conn.setAutoCommit(false);
                threadConexao.set(conn);
            }
        }catch(SQLException sqle){
           throw new FailedConnectionException("Erro ao obter a conexão: " + sqle.getMessage(), sqle);
        }
        return conn;
    }



    public static void commitAndClose(){
        Connection conn = threadConexao.get();
        Conexao conexao = new Conexao();
        if(conn != null){
            try{
                conn.commit();
            }catch(SQLException sqle){
                throw new FailedCommitException("Erro ao comitar comando no banco: " + sqle.getMessage(), sqle);
            }finally{
                desconectar();
            }
        }
    }




    public static void rollbackAndClose(){
        Connection conn = threadConexao.get();
        Conexao conexao = new Conexao();
        if(conn != null){
            try{
                conn.rollback();
            }catch(SQLException sqle){
                throw new RollbackException("Erro no rollback: "+sqle.getMessage(), sqle);
            }finally{
                desconectar();
            }
        }
    }




    private static void desconectar(){
        Connection conn = threadConexao.get();
        Conexao conexao = new Conexao();
        try{
            if(conn != null && !conn.isClosed()){
                conexao.closeConnection(conn);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }finally{
            threadConexao.remove();
        }
    }

}