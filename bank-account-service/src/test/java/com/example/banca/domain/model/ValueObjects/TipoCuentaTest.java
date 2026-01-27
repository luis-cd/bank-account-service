package com.example.banca.domain.model.ValueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoCuentaTest {

    @Test
    void deberiaCrearTipoCuentaDesdeString() {
        TipoCuenta tipo = TipoCuenta.fromString("premium");

        assertEquals(TipoCuenta.PREMIUM, tipo);
    }

    @Test
    void deberiaFallarConTipoInvalido() {
        assertThrows(
            IllegalArgumentException.class,
            () -> TipoCuenta.fromString("VIP_SUPREMO")
        );
    }

    @Test
    void deberiaFallarConNull() {
        assertThrows(
            IllegalArgumentException.class,
            () -> TipoCuenta.fromString(null)
        );
    }
}
