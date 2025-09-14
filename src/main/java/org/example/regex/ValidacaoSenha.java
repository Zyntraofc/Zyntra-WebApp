package org.example.regex;

import java.util.regex.*;

public class ValidacaoSenha {
    public static boolean validarSenha(String senha) {
        String expressao = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        Pattern regex = Pattern.compile(expressao);
        Matcher validacao = regex.matcher(senha);
        return validacao.matches();
    }
}