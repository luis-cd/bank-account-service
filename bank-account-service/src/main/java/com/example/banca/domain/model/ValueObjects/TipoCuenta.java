package com.example.banca.domain.model.ValueObjects;

import java.util.Arrays;

public enum TipoCuenta {
    PREMIUM,
    NORMAL,
    JUNIOR;

    public static TipoCuenta fromString(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El tipo de cuenta no puede ser nulo.");
        }

        String normalizado = valor.trim().toUpperCase();

        return Arrays.stream(values())
            .filter(tipo -> tipo.name().equals(normalizado))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Tipo de cuenta inválido: '" + valor +
                    "'. Valores válidos: " + Arrays.toString(values())
                )
            );
    }
}
