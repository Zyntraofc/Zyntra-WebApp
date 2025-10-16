package org.example.utils.regex;

import java.util.regex.*;

public class ValidacaoEmail {
    public boolean validarEmail(String email){
        String expressao = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        Pattern regex = Pattern.compile(expressao);
        Matcher validacao = regex.matcher(email);
        return validacao.matches();
    }
}