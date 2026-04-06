package com.felipesouza.usuario.infrastructure.exceptions;

//Extende a RunTimeException pois é com ela que criamos exceções não verificadas para tratarmos de acordo com nossa lógica
public class ConflictException extends RuntimeException {

    //Recebe uma mensagem
    public ConflictException(String mensagem) {
        super(mensagem);
    }

    //Recebe uma mensagem e um Throwable
    public ConflictException(String mensagem, Throwable throwable) {
        super(mensagem);
    }
}