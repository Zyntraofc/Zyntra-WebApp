package org.example.dao;

//Importações
import org.example.conexao.Conexao;
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
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para inserir uma nova empresa na tabela
        String comandoInserir = "insert into empresa (id_tipo_empresa, id_indice_classificacao, id_status_aprovacao, nome, cnpj, email, telefone) values (?,?,?,?,?,?,?)";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
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
                //Retorna verdadeiro indicando sucesso na inserção
                return true;
            }
            //Retorna falso indicando falha na inserção
            return false;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso indicando falha na operação
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public Empresa listarEmpresaPorId(int id){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para buscar empresa por ID
        String comandoListar = "select * from empresa where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
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
                //Retorna a empresa encontrada
                return empresa;
            }
            //Retorna null se nenhuma empresa for encontrada
            return null;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna null em caso de erro
            return null;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public Empresa listarEmpresaPorCnpj(String cnpj){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para buscar empresa por CNPJ
        String comandoListar = "select * from empresa where cnpj = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Prepara o statement SQL para consulta
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){
            //Atribui o CNPJ ao parâmetro da consulta
            pstmt.setString(1, cnpj);

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
                //Retorna a empresa encontrada
                return empresa;
            }
            //Retorna null se nenhuma empresa for encontrada
            return null;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna null em caso de erro
            return null;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public List<Empresa> listarEmpresas(){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para listar todas as empresas
        String comandoListar = "select * from empresa";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
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
            //Retorna lista de empresas
            return empresas;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna lista vazia em caso de erro
            return empresas;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarIdTipoEmpresaEmpresa(int id, int idTipoEmpresaNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para atualizar tipo de empresa
        String comandoAtualizar = "update empresa set id_tipo_empresa = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo ID do tipo de empresa ao primeiro parâmetro
            pstmt.setInt(1, idTipoEmpresaNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarIdIndiceClassificacaoEmpresa(int id, int idIndiceClassificacaoNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para atualizar índice de classificação
        String comandoAtualizar = "update empresa set id_indice_classificacao = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo ID do índice de classificação ao primeiro parâmetro
            pstmt.setInt(1, idIndiceClassificacaoNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarIdStatusAprovacaoEmpresa(int id, int idStatusAprovacaoNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL CORRIGIDO para atualizar status de aprovação
        String comandoAtualizar = "update empresa set id_status_aprovacao = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo ID do status de aprovação ao primeiro parâmetro
            pstmt.setInt(1, idStatusAprovacaoNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarNomeEmpresa(int id, String nomeNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para atualizar nome da empresa
        String comandoAtualizar = "update empresa set nome = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo nome ao primeiro parâmetro
            pstmt.setString(1, nomeNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarCnpjEmpresa(int id, String cnpjNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para atualizar CNPJ da empresa
        String comandoAtualizar = "update empresa set cnpj = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo CNPJ ao primeiro parâmetro
            pstmt.setString(1, cnpjNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarEmailEmpresa(int id, String emailNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para atualizar email da empresa
        String comandoAtualizar = "update empresa set email = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo email ao primeiro parâmetro
            pstmt.setString(1, emailNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean alterarTelefoneEmpresa(int id, String telefoneNovo){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL CORRIGIDO para atualizar telefone (não "numero")
        String comandoAtualizar = "update empresa set telefone = ? where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){
            //Atribui novo telefone ao primeiro parâmetro
            pstmt.setString(1, telefoneNovo);
            //Atribui ID da empresa ao segundo parâmetro
            pstmt.setInt(2, id);

            //Executa a atualização e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;
        }catch(SQLException sqle){
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }

    public boolean deletarEmpresa(int id){
        //Instancia da classe de conexão com o banco de dados
        Conexao conexao = new Conexao();
        //Comando SQL para deletar empresa
        String comandoDeletar = "delete from empresa where id_empresa = ?";
        //Estabelece conexão com o banco de dados
        Connection conn = conexao.conectar();
        //Variável para controlar o número de linhas afetadas
        int linhasAfetadas = 0;
        //Prepara o statement SQL para exclusão
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){
            //Atribui ID da empresa ao parâmetro
            pstmt.setInt(1 ,id);
            //Executa a exclusão e obtém número de linhas afetadas
            linhasAfetadas = pstmt.executeUpdate();

            //Retorna verdadeiro se pelo menos uma linha foi afetada
            return linhasAfetadas > 0;

        }catch(SQLException sqle) {
            //Imprime stack trace em caso de exceção SQL
            sqle.printStackTrace();
            //Retorna falso em caso de erro
            return false;
        }finally{
            //Desconecta do banco de dados antes de retornar
            conexao.desconectar(conn);
        }
    }
}