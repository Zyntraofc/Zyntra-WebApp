package org.example.exceptions;

///Classe de exceção no rollback

//Abertura da classe
public class RollbackException extends RuntimeException {

    //Metodo construtor que recebe mensagem de erro e causa do erro
    public RollbackException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
