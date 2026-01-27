package com.example.banca.domain.ports.in;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;

import java.util.List;

public interface ObtenerCuentasClienteUseCase {

    List<CuentaBancaria> obtenerPorCliente(Dni dniCliente);
}
