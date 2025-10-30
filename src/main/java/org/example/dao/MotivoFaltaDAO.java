package org.example.dao;

///Classe criada com objetivo de armazenar os metodos responsáveis por ações na tabela "empresa" do banco de dados

//Importações
import org.example.conexao.ConexaoManager;
import org.example.model.MotivoFalta;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

//Abertura da classe
public class MotivoFaltaDAO {

    // Metodo para inserir um motivo de falta no banco de dados
    public boolean inserirMotivoFalta(MotivoFalta motivoFalta) {

        //Comando de inserção em SQL
        String comandoInserir = "insert into motivo_falta (motivo) values (?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = ConexaoManager.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL, executará o comando e retornará chaves geradas pelo banco de dados
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {

            //Setando valores nos '?' do comando
            pstmt.setString(1, motivoFalta.getMotivo());

            //Executa o comando e armazena o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se ação foi bem sucedida
            if (linhasAfetadas > 0) {
                //Atributi chave gerada ao id do objeto
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    //Se houver alguma chave gerada atribui ela ao id
                    if (rs.next()) {
                        motivoFalta.setId(rs.getInt(1));
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


    // Metodo para buscar motivo de falta pelo ID
    public MotivoFalta listarMotivoFaltaPorID(int id) {

        //Comando de listagem por ID no banco de dados
        String comandoListar = "select * from motivo_falta where id_motivo_falta = ?";

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

                //Define objeto MotivoFalta com valores do banco de dados
                MotivoFalta motivoFaltaTemporario = new MotivoFalta(
                        rs.getString("motivo")
                );
                //Seta o ID vindo do banco
                motivoFaltaTemporario.setId(rs.getInt("id_motivo_falta"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return motivoFaltaTemporario;
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


    // Metodo para listar todos os motivos de falta do banco de dados
    public List<MotivoFalta> listarMotivosFalta() {

        //Comando de listagem SQL
        String comandoListar = "select * from motivo_falta order by 1";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de MotivoFalta
        List<MotivoFalta> motivosFalta = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (Statement stmt = conn.createStatement()) {

            //Executa consulta e armazena resultado em variável resultSet
            ResultSet rs = stmt.executeQuery(comandoListar);

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                MotivoFalta motivoFaltaTemporario = new MotivoFalta(
                        rs.getString("motivo")
                );

                //Seta o id no objeto
                motivoFaltaTemporario.setId(rs.getInt("id_motivo_falta"));

                //Adiciona a lista
                motivosFalta.add(motivoFaltaTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de motivos de falta
            return motivosFalta;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna lista de motivos de falta (vazia)
            return motivosFalta;
        }
    }

    // Metodo para alterar motivo a partir do ID
    public boolean alterarMotivoMotivoFalta(int id, String motivo) {

        //Comando de atualização SQL a partir do ID
        String comandoListar = "update motivo_falta set motivo = ? where id_motivo_falta = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {

            //Seta motivo falta no primeiro '?' do comando SQL
            pstmt.setString(1, motivo);
            //Seta o id no primeiro '?' do comando SQL
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


    // Metodo para deletar motivo de falta pelo ID
    public boolean deletarMotivoFalta(int id) {

        //Comando de deleção SQL com base no id
        String comandoDeletar = "delete from motivo_falta where id_motivo_falta = ?";

        //Abre conexão com banco de dados e armazena-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

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