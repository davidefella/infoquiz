package com.davidefella.infoquiz.exception;

/* Eccezione custom che si basa sul codice duplicato di un model */
public class DuplicateUUIDException extends RuntimeException {
    public DuplicateUUIDException(String message) {
        super(message);
    }
}