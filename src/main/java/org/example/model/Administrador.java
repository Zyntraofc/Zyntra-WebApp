package org.example.model;

/// Classe criada com o objetivo de representar entidade da tabela Administrador do banco de dados

//Importações
import org.example.utils.autenticacao.HashSenha;

//Abertura da classe
public class Administrador extends BaseModel{

    //Declaração de atributos
    private String email;
    private String hashSenha;

    //Metodo construtor (caso receba objeto HashSenha para criptografia)
    public Administrador(String email, HashSenha hashSenha){
        this.email = email;
        this.hashSenha = String.valueOf(hashSenha);
    }

    //Metodo construtor (Caso receba objeto hashSenha com a senha já criptografada)
    public Administrador(String email, String hashSenha){
        this.email = email;
        this.hashSenha = hashSenha;
    }


    //Métodos getters
    public String getEmail() {
        return this.email;
    }

    public String getHashSenha() {
        return this.hashSenha;
    }

    //Métodos setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashSenha(HashSenha hashSenha) {
        this.hashSenha = String.valueOf(hashSenha);
    }

    //Metodo toString de representação do objeto
    public String toString(){
        return
                "\nEmail: " + this.email +
                        "\nSenha: " + this.hashSenha;
    }


}
