package com.davidefella.infoquiz.exception.global;

/*
* ApiError è una classe semplice che rappresenta un oggetto di errore personalizzato, è un model
* */
public class ApiError {
    private int status;
    private String message;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}