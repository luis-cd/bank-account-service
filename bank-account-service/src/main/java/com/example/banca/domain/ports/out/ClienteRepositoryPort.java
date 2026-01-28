package com.example.banca.domain.ports.out;

import java.util.List;
import java.util.Optional;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.ValueObjects.Dni;

public interface ClienteRepositoryPort {
    Optional<Cliente> findByDni(Dni dni);
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
}