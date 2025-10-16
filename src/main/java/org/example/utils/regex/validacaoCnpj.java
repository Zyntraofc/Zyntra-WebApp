package org.example.utils.regex;

import java.util.regex.*;
public class validacaoCnpj {
    public boolean validarCnpj(String cnpj){
        String expressao = "^\\d{2}\\.?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2}$";
        Pattern regex = Pattern.compile(expressao);
        Matcher validacao = regex.matcher(cnpj);
        cnpj = cnpj.replaceAll("[^0-9]", "");
        return validacao.matches();
    }

    public String padronizarCnpj(String cnpj){
        if(validarCnpj(cnpj)){
            String cnpjFormatado = cnpj.replaceAll("[^0-9]", "");
            return cnpjFormatado;
        }else{
            return null;
        }
    }

}
