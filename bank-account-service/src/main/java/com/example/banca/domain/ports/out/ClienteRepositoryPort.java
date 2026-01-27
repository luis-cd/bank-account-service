package com.example.banca.domain.ports.out;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.ValueObjects.Dni;

import java.util.Optional;

public interface ClienteRepositoryPort {

    Cliente save(Cliente cliente);

    Optional<Cliente> findByDni(Dni dni);
}