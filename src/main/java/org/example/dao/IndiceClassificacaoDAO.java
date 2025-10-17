package org.example.dao;

//Importações
import org.example.conexao.ConexaoManager;
import org.example.model.IndiceClassificacao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class IndiceClassificacaoDAO {

    //Metodo para inserir novo indice de classficacao
    public boolean inserirIndiceClassificacao(IndiceClassificacao indiceClassificacao){
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        String comandoInserir = "insert into indice_classificacao (recomendacao, preocupacao, porcentagem_minima, porcentagem_maxima) values (?,?,?,?)";//Comando SQL para inserir índice de classificação
        int linhasAfetadas = 0;//Variável para verificar se a inserção foi bem-sucedida
        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){//Prepara o comando SQL com retorno de chaves geradas
            //Atribui valores aos parâmetros do comando SQL
            pstmt.setString(1, indiceClassificacao.getRecomendacao());
            pstmt.setString(2, indiceClassificacao.getPreocupacao());
            pstmt.setDouble(3, indiceClassificacao.getPorcentagemMinima());
            pstmt.setDouble(4, indiceClassificacao.getPorcentagemMaxima());

            linhasAfetadas = pstmt.executeUpdate();//Executa a inserção

            if(linhasAfetadas > 0){//Verifica se a inserção foi bem-sucedida
                try (ResultSet rs = pstmt.getGeneratedKeys()) {//Obtém as chaves geradas
                    if (rs.next()) {
                        indiceClassificacao.setId(rs.getInt(1)); // Define o ID gerado no objeto
                    }
                }
                ConexaoManager.commit();
                return true;//Retorna sucesso
            }
            ConexaoManager.rollback();
            return false;//Retorna falha
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }
    }

    //Metodo para listar um indice de classificacao específico pelo id
    public IndiceClassificacao listarIndiceClassificacaoPorId(int id){
        String comandoListar = "select * from indice_classificacao where id_indice_classificacao = ?";//Comando SQL para buscar por ID
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setInt(1, id);//Atribui o ID ao parâmetro

            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){//Se encontrar resultado
                IndiceClassificacao indice = new IndiceClassificacao(//Cria objeto com dados do banco
                        rs.getString("recomendacao"),
                        rs.getString("preocupacao"),
                        rs.getDouble("porcentagem_minima"),
                        rs.getDouble("porcentagem_maxima")
                );
                indice.setId(rs.getInt("id_indice_classificacao"));//Define o ID do banco
                ConexaoManager.commit();
                return indice;//Retorna o índice encontrado
            }
            ConexaoManager.commit();
            return null;//Retorna null se não encontrado
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return null;//Retorna null em caso de erro
        }
    }

    //Metodo para listar um indice de classificacao específico pelo percentual
    public IndiceClassificacao listarIndiceClassificacaoPorPercentual(double percentual){
        String comandoListar = "select * from indice_classificacao where ? between porcentagem_minima and porcentagem_maxima";//Comando SQL para buscar por percentual
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setDouble(1, percentual);//Atribui o percentual ao parâmetro

            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){//Se encontrar resultado
                IndiceClassificacao indice = new IndiceClassificacao(//Cria objeto com dados do banco
                        rs.getString("recomendacao"),
                        rs.getString("preocupacao"),
                        rs.getDouble("porcentagem_minima"),
                        rs.getDouble("porcentagem_maxima")
                );
                indice.setId(rs.getInt("id_indice_classificacao"));//Define o ID do banco
                ConexaoManager.commit();
                return indice;//Retorna o índice encontrado
            }
            ConexaoManager.commit();
            return null;//Retorna null se não encontrado
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return null;//Retorna null em caso de erro
        }
    }

    //Metodo para listar todos os indices de classificacao
    public List<IndiceClassificacao> listarIndicesClassificacao(){
        String comandoListar = "select * from indice_classificacao";//Comando SQL para listar todos
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        List<IndiceClassificacao> indicesClassificacao = new ArrayList<>();//Lista para armazenar resultados
        try(Statement stmt = conn.createStatement()){//Cria statement para consulta
            ResultSet rs = stmt.executeQuery(comandoListar);//Executa a consulta
            while(rs.next()){//Percorre todos os resultados
                IndiceClassificacao indice = new IndiceClassificacao(//Cria objeto com dados do banco
                        rs.getString("recomendacao"),
                        rs.getString("preocupacao"),
                        rs.getDouble("porcentagem_minima"),
                        rs.getDouble("porcentagem_maxima")
                );
                indice.setId(rs.getInt("id_indice_classificacao"));//Define o ID do banco
                indicesClassificacao.add(indice);//Adiciona à lista
            }
            ConexaoManager.commit();
            return indicesClassificacao;//Retorna a lista completa
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return indicesClassificacao;//Retorna lista vazia em caso de erro
        }
    }

    //Metodo para atualizar a coluna recomendacao pelo id
    public boolean alterarRecomendacaoIndiceClassificacao(int id, String recomendacaoNova){
        String comandoAtualizar = "update indice_classificacao set recomendacao = ? where id_indice_classificacao = ?";//Comando SQL para atualizar recomendação
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setString(1, recomendacaoNova);//Atribui nova recomendação
            pstmt.setInt(2, id);//Atribui ID do índice
            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização
            ConexaoManager.commit();
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }
    }

    //Metodo para atualizar a coluna preocupacao pelo id
    public boolean alterarPreocupacaoIndiceClassificacao(int id, String preocupacaoNova){
        String comandoAtualizar = "update indice_classificacao set preocupacao = ? where id_indice_classificacao = ?";//Comando SQL para atualizar preocupação
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setString(1, preocupacaoNova);//Atribui nova preocupação
            pstmt.setInt(2, id);//Atribui ID do índice
            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização
            ConexaoManager.commit();
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }
    }

    //Metodo para atualizar a coluna porcentagem_minima pelo id
    public boolean alterarPorcentagemMinimaIndiceClassificacao(int id, double porcentagemMinimaNova){
        String comandoAtualizar = "update indice_classificacao set porcentagem_minima = ? where id_indice_classificacao = ?";//Comando SQL para atualizar porcentagem mínima
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setDouble(1, porcentagemMinimaNova);//Atribui nova porcentagem mínima
            pstmt.setInt(2, id);//Atribui ID do índice
            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização
            ConexaoManager.commit();
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }
    }

    //Metodo para atualizar a coluna porcentagem_maxima pelo id
    public boolean alterarPorcentagemMaximaIndiceClassificacao(int id, double porcentagemMaximaNova){
        String comandoAtualizar = "update indice_classificacao set porcentagem_maxima = ? where id_indice_classificacao = ?";//Comando SQL para atualizar porcentagem máxima
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setDouble(1, porcentagemMaximaNova);//Atribui nova porcentagem máxima
            pstmt.setInt(2, id);//Atribui ID do índice
            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização
            ConexaoManager.commit();
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle){
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }
    }

    //Metodo para deletar registro pelo id
    public boolean deletarIndiceClassificacao(int id){
        String comandoDeletar = "delete from indice_classificacao where id_indice_classificacao = ?";//Comando SQL para deletar índice
        Connection conn = ConexaoManager.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar exclusão
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){//Prepara o comando SQL
            pstmt.setInt(1 ,id);//Atribui ID do índice
            linhasAfetadas = pstmt.executeUpdate();//Executa a exclusão
            ConexaoManager.commit();
            return linhasAfetadas > 0;//Retorna se foi bem-sucedido
        }catch(SQLException sqle) {
            ConexaoManager.rollback();
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }
    }
}