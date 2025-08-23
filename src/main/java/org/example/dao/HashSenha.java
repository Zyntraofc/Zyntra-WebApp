package org.example.dao;

import java.security.NoSuchAlgorithmException;//Importando Exception caso o algoritmo SHA-256 não seja valido
import java.security.MessageDigest; //Classe para converter senha para Hash
import java.nio.charset.StandardCharsets;//Classe converters bits de senha para hexadecimal
public class HashSenha {
    //Atributos da classe
    private final String senha;//Atributo de senha original
    private String hashSenha;//Atributo de senha com hash

    //Metodo construtor
    public HashSenha(String senha) throws NoSuchAlgorithmException {
        this.senha = senha;//Atribuição de valores para senha
        //Objeto para criar valor do hashSenha
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //Conversao de senha para hexadecimal
        byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
        //Construção de hexadecimal a partir do hash
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02X", b));
        }
        //Atribuição de hash para a variavel
        this.hashSenha = hash.toString();
    }

    //Métodos getters
    public String getSenha() {
        return this.senha;//Retorno de senha sem criptografia
    }

    //Metodo toString
    public String toString(){
        return this.hashSenha;//Retorno de hash da senha
    }
}
