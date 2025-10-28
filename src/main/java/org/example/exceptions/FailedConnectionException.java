package org.example.exceptions;

/// Classe de exceção na conexão

//Abertura da classe
public class FailedConnectionException extends RuntimeException {

    //Metodo construtor que recebe mensagem de erro e causa do erro
    public FailedConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
