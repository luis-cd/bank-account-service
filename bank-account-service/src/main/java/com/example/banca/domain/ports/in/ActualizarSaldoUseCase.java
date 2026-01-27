package com.example.banca.domain.ports.in;

import com.example.banca.domain.model.CuentaBancaria;

public interface ActualizarSaldoUseCase {

    CuentaBancaria actualizarSaldo(Long idCuenta, double nuevoTotal);
}