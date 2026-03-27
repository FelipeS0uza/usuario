package com.felipesouza.usuario.infrastructure.exceptions;

//Extende a RunTimeException pois é com ela que criamos exceções não verificadas para tratarmos de acordo com nossa lógica
//ConflictException representa um erro de conflito de estado
public class ConflictException extends RuntimeException {

    //Recebe uma mensagem e passa para a classe pai RuntimeException
    public ConflictException(String mensagem) {
        super(mensagem);
    }

    //Recebe uma mensagem e causa do erro(throwable)
    //Throwable é a classe base de todos os erros em Java
    public ConflictException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}