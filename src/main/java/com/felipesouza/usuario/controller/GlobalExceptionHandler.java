package com.felipesouza.usuario.controller;

import com.felipesouza.usuario.infrastructure.exceptions.ConflictException;
import com.felipesouza.usuario.infrastructure.exceptions.IllegalArgumentException;
import com.felipesouza.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.felipesouza.usuario.infrastructure.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice       //registra a classe como um manipulador global de exceções
public class GlobalExceptionHandler {

    //Quando uma exceção é lançada em qualquer controller e não é tratada localmente,
    // o Spring procura um metodo anotado com @ExceptionHandler compatível com aquela exceção.

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}