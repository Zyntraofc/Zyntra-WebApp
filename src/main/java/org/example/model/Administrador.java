package org.example.model;

import org.example.dao.HashSenha;

public class Administrador extends BaseModel{//Abertura da classe model Adm
    //Declaração de atributos
    private String email;
    private HashSenha hashSenha;

    //Método construtor
    public Administrador(String email, HashSenha hashSenha){//Único método construtor da classe
        this.email = email;
        this.hashSenha = hashSenha;
    }

    //Métodos getters
    public String getEmail() {
        return this.email;
    }
    public HashSenha getHashSenha() {
        return this.hashSenha;
    }

    //Métodos setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setHashSenha(HashSenha hashSenha) {
        this.hashSenha = hashSenha;
    }

    //Método toString
    public String toString(){
        return "Email: " + this.email + "\nSenha: " + this.hashSenha + "\n";
    }


}
