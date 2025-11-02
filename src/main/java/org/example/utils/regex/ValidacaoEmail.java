package org.example.utils.regex;

/// Classe de validação do email por regex

//Importação de regex
import java.util.regex.*;

//Abertura da classe
public class ValidacaoEmail {

    //Metodo booleano para validar email, retorna true se válido
    public boolean validarEmail(String email){
        //Expressão regular (regex)
        String expressao = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        //Compila expressão regular
        Pattern regex = Pattern.compile(expressao);

        //Comparador com email
        Matcher validacao = regex.matcher(email);

        //Retorna se é válido
        return validacao.matches();
    }
}