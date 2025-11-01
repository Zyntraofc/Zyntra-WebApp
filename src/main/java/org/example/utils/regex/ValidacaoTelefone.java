package org.example.utils.regex;

/// Classe criada para validar telefone por regex e deixar ele em um formato para o banco de dados

//Importação de regex
import java.util.regex.*;

//Abertura da classe
public class ValidacaoTelefone {

    //Metodo para validar o telefone
    public boolean validarTelefone(String telefone){
        //Expressão regular (regex) --> Aceita: (XX) XXXXX-XXXX; XX XXXXX-XXXX; XXXXXXXXXXX; etc...
        String expressao = "^(\\(?\\d{2}\\)?\\s?)?(\\d{4,5})[-\\s]?\\d{4}$";
        //Compilando expressão
        Pattern regex = Pattern.compile(expressao);
        //Comparador com telefone
        Matcher validacao = regex.matcher(telefone);

        //Retorna se o telefone é válido
        return validacao.matches();
    }


    //Metodo para padronizar telefone (Todos os telefones devem ter o mesmo formato no banco de dados)
    public static String formatarTelefone(String telefone){

        //Se o telefone for nulo retorna null (não é possivel fazer operações com String nula)
        if(telefone == null){
            return null;
        }

        //Troca tudo oque não for null por um espaço vazio na String
        String telefoneFormatado = telefone.replaceAll("[^0-9]", "");

        //Retorna o telefone formatado
        return telefoneFormatado;
    }
}