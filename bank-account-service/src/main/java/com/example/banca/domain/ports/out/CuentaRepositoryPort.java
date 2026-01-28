package com.example.banca.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;

public interface CuentaRepositoryPort {
    Optional<CuentaBancaria> findById(Long id);
    List<CuentaBancaria> findByDni(Dni dni);
    CuentaBancaria save(CuentaBancaria cuenta);

    void actualizarSaldo(Long id, double nuevoTotal);
}
