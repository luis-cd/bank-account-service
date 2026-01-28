package com.example.banca.domain.ports.in;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.ValueObjects.Dni;

public interface ObtenerClientePorDniUseCase {
    Cliente obtenerPorDni(String dni);
}