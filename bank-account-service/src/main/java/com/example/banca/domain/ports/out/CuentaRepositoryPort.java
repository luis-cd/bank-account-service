package com.example.banca.domain.ports.out;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;

import java.util.List;
import java.util.Optional;

public interface CuentaRepositoryPort {

    CuentaBancaria save(CuentaBancaria cuenta);

    Optional<CuentaBancaria> findById(Long id);

    List<CuentaBancaria> findByDniCliente(Dni dniCliente);
}