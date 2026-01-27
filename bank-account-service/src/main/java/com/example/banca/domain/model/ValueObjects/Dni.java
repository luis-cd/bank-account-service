package com.example.banca.domain.model.ValueObjects;

import java.util.Objects;
import java.util.regex.Pattern;


public final class Dni {

    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Z]$");
    private final String valor;

    private Dni(String valorNormalizado) {
        this.valor = valorNormalizado;
    }

    public static Dni of(String rawValue) {
        String normalizado = normalizar(rawValue);
        validar(normalizado);
        return new Dni(normalizado);
    }

    private static String normalizar(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("DNI inválido: no puede ser null");
        }
        return valor.trim().toUpperCase();
    }

	// El DNI se debe validar en la entrada para sistemas reales
	// Existe un algoritmo para comprobar si la letra de control corresponde a los números
	// No veo necesario implementar eso en un ejemplo tan sencillo 
    private static void validar(String valor) {
        if (valor.isBlank()) {
            throw new IllegalArgumentException("DNI inválido: no puede estar vacío.");
        }

        if (!DNI_PATTERN.matcher(valor).matches()) {
            throw new IllegalArgumentException(
                String.format("DNI inválido: '%s' no cumple el formato.", valor)
            );
        }
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dni dni = (Dni) o;
        return valor.equals(dni.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
