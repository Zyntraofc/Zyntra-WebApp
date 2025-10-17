package org.example.utils.regex;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
public class ValidacaoCnpj {
    public static boolean isCNPJValido(String cnpj) {
        CNPJValidator validator = new CNPJValidator();
        try {
            validator.assertValid(cnpj);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }
}
