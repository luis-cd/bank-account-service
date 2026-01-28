package com.example.banca.domain.ports.in;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.domain.model.ValueObjects.Dni;

public interface CrearCuentaUseCase {
    CuentaBancaria crearCuenta(Dni dniCliente, TipoCuenta tipoCuenta, double total);
}