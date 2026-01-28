package com.example.banca.infrastructure.persistence.repositories;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.ports.out.CuentaRepositoryPort;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;
import com.example.banca.infrastructure.persistence.mappers.CuentaBancariaMapper;
import com.example.banca.infrastructure.persistence.repositories.CuentaBancariaRepository;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;
import com.example.banca.domain.model.Exceptions.ClienteNoEncontradoException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CuentaRepositoryAdapter implements CuentaRepositoryPort {

    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public Optional<CuentaBancaria> findById(Long id) {
        return cuentaBancariaRepository.findById(id)
            .map(CuentaBancariaMapper::toDomain);
    }

    @Override
    public List<CuentaBancaria> findByDni(Dni dni) {
        return cuentaBancariaRepository.findByClienteDni(dni.getValor())
            .stream()
            .map(CuentaBancariaMapper::toDomain)
            .collect(Collectors.toCollection(ArrayList::new)); // lista mutable
    }

    @Override
    @Transactional
    public CuentaBancaria save(CuentaBancaria cuenta) {
        // Verificar que exista el cliente
        ClienteEntity clienteEntity = clienteRepository.findById(cuenta.getDniCliente().getValor())
            .orElseThrow(() -> new ClienteNoEncontradoException(cuenta.getDniCliente().getValor()));

        // Mapear y persistir la cuenta
        CuentaBancariaEntity cuentaEntity = CuentaBancariaMapper.toEntity(cuenta, clienteEntity);

        // Asociar la cuenta al cliente
        clienteEntity.addCuenta(cuentaEntity);
        clienteRepository.save(clienteEntity);

        return CuentaBancariaMapper.toDomain(cuentaEntity);
    }

    @Override
    @Transactional
    public boolean actualizarSaldo(Long id, double nuevoTotal) {
        int filasActualizadas = cuentaBancariaRepository.actualizarTotal(id, nuevoTotal);
        return filasActualizadas > 0;
    }
}
