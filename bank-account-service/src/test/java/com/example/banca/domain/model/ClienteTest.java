package com.example.banca.domain.model;

import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void detectarClienteMayorDeEdadTest() {
        Cliente cliente = Cliente.crearCompleto(
            Dni.of("11111111A"),
            "Juan",
            "Pérez",
            null,
            LocalDate.now().minusYears(30),
            List.of() // sin cuentas
        );

        assertTrue(cliente.esMayorDeEdad());
    }

    @Test
    void sumarTotalDeLasCuentasTest() {
        CuentaBancaria c1 = CuentaBancaria.crear(1000, "11111111A", "NORMAL");
        CuentaBancaria c2 = CuentaBancaria.crear(500, "11111111A", "PREMIUM");

        Cliente cliente = Cliente.crearCompleto(
            Dni.of("11111111A"),
            "Juan",
            "Pérez",
            null,
            LocalDate.now().minusYears(30),
            List.of(c1, c2)
        );

        assertEquals(1500.0, cliente.getTotalDineroEnCuentas());
    }
}
