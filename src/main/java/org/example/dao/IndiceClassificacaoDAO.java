package org.example.dao;

//Importações
import org.example.conexao.Conexao;
import org.example.model.IndiceClassificacao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class IndiceClassificacaoDAO {//Abertura da classe

    //Metodo para inserir novo indice de classficacao
    public boolean inserirIndiceClassificacao(IndiceClassificacao indiceClassificacao){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoInserir = "insert into indice_classificacao (recomendacao, preocupacao, porcentagem_minima, porcentagem_maxima) values (?,?,?,?)";//Comando SQL para inserir índice de classificação
        Connection conn = conexao.conectar();//Conecta ao banco de dados
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
                return true;//Retorna sucesso
            }
            return false;//Retorna falha
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para listar um indice de classificacao específico pelo id
    public IndiceClassificacao listarIndiceClassificacaoPorId(int id){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from indice_classificacao where id_indice_classificacao = ?";//Comando SQL para buscar por ID
        Connection conn = conexao.conectar();//Conecta ao banco de dados
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
                return indice;//Retorna o índice encontrado
            }
            return null;//Retorna null se não encontrado
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return null;//Retorna null em caso de erro
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para listar todos os indices de classificacao
    public List<IndiceClassificacao> listarIndicesClassificacao(){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from indice_classificacao";//Comando SQL para listar todos (CORRIGIDO: nome da tabela)
        Connection conn = conexao.conectar();//Conecta ao banco de dados
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
            return indicesClassificacao;//Retorna a lista completa
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return indicesClassificacao;//Retorna lista vazia em caso de erro
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para listar todos os indices de classificacao por porcentagem recebida
    public IndiceClassificacao listarIndiceClassificacaoPorPorcentagem(double porcentagem){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from indice_classificacao where porcentagem_minima <= ? AND porcentagem_maxima > ?";//Comando SQL para buscar por porcentagem
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setDouble(1, porcentagem);//Atribui a porcentagem ao parâmetro
            pstmt.setDouble(2, porcentagem);

            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){//Se encontrar resultado
                IndiceClassificacao indice = new IndiceClassificacao(//Cria objeto com dados do banco
                        rs.getString("recomendacao"),
                        rs.getString("preocupacao"),
                        rs.getDouble("porcentagem_minima"),
                        rs.getDouble("porcentagem_maxima")
                );
                indice.setId(rs.getInt("id_indice_classificacao"));//Define o ID do banco
                return indice;//Retorna o índice encontrado
            }
            return null;//Retorna null se não encontrado
        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return null;//Retorna null em caso de erro
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para atualizar a coluna recomendacao pelo id
    public boolean alterarRecomendacaoIndiceClassificacao(int id, String recomendacaoNova){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update indice_classificacao set recomendacao = ? where id_indice_classificacao = ?";//Comando SQL para atualizar recomendação
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setString(1, recomendacaoNova);//Atribui nova recomendação
            pstmt.setInt(2, id);//Atribui ID do índice

            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização

            return linhasAfetadas > 0;//Retorna se foi bem-sucedido

        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para atualizar a coluna preocupacao pelo id
    public boolean alterarPreocupacaoIndiceClassificacao(int id, String preocupacaoNova){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update indice_classificacao set preocupacao = ? where id_indice_classificacao = ?";//Comando SQL para atualizar preocupação
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setString(1, preocupacaoNova);//Atribui nova preocupação
            pstmt.setInt(2, id);//Atribui ID do índice

            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização

            return linhasAfetadas > 0;//Retorna se foi bem-sucedido

        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para atualizar a coluna porcentagem_minima pelo id
    public boolean alterarPorcentagemMinimaIndiceClassificacao(int id, double porcentagemMinimaNova){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update indice_classificacao set porcentagem_minima = ? where id_indice_classificacao = ?";//Comando SQL para atualizar porcentagem mínima
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setDouble(1, porcentagemMinimaNova);//Atribui nova porcentagem mínima
            pstmt.setInt(2, id);//Atribui ID do índice

            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização

            return linhasAfetadas > 0;//Retorna se foi bem-sucedido

        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para atualizar a coluna porcentagem_maxima pelo id
    public boolean alterarPorcentagemMaximaIndiceClassificacao(int id, double porcentagemMaximaNova){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update indice_classificacao set porcentagem_maxima = ? where id_indice_classificacao = ?";//Comando SQL para atualizar porcentagem máxima
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar atualização
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara o comando SQL
            pstmt.setDouble(1, porcentagemMaximaNova);//Atribui nova porcentagem máxima
            pstmt.setInt(2, id);//Atribui ID do índice

            linhasAfetadas = pstmt.executeUpdate();//Executa a atualização

            return linhasAfetadas > 0;//Retorna se foi bem-sucedido

        }catch(SQLException sqle){
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }

    //Metodo para deletar registro pelo id
    public boolean deletarIndiceClassificacao(int id){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoDeletar = "delete from indice_classificacao where id_indice_classificacao = ?";//Comando SQL para deletar índice
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        int linhasAfetadas = 0;//Variável para verificar exclusão
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){//Prepara o comando SQL
            pstmt.setInt(1 ,id);//Atribui ID do índice
            linhasAfetadas = pstmt.executeUpdate();//Executa a exclusão

            return linhasAfetadas > 0;//Retorna se foi bem-sucedido

        }catch(SQLException sqle) {
            sqle.printStackTrace();//Imprime erro
            return false;//Retorna falha
        }finally{
            conexao.desconectar(conn);//Desconecta do banco
        }
    }
}