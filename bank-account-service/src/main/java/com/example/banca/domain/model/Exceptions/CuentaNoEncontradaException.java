package com.example.banca.domain.model.Exceptions;

public class CuentaNoEncontradaException extends RuntimeException {
    public CuentaNoEncontradaException(Long id) {
        super("Cuenta no encontrada con id " + id);
    }
}