package com.example.banca.domain.model.Exceptions;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(String dni) {
        super("No existe cliente : " + dni);
    }
}