package com.example.banca.infrastructure.persistence.repositories;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.ports.out.ClienteRepositoryPort;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;
import com.example.banca.infrastructure.persistence.mappers.ClienteMapper;
import com.example.banca.infrastructure.persistence.mappers.CuentaBancariaMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = ClienteMapper.toEntity(cliente);
        ClienteEntity saved = clienteRepository.save(entity);
        Cliente domainSaved = ClienteMapper.toDomain(saved);
        return domainSaved;
    }

    @Override
    public Optional<Cliente> findByDni(Dni dni) {
        return clienteRepository.findByDni(dni.getValor())
            .map(ClienteMapper::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll()
            .stream()
            .map(ClienteMapper::toDomain)
            .toList();
    }
}
