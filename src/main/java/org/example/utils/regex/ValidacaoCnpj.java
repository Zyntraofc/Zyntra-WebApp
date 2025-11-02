package org.example.utils.regex;

/// Classe criada para validação real do CNPJ

//Importações
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import java.util.regex.*;

//Abertura da classe
public class ValidacaoCnpj {

    //Metodo para validar cnpjs validos e existentes
    public static boolean isCNPJValido(String cnpj) {
        //Objeto de validação real do cnpj
        CNPJValidator validator = new CNPJValidator();
        try {
            //Validando cnpj
            validator.assertValid(cnpj);
            return true;
        }
        //Caso o cnpj não seja válido lançará um exceção, nesse caso retorna falso
        catch (InvalidStateException e) {
            return false;
        }

    }

    //Metodo para formatar CNPJ (Todos os CNPJ devem ter o mesmo formato no banco de dados)
    public static String formatarCnpj(String cnpj){
        //Se o cnpj for nulo retorna null (Dá errose tentar fazer operações com String nula)
        if(cnpj == null){
            return null;
        }

        //Troca tudo oque não for número por um espaço vazio na String
        String cnpjFormatado = cnpj.replaceAll("[^0-9]", "");

        //Retorna o CNPJ formatado
        return cnpjFormatado;
    }
}
