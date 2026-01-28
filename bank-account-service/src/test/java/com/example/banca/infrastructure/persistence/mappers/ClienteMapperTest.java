package com.example.banca.infrastructure.persistence.mappers;

import com.example.banca.domain.model.Cliente;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ClienteMapperTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void mapearEntityToDomain() {
        ClienteEntity entity = clienteRepository
            .findById("11111111A")
            .orElseThrow();

        Cliente clienteDomain = ClienteMapper.toDomain(entity);

        assertThat(clienteDomain.getDni().getValor()).isEqualTo("11111111A");
        assertThat(clienteDomain.getCuentas()).hasSize(2);
    }

    @Test
    void mapearDomainToEntity() {
        ClienteEntity entity = clienteRepository
            .findById("11111111A")
            .orElseThrow();

        Cliente clienteDomain = ClienteMapper.toDomain(entity);

        ClienteEntity newEntity = ClienteMapper.toEntity(clienteDomain);

        assertThat(newEntity.getDni()).isEqualTo("11111111A");
        assertThat(newEntity.getCuentas()).hasSize(2);
    }

    @Test
    void mapearClienteSinCompletar() {
        // Estos se dan cuando se crea cuenta sin cliente pre-existente
        ClienteEntity partialEntity = ClienteEntity.builder()
            .dni("66666666F")
            .build(); // sin nombre, apellido1 ni fecha

        Cliente partialDomain = ClienteMapper.toDomain(partialEntity);

        assertThat(partialDomain.getDni().getValor()).isEqualTo("66666666F");
        assertThat(partialDomain.estaCompleto()).isFalse();

        ClienteEntity mappedBack = ClienteMapper.toEntity(partialDomain);
        assertThat(mappedBack.getDni()).isEqualTo("66666666F");
        assertThat(mappedBack.getNombre()).isNull();
    }
}
