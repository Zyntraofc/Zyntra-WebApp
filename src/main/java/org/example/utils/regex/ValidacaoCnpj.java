package org.example.utils.regex;

/// Classe criada para validação real do CNPJ

//Importações
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;

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
}
