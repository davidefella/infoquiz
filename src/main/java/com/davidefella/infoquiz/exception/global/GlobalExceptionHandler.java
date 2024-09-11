package com.davidefella.infoquiz.exception.global;

import com.davidefella.infoquiz.exception.DuplicateUUIDException;
import com.davidefella.infoquiz.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
* @ControllerAdvice: Questa annotazione indica che la classe è un "Advice", ovvero un componente che intercetta le eccezioni lanciate dai controller di tutta l'applicazione. Funziona come un middleware che gestisce le eccezioni in modo centralizzato
* */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUUIDException.class)
    public ResponseEntity<ApiError> handleDuplicateUUIDException(DuplicateUUIDException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}