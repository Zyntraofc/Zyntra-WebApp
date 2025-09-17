package org.example.dao;

//Importações
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.conexao.Conexao;
import org.example.model.Administrador;

public class AdministradorDAO {//Abertura da classe

    //Metodo booleano para registrar administradores
    public boolean inserirAdministrador(Administrador administrador){
        Conexao conexao = new Conexao();//Abrindo classe para utilizar métodos de conexão de forma segura
        String comandoInserir = "insert into Administrador (email, hash_senha) values (?,?);";//Comando para inserir administrador
        Connection conn = conexao.conectar();//Abrindo e mantendo a conexão com metodo 'conectar()' da classe Conexao
        int linhasAfetadas = 0;//Variavel para verificar se a atualização deu certo no banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoInserir, Statement.RETURN_GENERATED_KEYS)){//Tratamento de excessoes com objeto para a execução do comando com retorno das chaves geradas no DB
            pstmt.setString(1, administrador.getEmail());//Atribuindo o email ao primeiro '?'
            pstmt.setString(2, administrador.getHashSenha());//Atribuindo a senha ao segundo '?'
            linhasAfetadas = pstmt.executeUpdate();//Executando comando e retornando o número de linhas afetadas a variavel de verificacao
            if(linhasAfetadas > 0){//Verificacao da atrualizacao
                try (ResultSet rs = pstmt.getGeneratedKeys()) {//Atribuindo chaves geradas no db ao ResultSet
                    if (rs.next()) {
                        administrador.setId(rs.getInt(1)); // seta o ID gerado (índice 1 do GeneratedKeys)
                    }
                }
                return true;//Retorna que a insercao deu certo
            }
            return false;//Retorna que a insercao deu errado
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;//Retorna que a insercao deu errado
        }
        finally{
            conexao.desconectar(conn);//Desconecta antes de quaisquer retornos
        }
    }


    //Metodo para fazer a listagem de administradores pelo email
    public Administrador listarAdministradorPorEmail(String email){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from Administrador a where a.email = ?";//Comando SQL para busca por email
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setString(1, email);//Atribui o valor do email ao parâmetro '?'
            ResultSet rs = pstmt.executeQuery();//Executa a consulta e armazena resultado no ResultSet
            if(rs.next()){//Se houver registro correspondente
                //Criação do objeto Administrador a partir dos dados do ResultSet
                Administrador administrador = new Administrador(
                        rs.getString("email"),
                        rs.getString("hash_senha")
                );
                //Atribuição do ID do banco de dados ao objeto através do metodo setId()
                administrador.setId(rs.getInt("id_adm"));
                return administrador;//Retorna o administrador encontrado
            }
            return null;//Nenhum administrador encontrado
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
        finally{
            conexao.desconectar(conn);//Encerra a conexão
        }
    }

    //Metodo para listar administrador por ID
    public Administrador listarAdministradorPorId(int id){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from Administrador a where a.id_adm = ?";//Comando SQL para busca por id_adm
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        try(PreparedStatement pstmt = conn.prepareStatement(comandoListar)){//Prepara o comando SQL
            pstmt.setInt(1, id);//Atribui o id ao parâmetro '?'
            ResultSet rs = pstmt.executeQuery();//Executa a consulta
            if(rs.next()){//Se houver registro correspondente
                //Criação do objeto Administrador a partir dos dados do ResultSet
                Administrador administrador = new Administrador(
                        rs.getString("email"),
                        rs.getString("hash_senha")
                );
                //Atribuição do ID do banco de dados ao objeto através do método setId()
                administrador.setId(rs.getInt("id_adm"));
                return administrador;
            }
            return null;//Nenhum administrador encontrado
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
        finally{
            conexao.desconectar(conn);//Fecha a conexão
        }
    }

    //Metodo para listar todos administradores
    public List<Administrador> listarAdministradores(){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoListar = "select * from Administrador";//Comando SQL para listar todos administradores
        Connection conn = conexao.conectar();//Conecta ao banco de dados
        List<Administrador> administradores = new ArrayList<>();//Lista que armazenará os administradores
        try(Statement stmt = conn.createStatement()){//Criação de objeto para executar comandos SQL
            ResultSet rs = stmt.executeQuery(comandoListar);//Executa a consulta
            while(rs.next()){//Itera sobre cada registro do banco
                Administrador administrador = new Administrador(
                        rs.getString("email"),
                        rs.getString("hash_senha")
                );
                //Atribuição do ID do banco de dados ao objeto através do método setId()
                administrador.setId(rs.getInt("id_adm"));
                administradores.add(administrador);//Adiciona objeto administrador na lista
            }
            return administradores;//Retorna a lista completa

        }catch(SQLException sqle){
            sqle.printStackTrace();
            return administradores;
        }finally{
            conexao.desconectar(conn);//Fecha a conexão
        }
    }

    //Metodo para alterar email de administrador
    public boolean alterarEmailAdministrador(int id, String emailNovo){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update Administrador set email = ? where id_adm = ?";//Comando SQL para atualizar email
        Connection conn = conexao.conectar();//Conecta ao banco
        int linhasAfetadas = 0;//Variável de controle
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara comando SQL
            pstmt.setString(1, emailNovo);//Atribui novo email
            pstmt.setInt(2, id);//Atribui id do administrador
            linhasAfetadas = pstmt.executeUpdate();//Executa atualização
            return linhasAfetadas > 0;//Retorna verdadeiro se houve alteração
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            conexao.desconectar(conn);//Fecha conexão
        }
    }

    //Metodo para alterar senha de administrador
    public boolean alterarSenhaAdministrador(int id, String senhaNova){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoAtualizar = "update Administrador set hash_senha = ? where id_adm = ?";//Comando SQL para atualizar senha
        Connection conn = conexao.conectar();//Conecta ao banco
        int linhasAfetadas = 0;//Variável de controle
        try(PreparedStatement pstmt = conn.prepareStatement(comandoAtualizar)){//Prepara comando SQL
            pstmt.setString(1, String.valueOf(new HashSenha(senhaNova)));//Atribui a nova senha já com hash
            pstmt.setInt(2, id);//Atribui id do administrador
            linhasAfetadas = pstmt.executeUpdate();//Executa atualização
            return linhasAfetadas > 0;//Retorna verdadeiro se houve alteração
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{
            conexao.desconectar(conn);//Fecha conexão
        }
    }

    //Metodo para deletar administrador
    public boolean deletarAdministrador(int id){
        Conexao conexao = new Conexao();//Instancia da conexão
        String comandoDeletar = "delete from Administrador where id_adm = ?";//Comando SQL para deletar administrador (ajustado para id_adm)
        Connection conn = conexao.conectar();//Conecta ao banco
        int linhasAfetadas = 0;//Variável de controle
        try(PreparedStatement pstmt = conn.prepareStatement(comandoDeletar)){//Prepara comando SQL
            pstmt.setInt(1, id);//Atribui id ao parâmetro
            linhasAfetadas = pstmt.executeUpdate();//Executa comando
            return linhasAfetadas > 0;//Retorna verdadeiro se houve exclusão
        }catch(SQLException sqle){
            sqle.printStackTrace();
            return false;
        }finally{//Desconectando do database antes de fazer retorno
            conexao.desconectar(conn);
        }
    }

}
