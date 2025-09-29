package org.example.regex;

import java.util.regex.*;
public class ValidacaoCpf {
    public boolean validarCpf(String cpf){
        String expressao = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}";
        Pattern regex = Pattern.compile(expressao);
        Matcher validacao = regex.matcher(cpf);
        cpf = cpf.replaceAll("[^0-9]", "");
        return validacao.matches();
    }

}
