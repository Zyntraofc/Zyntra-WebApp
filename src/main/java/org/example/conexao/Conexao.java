package org.example.conexao;

/// Classe criada com objetivo da criação e destruição da conexão com o banco de dados

//Importações
import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

//Abertura da classe
public class Conexao {

    //Extraindo dados de conexão de variáveis de ambiente do .env e colocando
    private static Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    //Metodo para estabelecer conexão com o banco de dados
    public Connection getConnection() {
        try {

            //Carregando a classe do driver postgreSQL
            Class.forName("org.postgresql.Driver");

            //Obtendo valores das variáveis de ambiente do .env em resourses, caso não ache pega do render
            String url = dotenv.get("dbUrl", System.getenv("dbUrl"));
            String user = dotenv.get("dbUser", System.getenv("dbUser"));
            String password = dotenv.get("dbPassword", System.getenv("dbPassword"));

            //Retornando a criação da conexão
            return DriverManager.getConnection(url, user, password);

        }
        //Em caso de erros no banco de dados, lista todos os erros e retorna null
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        //Caso não ache a classe instruida ao driver, lista todos os erros e retorna null
        catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            return null;
        }
    }



    //Metodo para fechar a conexão com o banco de dados
    public void closeConnection(Connection con) {

        //Verificando se a conexão está aberta
        if (con != null) {
            try {
                //Fecha a conexão
                con.close();
            }
            //Em casos de erros no banco de dados, lista todos os erros
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }

        }

    }

}
