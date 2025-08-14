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
    public Connection conn;//Atributo de conexão
    public PreparedStatement pstmt;//Atributo de comando
    public ResultSet rs;//Atributo de resultados do banco de dados

    //Método para abrir conexão com o banco de dados
    public boolean Conectar() {//Abrindo método de conexão
        if (this.conn == null) {//Verificação de conexão aberta?
            try{//Tratamento de excessões
                this.conn = DriverManager.getConnection(dotenv.get("dbUrl"), dotenv.get("dbUser"), dotenv.get("dbPassword"));//Ativa driver JDBC e abre a conexão
                return true;//Retorna que a conexao foi bem sucedida
            }
            catch (SQLException sqle){//SQL exception
                sqle.printStackTrace();//Mensagem de erro na conexão
                return false;//Retorna erro na conexão
            }
        }
        else {
            return false;//Retorna erro na conexão
        }
    }

    //Método para fechar a conexão
    public boolean Desconectar() {//Abrindo o método
        if (this.conn != null) {//Verificação de se a conexão já está aberta
            try {//Tratamento de excessões
                this.conn.close();//Fechamento da conexão
                return true;//Retorna desconexão bem-sucedida
            } catch (SQLException e) {//Tratamento de SQL exception
                e.printStackTrace();//Mensagem de erro
                return false;//Retorna erro ao desconectar
            }
        } else {
            return false;//Retorna erro ao desconectar, se a conexão já estiver fechada
        }
    }


}
