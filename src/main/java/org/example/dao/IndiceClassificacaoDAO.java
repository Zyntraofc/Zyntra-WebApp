package org.example.dao;

///Classe criada com objetivo de armazenar os metodos responsáveis por ações na tabela "indice_classificacao" do banco de dados

//Importações
import org.example.conexao.ConexaoManager;
import org.example.model.IndiceClassificacao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

//Abertura da classe
public class IndiceClassificacaoDAO {

    // Metodo para inserir indice de classificacao no banco de dados
    public boolean inserirIndiceClassificacao(IndiceClassificacao indiceClassificacao) {

        //Comando de inserção em SQL
        String comandoInserir = "insert into indice_classificacao (recomendacao, preocupacao, porcentagem_minima, porcentagem_maxima) values (?,?,?,?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = ConexaoManager.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL, executará o comando e retornará chaves geradas pelo banco de dados
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {

            //Setando valores nos '?' do comando
            pstmt.setString(1, indiceClassificacao.getRecomendacao());
            pstmt.setString(2, indiceClassificacao.getPreocupacao());
            pstmt.setDouble(3, indiceClassificacao.getPorcentagemMinima());
            pstmt.setDouble(4, indiceClassificacao.getPorcentagemMaxima());

            //Executa o comando e armazena o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se ação foi bem sucedida
            if (linhasAfetadas > 0) {
                //Atributi chave gerada ao id do objeto
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    //Se houver alguma chave gerada atribui ela ao id
                    if (rs.next()) {
                        indiceClassificacao.setId(rs.getInt(1));
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

    // Metodo para buscar indice de classificacao pelo ID
    public IndiceClassificacao listarIndiceClassificacaoPorId(int id) {

        //Comando de listagem por ID no banco de dados
        String comandoListar = "select * from indice_classificacao where id_indice_classificacao = ?";

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

                //Define objeto IndiceClassificacao com valores do banco de dados
                IndiceClassificacao indiceClassificacaoTemporario = new IndiceClassificacao(
                        rs.getString("recomendacao"),
                        rs.getString("preocupacao"),
                        rs.getDouble("porcentagem_minima"),
                        rs.getDouble("porcentagem_maxima")
                );
                //Seta o ID vindo do banco
                indiceClassificacaoTemporario.setId(rs.getInt("id_indice_classificacao"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return indiceClassificacaoTemporario;
            }

            //Só commita consulta no banco de dados e retorna null
            ConexaoManager.commit();
            return null;

        } 
        //Em casos de erros no banco de dados desfaz ação
        catch (SQLException sqle) {

            //Lista todos os erros
            sqle.printStackTrace();

            //Retorna null
            return null;
        }
    }

    // Metodo para listar todos os indices de classificacao do banco de dados
    public List<IndiceClassificacao> listarIndicesClassificacao() {

        //Comando de listagem SQL
        String comandoListar = "select * from indice_classificacao order by 1";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de IndiceClassificacao
        List<IndiceClassificacao> indicesClassificacao = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (Statement stmt = conn.createStatement()) {

            //Executa consulta e armazena resultado em variável resultSet
            ResultSet rs = stmt.executeQuery(comandoListar);

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                IndiceClassificacao indiceClassificacaoTemporario = new IndiceClassificacao(
                        rs.getString("recomendacao"),
                        rs.getString("preocupacao"),
                        rs.getDouble("porcentagem_minima"),
                        rs.getDouble("porcentagem_maxima")
                );

                //Seta o id no objeto
                indiceClassificacaoTemporario.setId(rs.getInt("id_indice_classificacao"));

                //Adiciona a lista
                indicesClassificacao.add(indiceClassificacaoTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de indices de classificacao
            return indicesClassificacao;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Retorna lista de indices de classificacao (vazia)
            return indicesClassificacao;
        }
    }

    // Metodo para alterar recomendacao a partir do ID
    public boolean alterarRecomendacaoIndiceClassificacao(int id, String recomendacaoNova) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update indice_classificacao set recomendacao = ? where id_indice_classificacao = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta recomendacao no primeiro '?' do comando SQL
            pstmt.setString(1, recomendacaoNova);
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

    // Metodo para alterar preocupacao a partir do ID
    public boolean alterarPreocupacaoIndiceClassificacao(int id, String preocupacaoNova) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update indice_classificacao set preocupacao = ? where id_indice_classificacao = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta preocupacao no primeiro '?' do comando SQL
            pstmt.setString(1, preocupacaoNova);
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

    // Metodo para alterar porcentagem minima a partir do ID
    public boolean alterarPorcentagemMinimaIndiceClassificacao(int id, double porcentagemMinimaNova) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update indice_classificacao set porcentagem_minima = ? where id_indice_classificacao = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta porcentagem minima no primeiro '?' do comando SQL
            pstmt.setDouble(1, porcentagemMinimaNova);
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

    // Metodo para alterar porcentagem maxima a partir do ID
    public boolean alterarPorcentagemMaximaIndiceClassificacao(int id, double porcentagemMaximaNova) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update indice_classificacao set porcentagem_maxima = ? where id_indice_classificacao = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta porcentagem maxima no primeiro '?' do comando SQL
            pstmt.setDouble(1, porcentagemMaximaNova);
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

    // Metodo para deletar indice de classificacao pelo ID
    public boolean deletarIndiceClassificacao(int id) {

        //Comando de deleção SQL com base no id
        String comandoDeletar = "delete from indice_classificacao where id_indice_classificacao = ?";

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