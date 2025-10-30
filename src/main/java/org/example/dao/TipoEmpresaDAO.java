package org.example.dao;

///Classe criada com objetivo de armazenar os metodos responsáveis por ações na tabela "tipo_empresa" do banco de dados

//Importações
import org.example.conexao.ConexaoManager;
import org.example.model.TipoEmpresa;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

//Abertura da classe
public class TipoEmpresaDAO {

    // Metodo para inserir tipo empresa no banco de dados
    public boolean inserirTipoEmpresa(TipoEmpresa tipoEmpresa) {

        //Comando de inserção em SQL
        String comandoInserir = "insert into tipo_empresa (nome, descricao) values (?,?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = ConexaoManager.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL, executará o comando e retornará chaves geradas pelo banco de dados
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {

            //Setando valores nos '?' do comando
            if (tipoEmpresa.getDescricao() != null) {
                pstmt.setString(2, tipoEmpresa.getDescricao());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }
            pstmt.setString(1, tipoEmpresa.getNome());

            //Executa o comando e armazena o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se ação foi bem sucedida
            if (linhasAfetadas > 0) {
                //Atributi chave gerada ao id do objeto
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    //Se houver alguma chave gerada atribui ela ao id
                    if (rs.next()) {
                        tipoEmpresa.setId(rs.getInt(1));
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

    // Metodo para buscar tipo empresa pelo ID
    public TipoEmpresa listarTipoEmpresaPorId(int id) {

        //Comando de listagem por ID no banco de dados
        String comandoListar = "select * from tipo_empresa where id_tipo_empresa = ?";

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

                //Define objeto TipoEmpresa com valores do banco de dados
                TipoEmpresa tipoEmpresaTemporario = new TipoEmpresa(
                        rs.getString("nome"),
                        rs.getString("status").charAt(0),
                        rs.getDate("ultima_atualizacao").toLocalDate(),
                        rs.getString("descricao")
                );
                //Seta o ID vindo do banco
                tipoEmpresaTemporario.setId(rs.getInt("id_tipo_empresa"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return tipoEmpresaTemporario;
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

    // Metodo para listar todos os tipos empresa do banco de dados
    public List<TipoEmpresa> listarTiposEmpresa() {

        //Comando de listagem SQL
        String comandoListar = "select * from tipo_empresa order by 1";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de TipoEmpresa
        List<TipoEmpresa> tiposEmpresa = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (Statement stmt = conn.createStatement()) {

            //Executa consulta e armazena resultado em variável resultSet
            ResultSet rs = stmt.executeQuery(comandoListar);

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                TipoEmpresa tipoEmpresaTemporario = new TipoEmpresa(
                        rs.getString("nome"),
                        rs.getString("status").charAt(0),
                        rs.getDate("ultima_atualizacao").toLocalDate(),
                        rs.getString("descricao")
                );

                //Seta o id no objeto
                tipoEmpresaTemporario.setId(rs.getInt("id_tipo_empresa"));

                //Adiciona a lista
                tiposEmpresa.add(tipoEmpresaTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de tipos empresa
            return tiposEmpresa;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna lista de tipos empresa (vazia)
            return new ArrayList<>();
        }
    }

    // Metodo para alterar nome a partir do ID
    public boolean alterarNomeTipoEmpresa(int id, String nome) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update tipo_empresa set nome = ? where id_tipo_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta nome no primeiro '?' do comando SQL
            pstmt.setString(1, nome);
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

    // Metodo para alterar status a partir do ID
    public boolean alterarStatusTipoEmpresa(int id, char status) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update tipo_empresa set status = ? where id_tipo_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta status no primeiro '?' do comando SQL
            pstmt.setString(1, String.valueOf(status));
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

    // Metodo para alterar ultima atualizacao a partir do ID
    public boolean alterarUltimaAtualizacaoTipoEmpresa(int id, LocalDate ultimaAtualizacao) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update tipo_empresa set ultima_atualizacao = ? where id_tipo_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta ultima atualizacao no primeiro '?' do comando SQL
            pstmt.setDate(1, Date.valueOf(ultimaAtualizacao));
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

    // Metodo para alterar descricao a partir do ID
    public boolean alterarDescricaoTipoEmpresa(int id, String descricao) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update tipo_empresa set descricao = ? where id_tipo_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta descricao no primeiro '?' do comando SQL
            pstmt.setString(1, descricao);
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

    // Metodo para deletar tipo empresa pelo ID
    public boolean deletarTipoEmpresa(int id) {

        //Comando de deleção SQL com base no id
        String comandoDeletar = "delete from tipo_empresa where id_tipo_empresa = ?";

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