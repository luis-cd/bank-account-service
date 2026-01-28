package com.example.banca.domain.model.ValueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoCuentaTest {

    @Test
    void crearTipoCuentaStringTest() {
        TipoCuenta tipo = TipoCuenta.fromString("premium");

        assertEquals(TipoCuenta.PREMIUM, tipo);
    }

    @Test
    void tipoInvalidoTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> TipoCuenta.fromString("VIP_SUPREMO")
        );
    }

    @Test
    void tipoCuentaNullTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> TipoCuenta.fromString(null)
        );
    }
}
