package org.example.utils.regex;
import java.util.regex.*;
public class ValidacaoTelefone {
    public boolean validarTelefone(String telefone){
        String expressao = "\\(?\\d{2}\\)? ?\\d{5}-?\\d{4}";
        Pattern regex = Pattern.compile(expressao);
        Matcher validacao = regex.matcher(telefone);
        return validacao.matches();
    }

    public String padronizarTelefone(String telefone){
        if(validarTelefone(telefone)){
            String telefoneFormatado = telefone.replaceAll("[^0-9]", "");
            return telefoneFormatado;
        }else{
            return null;
        }
    }
}