package org.example.regex;
import java.util.regex.*;
public class ValidacaoTelefone {
    public boolean validarTelefone(String telefone){
        String expressao = "\\(\\d{2}\\) \\d{5}-\\d{4}";
        Pattern regex = Pattern.compile(expressao);
        Matcher validacao = regex.matcher(telefone);
        return validacao.matches();
    }
}