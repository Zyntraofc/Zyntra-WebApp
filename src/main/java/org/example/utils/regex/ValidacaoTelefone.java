package org.example.utils.regex;

/// Classe criada para validar telefone por regex e deixar ele em um formato para o banco de dados

//Importação de regex
import java.util.regex.*;

//Abertura da classe
public class ValidacaoTelefone {

    //Metodo para validar o telefone
    public boolean validarTelefone(String telefone){
        //Expressão regular (regex) --> Aceita: (XX) XXXXX-XXXX; XX XXXXX-XXXX; XXXXXXXXXXX; etc...
        String expressao = "\\(?\\d{2}\\)? ?\\d{5}-?\\d{4}";
        //Compilando expressão
        Pattern regex = Pattern.compile(expressao);
        //Comparador com telefone
        Matcher validacao = regex.matcher(telefone);

        //Retorna se o telefone é válido
        return validacao.matches();
    }


    //Metodo para padronizar telefone
    public String padronizarTelefone(String telefone){
        //Verificação se o telefone é válido
        if(validarTelefone(telefone)){
            //Troca tudo que não for número no telefone por um espaço vazio e retorna
            String telefoneFormatado = telefone.replaceAll("[^0-9]", "");
            return telefoneFormatado;
        }else{
            //Se não for válido retorna nulo
            return null;
        }
    }
}