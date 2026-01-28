package com.example.banca.domain.model;

import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaTest {

    @Test
    void crearCuentaTest() {
        CuentaBancaria cuenta = CuentaBancaria.crear(
            1000.0,
            "11111111A",
            "NORMAL"
        );

        assertEquals(1000.0, cuenta.getTotal());
        assertEquals(TipoCuenta.NORMAL, cuenta.getTipoCuenta());
        assertEquals(Dni.of("11111111A"), cuenta.getDniCliente());
        assertNull(cuenta.getId());
    }

    @Test
    void actualizarTotalConNuevaInstanciaTest() {
        CuentaBancaria original = CuentaBancaria.crear(
            1000.0,
            "11111111A",
            "NORMAL"
        );

        CuentaBancaria actualizada = original.actualizarTotal(2000.0);

        assertEquals(1000.0, original.getTotal());
        assertEquals(2000.0, actualizada.getTotal());
        assertEquals(original.getDniCliente(), actualizada.getDniCliente());
    }
}
