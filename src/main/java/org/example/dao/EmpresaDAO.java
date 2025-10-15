package org.example.dao;

//Importações
import org.example.conexao.ConexaoManager;
import org.example.model.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class EmpresaDAO {

    public boolean inserirEmpresa(Empresa empresa){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para inserir uma nova empresa na tabela
        String comandoInserir = "insert into empresa (id_tipo_empresa, id_indice_classificacao, id_status_aprovacao, nome, cnpj, email, telefone) values (?,?,?,?,?,?,?)";
        //Variável para controlar o número de linhas afetadas pela operação
        int linhasAfetadas = 0;
        //Prepara o statement SQL com capacidade de retornar chaves geradas automaticamente
        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){
            //Atribui o valor do ID do tipo de empresa aos parametros
            pstmt.setInt(1, empresa.getIdTipoEmpresa());
            pstmt.setInt(2, empresa.getIdIndiceClassificacao());
            pstmt.setInt(3, empresa.getIdStatusAprovacao());
            pstmt.setString(4, empresa.getNome());
            pstmt.setString(5, empresa.getCnpj());
            pstmt.setString(6, empresa.getEmail());
            pstmt.setString(7, empresa.getTelefone());

            //Executa a atualização no banco e obtém o número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Verifica se a inserção foi bem-sucedida
            if(linhasAfetadas > 0){
                //Obtém as chaves geradas automaticamente pelo banco
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    //Se houver uma chave gerada, define o ID da empresa
                    if(rs.next()){
                        //Define o ID gerado no objeto empresa
                        empresa.setId(rs.getInt(1));
                    }
                }
                ConexaoManager.commitAndClose();
                //Retorna verdadeiro indicando sucesso na inserção
                return true;
            }
            ConexaoManager.rollbackAndClose();
            //Retorna falso indicando falha na inserção
            return false;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso indicando falha na operação
            return false;
        }
    }

    public Empresa listarEmpresaPorId(int id){
        //Comando SQL para buscar empresa por ID
        String comandoListar = "select * from empresa where id_empresa = ?";
        Connection conn = ConexaoManager.conectar();
        //Prepara o statement SQL para consulta
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            //Atribui o ID ao parâmetro da consulta
            pstmt.setInt(1, id);

            //Executa a consulta e obtém o resultado
            ResultSet rs = pstmt.executeQuery();
            //Verifica se há resultados na consulta
            if(rs.next()){
                //Cria novo objeto Empresa com dados do banco
                Empresa empresa = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                //Define o ID da empresa a partir do banco de dados
                empresa.setId(rs.getInt("id_empresa"));
                ConexaoManager.commitAndClose();
                //Retorna a empresa encontrada
                return empresa;
            }
            ConexaoManager.commitAndClose();
            //Retorna null se nenhuma empresa for encontrada
            return null;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna null em caso de erro
            return null;
        }
    }

    //Metodo de listagem de empresas por id de status aprovação
    public Empresa listarEmpresaPorIdStatusAprovacao(int idStatusAprovacao){
        //Comando SQL para buscar empresa por status de aprovação
        String comandoListar = "select * from empresa where id_status_aprovacao = ?";
        Connection conn = ConexaoManager.conectar();
        //Prepara o statement SQL para consulta
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            //Atribui o status de aprovação ao parâmetro da consulta
            pstmt.setInt(1, idStatusAprovacao);

            //Executa a consulta e obtém o resultado
            ResultSet rs = pstmt.executeQuery();
            //Verifica se há resultados na consulta
            if(rs.next()){
                //Cria novo objeto Empresa com dados do banco
                Empresa empresa = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                //Define o ID da empresa a partir do banco de dados
                empresa.setId(rs.getInt("id_empresa"));
                ConexaoManager.commitAndClose();
                //Retorna a empresa encontrada
                return empresa;
            }
            ConexaoManager.commitAndClose();
            //Retorna null se nenhuma empresa for encontrada
            return null;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna null em caso de erro
            return null;
        }
    }

    public List<Empresa> listarEmpresaPorIdTipoEmpresa(int idTipoEmpresa){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para buscar empresas por tipo de empresa
        String comandoListar = "select * from empresa where id_tipo_empresa = ?";
        //Cria lista vazia para armazenar empresas
        List<Empresa> empresas = new ArrayList<>();
        //Prepara o statement SQL para consulta
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            //Atribui o tipo de empresa ao parâmetro da consulta
            pstmt.setInt(1, idTipoEmpresa);
            //Executa a consulta e obtém o resultado
            ResultSet rs = pstmt.executeQuery();
            //Verifica se há resultados na consulta
            while(rs.next()){
                //Cria novo objeto Empresa com dados do banco
                Empresa empresa = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                //Define o ID da empresa a partir do banco de dados
                empresa.setId(rs.getInt("id_empresa"));
                //Adiciona empresa à lista
                empresas.add(empresa);
            }
            ConexaoManager.commitAndClose();
            //Retorna lista de empresas
            return empresas;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna lista vazia em caso de erro
            return empresas;
        }
    }

    public List<Empresa> listarEmpresas(){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para listar todas as empresas
        String comandoListar = "select * from empresa";
        //Cria lista vazia para armazenar empresas
        List<Empresa> empresas = new ArrayList<>();
        //Cria statement para execução da consulta
        try(Statement stmt = conn.createStatement()){
            //Executa a consulta e obtém resultados
            ResultSet rs = stmt.executeQuery(comandoListar);
            //Percorre todos os resultados da consulta
            while(rs.next()){
                //Cria novo objeto Empresa com dados do banco
                Empresa empresa = new Empresa(
                        rs.getInt("id_tipo_empresa"),
                        rs.getInt("id_indice_classificacao"),
                        rs.getInt("id_status_aprovacao"),
                        rs.getString("nome"),
                        rs.getString("cnpj"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                //Define o ID da empresa a partir do banco de dados
                empresa.setId(rs.getInt("id_empresa"));
                //Adiciona empresa à lista
                empresas.add(empresa);
            }
            ConexaoManager.commitAndClose();
            //Retorna lista de empresas
            return empresas;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna lista vazia em caso de erro
            return empresas;
        }
    }

    public boolean alterarIdTipoEmpresaEmpresa(int id, int idTipoEmpresaNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar tipo de empresa
        String comandoAtualizar = "update empresa set id_tipo_empresa = ? where id_empresa = ?";
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setInt(1, idTipoEmpresaNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean alterarIdIndiceClassificacaoEmpresa(int id, int idIndiceClassificacaoNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar índice de classificação
        String comandoAtualizar = "update empresa set id_indice_classificacao = ? where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setInt(1, idIndiceClassificacaoNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean alterarIdStatusAprovacaoEmpresa(int id, int idStatusAprovacaoNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar status de aprovação
        String comandoAtualizar = "update empresa set id_status_aprovacao = ? where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setInt(1, idStatusAprovacaoNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean alterarNomeEmpresa(int id, String nomeNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar nome da empresa
        String comandoAtualizar = "update empresa set nome = ? where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, nomeNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean alterarCnpjEmpresa(int id, String cnpjNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar CNPJ da empresa
        String comandoAtualizar = "update empresa set cnpj = ? where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, cnpjNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean alterarEmailEmpresa(int id, String emailNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar email da empresa
        String comandoAtualizar = "update empresa set email = ? where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, emailNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean alterarTelefoneEmpresa(int id, String telefoneNovo){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para atualizar telefone
        String comandoAtualizar = "update empresa set telefone = ? where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            pstmt.setString(1, telefoneNovo);
            pstmt.setInt(2, id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }

    public boolean deletarEmpresa(int id){
        Connection conn = ConexaoManager.conectar();
        //Comando SQL para deletar empresa
        String comandoDeletar = "delete from empresa where id_empresa = ?";
        int linhasAfetadas = 0;
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){
            pstmt.setInt(1 ,id);
            linhasAfetadas = pstmt.executeUpdate();
            ConexaoManager.commitAndClose();
            return linhasAfetadas > 0;
        }catch(SQLException sqle) {
            ConexaoManager.rollbackAndClose();
            sqle.printStackTrace();
            return false;
        }
    }
}
