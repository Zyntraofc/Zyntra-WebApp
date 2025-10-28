package org.example.utils.autenticacao;

/// Classe criada para a criptografia da senha do administrador do sistema, utilizando algoritmo SHA-256
/// O objeto gerado por ela é uma String de 64 digitos sempre

//Importações
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

//Abertura da classe
public class HashSenha {

    //Atributos de senha e senha criptografada
    private String senha;
    private String hashSenha;

    //Metodo construtor que gerará a criptografia da senha
    public HashSenha(String senha) {
        try {
            this.senha = senha;
            //Objeto para criar o hash da senha
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Vetor de bytes para gerar o hexadecimal
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));

            //Construçao do hexadecimal a partir do hash
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // Converte para hexadecimal
                hexString.append(String.format("%02X", b));
            }

            //Retorna String hexadecimal de 64 digitos
            this.hashSenha = hexString.toString();

        }
        //Em casos de erros do nome do algoritmo
        catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
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