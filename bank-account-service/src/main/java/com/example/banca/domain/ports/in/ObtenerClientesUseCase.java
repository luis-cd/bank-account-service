package com.example.banca.domain.ports.in;

import java.util.List;

import com.example.banca.domain.model.Cliente;

public interface ObtenerClientesUseCase {
    List<Cliente> obtenerTodos();
}
