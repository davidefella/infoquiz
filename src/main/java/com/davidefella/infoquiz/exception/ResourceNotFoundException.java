package com.davidefella.infoquiz.exception;

/* Eccezione custom per risorse non trovate */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
