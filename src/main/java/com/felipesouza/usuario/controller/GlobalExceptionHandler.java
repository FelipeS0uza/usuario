package com.felipesouza.usuario.controller;

import com.felipesouza.usuario.infrastructure.exceptions.ConflictException;
import com.felipesouza.usuario.infrastructure.exceptions.IllegalArgumentException;
import com.felipesouza.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.felipesouza.usuario.infrastructure.exceptions.UnauthorizedException;
import com.felipesouza.usuario.infrastructure.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice       //registra a classe como um manipulador global de exceções
public class GlobalExceptionHandler {

    //Quando uma exceção é lançada em qualquer controller e não é tratada localmente,
    // o Spring procura um metodo anotado com @ExceptionHandler compatível com aquela exceção.

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "Not Found",
                request.getRequestURI()
                ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                "Not Found",
                request.getRequestURI()
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO buildError(int status, String error, String mensagem, String path) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .message(mensagem)
                .path(path)
                .build();
    }
}