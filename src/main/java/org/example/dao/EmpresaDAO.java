package org.example.dao;

///Classe criada com objetivo de armazenar os metodos responsáveis por ações na tabela "empresa" do banco de dados

//Importações
import org.example.conexao.ConexaoManager;
import org.example.exceptions.InvalidForeignKeyException;
import org.example.model.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.example.utils.regex.ValidacaoCnpj;
import org.example.utils.regex.ValidacaoTelefone;

//Abertura da classe
public class EmpresaDAO {

    // Metodo para inserir empresa no banco de dados
    public boolean inserirEmpresa(Empresa empresa) {

        //Comando de inserção em SQL
        String comandoInserir = "insert into empresa (id_tipo_empresa, id_indice_classificacao, id_status_aprovacao, nome, cnpj, email, telefone) values (?,?,?,?,?,?,?)";

        //Abrindo e armazenando conexão com o banco
        Connection conn = ConexaoManager.conectar();

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: adicionará valores no comando SQL, executará o comando e retornará chaves geradas pelo banco de dados
        try (PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)) {

            //Formata valores com várias entradas
            String cnpj = ValidacaoCnpj.formatarCnpj(empresa.getCnpj());
            String telefone = ValidacaoTelefone.formatarTelefone(empresa.getTelefone());


            //Setando valores nos '?' do comando
            pstmt.setInt(1, empresa.getIdTipoEmpresa());
            pstmt.setInt(2, empresa.getIdIndiceClassificacao());
            pstmt.setInt(3, empresa.getIdStatusAprovacao());
            pstmt.setString(4, empresa.getNome());
            pstmt.setString(5, cnpj);
            pstmt.setString(6, empresa.getEmail());
            pstmt.setString(7, telefone);

            //Executa o comando e armazena o total de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se ação foi bem sucedida
            if (linhasAfetadas > 0) {
                //Atributi chave gerada ao id do objeto
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    //Se houver alguma chave gerada atribui ela ao id
                    if (rs.next()) {
                        empresa.setId(rs.getInt(1));
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

            //Obtém código da causa do erro
            String sqleState = sqle.getSQLState();

            //Se for erro de foreignkey inváida, lança exceção de foreign key invalido
            if(sqleState.startsWith("23")){
                throw new InvalidForeignKeyException("Valores inseridos inexistentes na tabela tipoEmpresa");
            }

            //Lista os erros
            sqle.printStackTrace();

            //Desfaz a ação
            ConexaoManager.rollback();

            //Retorna false
            return false;
        }
    }

    // Metodo para buscar empresa pelo ID
    public Empresa listarEmpresaPorId(int id) {

        //Comando de listagem por ID no banco de dados
        String comandoListar = "select * from empresa where id_empresa = ?";

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

                //Define objeto Empresa com valores do banco de dados
                Empresa empresaTemporario = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                //Seta o ID vindo do banco
                empresaTemporario.setId(rs.getInt("id_empresa"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return empresaTemporario;
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

    // Metodo para buscar empresa por id status aprovacao
    public Empresa listarEmpresaPorIdStatusAprovacao(int idStatusAprovacao) {

        //Comando de listagem por id status aprovacao no banco de dados
        String comandoListar = "select * from empresa where id_status_aprovacao = ?";

        //Conecta com o banco de dados
        Connection conn = ConexaoManager.conectar();

        //Iniciando executor que: Atribuirá valores ao comando sql e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            
            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, idStatusAprovacao);

            //Executa a consulta e atribui o valor retornado a variável de resultSet
            ResultSet rs = pstmt.executeQuery();

            //Verifica se a consulta retornou algo
            if (rs.next()) {

                //Define objeto Empresa com valores do banco de dados
                Empresa empresaTemporario = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                //Seta o ID vindo do banco
                empresaTemporario.setId(rs.getInt("id_empresa"));

                //Commita a ação no banco de dados e retorna o objeto gerado
                ConexaoManager.commit();
                return empresaTemporario;
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

    // Metodo para listar empresas por id tipo empresa
    public List<Empresa> listarEmpresaPorIdTipoEmpresa(int idTipoEmpresa) {

        //Comando de listagem por id tipo empresa no banco de dados
        String comandoListar = "select * from empresa where id_tipo_empresa = ?";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de Empresa
        List<Empresa> empresas = new ArrayList<>();

        //Iniciando executor que: Atribuirá valores ao comando sql e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoListar)) {
            
            //Seta valores nos '?' do comando SQL
            pstmt.setInt(1, idTipoEmpresa);

            //Executa a consulta e atribui o valor retornado a variável de resultSet
            ResultSet rs = pstmt.executeQuery();

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                Empresa empresaTemporario = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );

                //Seta o id no objeto
                empresaTemporario.setId(rs.getInt("id_empresa"));

                //Adiciona a lista
                empresas.add(empresaTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de empresas
            return empresas;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Retorna lista de empresas (vazia)
            return empresas;
        }
    }

    // Metodo para listar todas as empresas do banco de dados
    public List<Empresa> listarEmpresas() {

        //Comando de listagem SQL
        String comandoListar = "select * from empresa order by 1";

        //Abrindo conexão com o banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar();

        //Lista para armazenar registros de Empresa
        List<Empresa> empresas = new ArrayList<>();

        //Cria executor que receberá comando e executará ele
        try (Statement stmt = conn.createStatement()) {

            //Executa consulta e armazena resultado em variável resultSet
            ResultSet rs = stmt.executeQuery(comandoListar);

            //Enquanto tiver mais registros cria novos objetos e adiciona à lista
            while (rs.next()) {

                //Cria objeto com valores do registro atual
                Empresa empresaTemporario = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );

                //Seta o id no objeto
                empresaTemporario.setId(rs.getInt("id_empresa"));

                //Adiciona a lista
                empresas.add(empresaTemporario);
            }

            //Commita a ação
            ConexaoManager.commit();

            //Retorna a lista de empresas
            return empresas;

        } 
        //Em casos de erros com o banco de dados desfaz a ação
        catch (SQLException sqle) {
            //Lista todos os erros
            sqle.printStackTrace();

            //Retorna lista de empresas (vazia)
            return empresas;
        }
    }

    // Metodo para alterar id tipo empresa a partir do ID
    public boolean alterarIdTipoEmpresaEmpresa(int id, int idTipoEmpresaNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set id_tipo_empresa = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta id tipo empresa no primeiro '?' do comando SQL
            pstmt.setInt(1, idTipoEmpresaNovo);
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

            //Obtém código da causa do erro
            String sqleState = sqle.getSQLState();

            //Se for erro de foreignkey inváida, lança exceção de foreign key invalido
            if(sqleState.startsWith("23")){
                throw new InvalidForeignKeyException("Valores inseridos inexistentes na tabela tipoEmpresa");
            }
            //Lista todos os erros
            sqle.printStackTrace();
            //Desfaz a ação
            ConexaoManager.rollback();
            //Retorna false
            return false;
        }
    }

    // Metodo para alterar id indice classificacao a partir do ID
    public boolean alterarIdIndiceClassificacaoEmpresa(int id, int idIndiceClassificacaoNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set id_indice_classificacao = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta id indice classificacao no primeiro '?' do comando SQL
            pstmt.setInt(1, idIndiceClassificacaoNovo);
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

            //Obtém código da causa do erro
            String sqleState = sqle.getSQLState();

            //Se for erro de foreignkey inváida, lança exceção de foreign key invalido
            if(sqleState.startsWith("23")){
                throw new InvalidForeignKeyException("Valores inseridos inexistentes na tabela indice de classificação");
            }
            //Lista todos os erros
            sqle.printStackTrace();
            //Desfaz a ação
            ConexaoManager.rollback();
            //Retorna false
            return false;
        }
    }

    // Metodo para alterar id status aprovacao a partir do ID
    public boolean alterarIdStatusAprovacaoEmpresa(int id, int idStatusAprovacaoNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set id_status_aprovacao = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta id status aprovacao no primeiro '?' do comando SQL
            pstmt.setInt(1, idStatusAprovacaoNovo);
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

    // Metodo para alterar nome a partir do ID
    public boolean alterarNomeEmpresa(int id, String nomeNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set nome = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta nome no primeiro '?' do comando SQL
            pstmt.setString(1, nomeNovo);
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

    // Metodo para alterar cnpj a partir do ID
    public boolean alterarCnpjEmpresa(int id, String cnpjNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set cnpj = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta cnpj no primeiro '?' do comando SQL
            pstmt.setString(1, cnpjNovo);
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

    // Metodo para alterar email a partir do ID
    public boolean alterarEmailEmpresa(int id, String emailNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set email = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta email no primeiro '?' do comando SQL
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

    // Metodo para alterar telefone a partir do ID
    public boolean alterarTelefoneEmpresa(int id, String telefoneNovo) {

        //Comando de atualização SQL a partir do ID
        String comandoAtualizar = "update empresa set telefone = ? where id_empresa = ?";

        //Abrindo conexão com banco de dados e armazenando-a
        Connection conn = ConexaoManager.conectar(); 

        //Controle de linhas afetadas
        int linhasAfetadas = 0;

        //Iniciando executor que: Atribuirá valores ao comando SQL e executará ele
        try (PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)) {

            //Seta telefone no primeiro '?' do comando SQL
            pstmt.setString(1, telefoneNovo);
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

    // Metodo para deletar empresa pelo ID
    public boolean deletarEmpresa(int id) {

        //Comando de deleção SQL com base no id
        String comandoDeletar = "delete from empresa where id_empresa = ?";

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