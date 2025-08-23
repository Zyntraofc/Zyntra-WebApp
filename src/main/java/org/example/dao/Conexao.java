package org.example.dao;

//---Importações---//
//Classes do package SQL
import java.sql.*;

//Leitor do .env
import io.github.cdimascio.dotenv.Dotenv;


public class Conexao {//Abrindo classe de conexão

    //Atributo privato para carregar o .env
    private static final Dotenv dotenv = Dotenv.load();

    //Atributos a serem usados em outras classes
    public static Connection conn;//Atributo de conexão


    //Metodo para abrir conexão com o banco de dados
    public static Connection Conectar() {//Abrindo método de conexão
        if (conn == null) {//Verificação de conexão aberta?
            try{//Tratamento de excessões
                conn = DriverManager.getConnection(dotenv.get("dbUrl"), dotenv.get("dbUser"), dotenv.get("dbPassword"));//Ativa driver JDBC e abre a conexão
                return conn;//Retorna a conexão
            }
            catch (SQLException sqle){//SQL exception
                sqle.printStackTrace();//Mensagem de erro na conexão
            }
        }
        return null;
    }



    //Metodo para fechar a conexão
    public static boolean Desconectar() {//Abrindo o metodo
        if (conn != null) {//Verificação de se a conexão já está aberta
            try {//Tratamento de excessões
                conn.close();//Fechamento da conexão
                return true;//Retorna desconexão bem-sucedida
            } catch (SQLException e) {//Tratamento de SQL exception
                e.printStackTrace();//Mensagem de erro
                return false;//Retorna erro ao desconectar
            }
        } else {
            return false;//Retorna erro ao desconectar, se a conexão já estiver fechada
        }
    }

    //Métodos getters
    public Connection getConnection(){
        return conn;
    }



}
