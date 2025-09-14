package org.example.model;

//Importação da classe hashSenha para criptografia da senha do administrador
import org.example.dao.HashSenha;

public class Administrador extends BaseModel{//Abertura da classe model Administrador

    //Declaração de atributos
    private String email;
    private String hashSenha;

    //Metodo construtor
    public Administrador(String email, HashSenha hashSenha){//Único metodo construtor da classe
        this.email = email;
        this.hashSenha = String.valueOf(hashSenha);
    }

    public Administrador(String email, String hashSenha){//Único metodo construtor da classe
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

    //Metodo toString
    public String toString(){
        return "Id: "+(super.getId() == null ? "Nulo" : super.id) +
                "\nEmail: " + this.email +
                "\nSenha: " + this.hashSenha;
    }


}
