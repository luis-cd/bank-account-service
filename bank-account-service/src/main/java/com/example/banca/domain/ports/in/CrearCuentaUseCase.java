package com.example.banca.domain.ports.in;

import com.example.banca.domain.model.CuentaBancaria;

public interface CrearCuentaUseCase {

    /**
     * Crea una nueva cuenta bancaria.
     * Si el cliente no existía, se crea automáticamente.
     */
    CuentaBancaria crearCuenta(String dniCliente, String tipoCuenta, double totalInicial);
}
