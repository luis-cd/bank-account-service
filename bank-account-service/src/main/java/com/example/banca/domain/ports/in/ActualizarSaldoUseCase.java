package com.example.banca.domain.ports.in;

public interface ActualizarSaldoUseCase {
    void actualizarSaldo(Long idCuenta, double nuevoTotal);
}
