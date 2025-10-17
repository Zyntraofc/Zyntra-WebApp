package org.example.utils.autenticacao;

//Importações
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

//Abertura da classe
public class HashSenha {
    private String senha;//Atributo de senha recebida
    private String hashSenha;//Atributo de hash senha feito a partir da senha

    //Metodo construtor que gerará a criptografia da senha
    public HashSenha(String senha) {
        try {
            this.senha = senha;//Atribuindo parametro ao atributo senha
            //Objeto para criar o hash da senha
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Vetor de bytes para gerar o hexadecimal
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));

            //Construçao do hexadecimal a partir do hash
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02X", b)); // Converte para hexadecimal
            }

            //Retorna String hexadecimal de 64 digitos
            this.hashSenha = hexString.toString();

        } catch (NoSuchAlgorithmException nsae) {//Tratamento da excessao de algoritmo
            nsae.printStackTrace();//Printa pilha de erros
        }
    }

    //Metodo get da senha
    public String getSenha() {
        return this.senha;
    }

    //Metodo get do hash senha
    public String getHashSenha() {
        return this.hashSenha;
    }

    //Metodo toString do hash senha
    public String toString() {
        return this.hashSenha;
    }
}