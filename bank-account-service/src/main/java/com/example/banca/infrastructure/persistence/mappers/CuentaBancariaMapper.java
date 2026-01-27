package com.example.banca.infrastructure.persistence.mappers;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;

public final class CuentaBancariaMapper {

    private CuentaBancariaMapper() {}

    // Entity -> Domain
    public static CuentaBancaria toDomain(CuentaBancariaEntity entity) {
        return new CuentaBancaria(
            entity.getId(),
            entity.getTotal(),
            Dni.of(entity.getCliente().getDni()),
            TipoCuenta.fromString(entity.getTipoCuenta())
        );
    }

    // Domain -> Entity
    public static CuentaBancariaEntity toEntity(CuentaBancaria domain, ClienteEntity clienteEntity) {
        CuentaBancariaEntity entity = CuentaBancariaEntity.builder()
            .id(domain.getId())
            .total(domain.getTotal())
            .tipoCuenta(domain.getTipoCuenta().name()) // almacenamos como String en DB
            .cliente(clienteEntity)
            .build();

        return entity;
    }
}
