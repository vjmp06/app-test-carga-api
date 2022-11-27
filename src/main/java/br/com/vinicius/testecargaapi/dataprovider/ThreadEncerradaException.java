package br.com.vinicius.testecargaapi.dataprovider;

public class ThreadEncerradaException extends RuntimeException {
    private String message;

    public ThreadEncerradaException(String message) {
        super(message);
    }
}
