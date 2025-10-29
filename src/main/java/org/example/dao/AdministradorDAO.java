package org.example.dao;

///Classe criada com objetivo de armazenar os metodos responsáveis por ações na tabela "administrador" do banco de dados

//Importações
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.utils.autenticacao.HashSenha;
import org.example.conexao.ConexaoManager;
import org.example.model.Administrador;

//Abertura da classe
public class AdministradorDAO {

    // Metodo para inserir administrador no banco de dados
    public boolean inserirAdministrador(Administrador administrador) {

        //Comando de inserção em SQL
        String comandoInserir = "INSERT INTO Administrador (email, hash_senha) VALUES (?, ?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = ConexaoManager.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL, executará o comando e retornará chaves geradas pelo banco de dados
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {

            //Setando valores nos '?' do comando
            pstmt.setString(1, administrador.getEmail());
            pstmt.setString(2, administrador.getHashSenha());

            //Executa o comando e armazena o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se ação foi bem sucedida
            if (linhasAfetadas > 0) {
                //Atributi chave gerada ao id do objeto
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    //Se houver alguma chave gerada atribui ela ao id
                    if (rs.next()) {
                        administrador.setId(rs.getInt(1));
                    }
                }

                //Commita ação e retorna true caso tenha dado certo
                ConexaoManager.commit();
                return true;
            }

            //Se não der certo desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;

        } 
        //Em casos de erro no banco de dados desfaz a ação
        catch (SQLException sqle) {

            //Lista os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna false
            return false;
        }
    }

    // Metodo para buscar administrador por e-mail
    public Administrador listarAdministradorPorEmail(String email) {

        //Comando de listagem por e-mail no banco de dados
        String comandoListar = "SELECT * FROM Administrador WHERE email = ?";

        //Conecta com o banco de dados
        Connection conn = ConexaoManager.conectar();

        //Iniciando executor que: Atribuirá valores ao comando sql e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            
            //Seta valores nos '?' do comando SQL
            pstmt.setString(1, email);

            //Executa a consulta e atribui o valor retornado a variável de resultSet
            ResultSet rs = pstmt.executeQuery();

            //Verifica se a consulta retornou algo
            if (rs.next()) {

                //Define objeto Administrador com valores do banco de dados
                Administrador administradorTemporario = new Administrador(
                        rs.getString("email"),
                        rs.getString("hash_senha")
                );
                //Seta o ID vindo do banco
                administradorTemporario.setId(rs.getInt("id_adm"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return administradorTemporario;
            }

            //Só commita consulta no banco de dados e retorna null
            ConexaoManager.commit();
            return null;

        } 
        //Em casos de erros no banco de dados desfaz ação
        catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna null
            return null;
        }
    }

    // Metodo para buscar administrador por ID
    public Administrador listarAdministradorPorId(int id) {

        //Comando de listagem por ID no banco de dados
        String comandoListar = "SELECT * FROM Administrador WHERE id_adm = ?";

        //Conecta com o banco de dados
        Connection conn = ConexaoManager.conectar();

        //Iniciando executor que: Atribuirá valores ao comando sql e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            
            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, id);

            //Executa a consulta e atribui o valor retornado a variável de resultSet
            ResultSet rs = pstmt.executeQuery();

            //Verifica se a consulta retornou algo
            if (rs.next()) {

                //Define objeto Administrador com valores do banco de dados
                Administrador administradorTemporario = new Administrador(
                        rs.getString("email"),
                        rs.getString("hash_senha")
                );
                //Seta o ID vindo do banco
                administradorTemporario.setId(rs.getInt("id_adm"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return administradorTemporario;
            }

            //Só commita consulta no banco de dados e retorna null
            ConexaoManager.commit();
            return null;

        } 
        //Em casos de erros no banco de dados desfaz ação
        catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna null
            return null;
        }
    }

    // Metodo para listar todos os administradores do banco de dados
    public List<Administrador> listarAdministradores() {

        //Comando de listagem SQL
        String comandoListar = "SELECT * FROM Administrador order by 1";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de Administrador
        List<Administrador> administradores = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar);
             ResultSet rs = pstmt.executeQuery()) {

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                Administrador administradorTemporario = new Administrador(
                        rs.getString("email"),
                        rs.getString("hash_senha")
                );

                //Seta o id no objeto
                administradorTemporario.setId(rs.getInt("id_adm"));

                //Adiciona a lista
                administradores.add(administradorTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de administradores
            return administradores;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna lista de administradores (vazia)
            return administradores;
        }
    }

    // Metodo para alterar e-mail a partir do ID
    public boolean alterarEmailAdministrador(int id, String emailNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "UPDATE Administrador SET email = ? WHERE id_adm = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta e-mail no primeiro '?' do comando SQL
            pstmt.setString(1, emailNovo);
            //Seta o id no segundo '?' do comando SQL
            pstmt.setInt(2, id);

            //Executa ação no banco de dados e guarda o total de linhas que foram afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;
        } 
        //Em casos de erros no banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();
            //Desfaz a ação
            ConexaoManager.rollback();
            //Retorna false
            return false;
        }
    }

    // Metodo para alterar senha a partir do ID
    public boolean alterarSenhaAdministrador(int id, String senhaNova) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "UPDATE Administrador SET hash_senha = ? WHERE id_adm = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta hash da senha no primeiro '?' do comando SQL
            pstmt.setString(1, new HashSenha(senhaNova).toString());
            //Seta o id no segundo '?' do comando SQL
            pstmt.setInt(2, id);

            //Executa ação no banco de dados e guarda o total de linhas que foram afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se ação tiver dados certo commita e retorna true
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }

            //Se não desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;
        } 
        //Em casos de erros no banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();
            //Desfaz a ação
            ConexaoManager.rollback();
            //Retorna false
            return false;
        }
    }

    // Metodo para deletar administrador pelo ID
    public boolean deletarAdministrador(int id) {

        //Comando de deleção SQL com base no id
        String comandoDeletar = "DELETE FROM Administrador WHERE id_adm = ?";

        //Abre conexão com banco de dados e armazena-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)) {

            //Seta Id no comando SQL
            pstmt.setInt(1, id);

            //Executa comando SQL e guarda o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Se a ação tiver sido concluida corretamente, commita ela e retorna true
            if (linhasAfetadas > 0) {
                ConexaoManager.commit();
                return true;
            }

            //Se a ação der errado desfaz a ação e retorna false
            ConexaoManager.rollback();
            return false;
        } 
        //Em casos de erro no banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();
        
            //Retorna false
            return false;
        }
    }
}