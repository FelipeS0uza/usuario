package com.felipesouza.usuario.infrastructure.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {

    //Recebe uma mensagem
    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }

    //Recebe uma mensagem e um Throwable
    public UnauthorizedException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}