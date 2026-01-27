package com.example.banca.infrastructure.persistence.mappers;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class ClienteMapper {

    private ClienteMapper() {}

    // Entity -> Domain
    public static Cliente toDomain(ClienteEntity entity) {
        List<CuentaBancaria> cuentas = entity.getCuentas()
            .stream()
            .map(CuentaBancariaMapper::toDomain)
            .collect(Collectors.toList());

        return new Cliente(
            Dni.of(entity.getDni()),
            entity.getNombre(),
            entity.getApellido1(),
            entity.getApellido2(),
            entity.getFechaNacimiento(),
            cuentas
        );
    }

    // Domain -> Entity
    public static ClienteEntity toEntity(Cliente domain) {
        ClienteEntity entity = ClienteEntity.builder()
            .dni(domain.getDni().getValor())
            .nombre(domain.getNombre())
            .apellido1(domain.getApellido1())
            .apellido2(domain.getApellido2())
            .fechaNacimiento(domain.getFechaNacimiento())
            .build();

        domain.getCuentas()
            .stream()
            .map(c -> CuentaBancariaMapper.toEntity(c, entity))
            .forEach(entity::addCuenta);

        return entity;
    }
}
