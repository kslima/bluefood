package com.kslima.bluefood.application.service;

public class PagamentoException extends Exception{

    public PagamentoException() {
    }

    public PagamentoException(String message) {
        super(message);
    }

    public PagamentoException(String message, Throwable cause) {
        super(message, cause);
    }

}
