package org.example.conexao;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {

    private static Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            // tenta primeiro no .env, se não achar pega do ambiente do Render
            String url = dotenv.get("dbUrl", System.getenv("dbUrl"));
            String user = dotenv.get("dbUser", System.getenv("dbUser"));
            String password = dotenv.get("dbPassword", System.getenv("dbPassword"));

            return DriverManager.getConnection(url, user, password);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            return null;
        }
    }

    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
