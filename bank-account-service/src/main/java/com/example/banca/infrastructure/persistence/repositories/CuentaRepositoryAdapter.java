package com.example.banca.infrastructure.persistence.repositories;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.ports.out.CuentaRepositoryPort;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;
import com.example.banca.infrastructure.persistence.mappers.CuentaBancariaMapper;
import com.example.banca.infrastructure.persistence.repositories.CuentaBancariaRepository;

import lombok.RequiredArgsConstructor;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;

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
        var clienteEntity = clienteRepository.findById(cuenta.getDniCliente().getValor())
                .orElseThrow(() -> new IllegalStateException(
                        "No se puede guardar cuenta, cliente no existe: " + cuenta.getDniCliente().getValor()
                ));

        var cuentaEntity = CuentaBancariaMapper.toEntity(cuenta, clienteEntity);

        clienteEntity.addCuenta(cuentaEntity);

        clienteRepository.save(clienteEntity);

        return CuentaBancariaMapper.toDomain(cuentaEntity);
    }

    @Override
    @Transactional
    public void actualizarSaldo(Long id, double nuevoTotal) {
        cuentaBancariaRepository.actualizarTotal(id, nuevoTotal);
    }
}
