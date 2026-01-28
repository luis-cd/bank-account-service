package com.example.banca.domain.model.ValueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DniTest {

    @Test
    void crearDniValidadoNormalizadoTest() {
        Dni dni = Dni.of(" 11111111a ");

        assertEquals("11111111A", dni.getValor());
    }

    @Test
    void formatoInvalidoTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Dni.of("ABC")
        );
    }

    @Test
    void dosDnisConMismoValorTest() {
        Dni dni1 = Dni.of("11111111A");
        Dni dni2 = Dni.of("11111111a");

        assertEquals(dni1, dni2);
        assertEquals(dni1.hashCode(), dni2.hashCode());
    }
}
