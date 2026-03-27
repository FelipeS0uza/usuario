package com.felipesouza.usuario.infrastructure.exceptions;

//Extende a RunTimeException pois é com ela que criamos exceções não verificadas para tratarmos de acordo com nossa lógica
public class ResourceNotFoundException extends RuntimeException{

    //Recebe uma mensagem e passa para a classe pai RuntimeException
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

    //Recebe uma mensagem e causa do erro(throwable)
    //Throwable é a classe base de todos os erros em Java
    public ResourceNotFoundException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}