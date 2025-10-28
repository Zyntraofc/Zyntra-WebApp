package org.example.exceptions;

/// Classe de exceção de commit

//Abertura da classe
public class FailedCommitException extends RuntimeException {

    //Metodo construtor que recebe mensagem de erro e causa do erro
    public FailedCommitException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
