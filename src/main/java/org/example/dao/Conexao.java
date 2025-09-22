package org.example.dao;
//Importacoes
import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

//Abertura da classe
public class Conexao {
    //Carregando dotEnv em um objeto estatico
    private static Dotenv dotenv = Dotenv.configure()
            //Passando caminho do .env
            .directory("C:\\Users\\erickbarbosa-ieg\\OneDrive - Instituto J&F\\Tech\\Interdisciplinar\\Zyntra-WebApp") // ajuste para sua máquina
            .load();

    //Metodo para conectar e manter conexao com o database
    public Connection conectar() {
        try {
            // Carregar driver do MySQL
            Class.forName("org.postgresql.Driver");

//             Pegar variáveis do .env
            String url = dotenv.get("dbUrl");
            String user = dotenv.get("dbUser");
            String password = dotenv.get("dbPassword");


            // Abrir conexão
            Connection conn = DriverManager.getConnection(url, user, password);
            //Retorno da conexao
            return conn;

        } catch (SQLException sqle) {//Tratamento de excessoes do SQL
            sqle.printStackTrace();//Printa pilha de erros
            return null;//Retorno nulo
        }
        catch (ClassNotFoundException cnfe){//Tratamento de excessao caso a classe não seja acessada pelo postgresSQL
            cnfe.printStackTrace();//Printa pilha de erros
            return null;//Retorno nulo
        }
    }

    //Metodo para fechar a conexao com o banco de dados
    public void desconectar(Connection con) {//Recebimento do atributo Connection que será fechado
        if (con != null) {//Verificacao se a conexao não está fechada já
            try {
                con.close();//Fechament da conexao
            } catch (SQLException e) {//Tratamento de excessões SQL
                e.printStackTrace();//Printa pilha de erros
            }
        }
    }
}