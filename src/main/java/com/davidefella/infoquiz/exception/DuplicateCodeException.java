package com.davidefella.infoquiz.exception;

/* Eccezione custom che si basa sul codice duplicato di un model */
public class DuplicateCodeException extends RuntimeException {
    public DuplicateCodeException(String message) {
        super(message);
    }
}