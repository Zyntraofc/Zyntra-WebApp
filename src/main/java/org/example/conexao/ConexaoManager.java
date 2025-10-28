package org.example.conexao;

/// Classe criada com objetivo de administrar a conexão
/// Possibilitando correções sequenciais de erros no banco de dados, evitando abrir e fechar conexão toda hora
/// Ela armazena só uma variável estática e todos os seus metodos são estáticos, utilizando a mesma conexão pra tudo

//Importações
import java.sql.Connection;
import java.sql.SQLException;
import org.example.exceptions.FailedCommitException;
import org.example.exceptions.FailedConnectionException;
import org.example.exceptions.RollbackException;

//Abertura da classe
public class ConexaoManager {

    //Variável estática para somente uma conexão ser usada pelo sistema inteiro
    private static Connection conn;

    //Metodo de conexão com o banco de dados
    public static Connection conectar() {

        try {
            //Verificando se a conexão já não foi estabelecida
            if (conn == null || conn.isClosed()) {

                Conexao conexao = new Conexao();
                //Criando a conexão pelo metodo getConnection de Conexão
                conn = conexao.getConnection();
                //Desativando o autoCommit da conexão
                conn.setAutoCommit(false);
            }

        }
        //Em caso de erros com o banco de dados ao conectar, lança exceção de falha ao conectar
        catch (SQLException sqle) {
            throw new FailedConnectionException("Erro ao obter a conexão: " + sqle.getMessage(), sqle);
        }

        //Retornando valor da conexão
        return conn;
    }


    //Metodo para commitar ação no banco de dados
    public static void commit() {
        try {
            //Verificação se a conexão está ativa
            if (conn != null && !conn.isClosed()) {

                //Commitando ação
                conn.commit();
            }
        }
        //Em caso de erros com o banco de dados, lança exceção de falha no commit
        catch (SQLException sqle) {
            throw new FailedCommitException("Erro ao comitar comando no banco: " + sqle.getMessage(), sqle);
        }
    }


    //Metodo para desfazer ação do banco de dados
    public static void rollback() {
        try {
            //Verificação se a conexão está ativa
            if (conn != null && !conn.isClosed()) {

                //Desfazendo ação anterior
                conn.rollback();
            }
        }
        //Em caso de erros com o banco de dados, lança exceção de falha ao fazer o rollback
        catch (SQLException sqle) {
            throw new RollbackException("Erro no rollback: " + sqle.getMessage(), sqle);
        }
    }


    //Metodo para fechar a conexão com o banco de dados
    public static void desconectar() {
        try {
            //Verificação se a conexão já está ativa
            if (conn != null && !conn.isClosed()) {

                //Fecha conexão
                conn.close();
            }
        }
        //Em caso de erros com o banco de dados, lista todos os erros
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        //Antes de finalizar o metodo, esvazia o valor do atributo de conexão da classe
        finally {
            conn = null;
        }
    }
}
