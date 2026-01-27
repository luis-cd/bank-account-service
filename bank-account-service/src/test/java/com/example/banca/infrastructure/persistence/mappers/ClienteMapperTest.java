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
    void shouldMapEntityToDomain() {
        ClienteEntity entity = clienteRepository
            .findById("11111111A")
            .orElseThrow();

        Cliente clienteDomain = ClienteMapper.toDomain(entity);

        assertThat(clienteDomain.getDni().getValor()).isEqualTo("11111111A");
        assertThat(clienteDomain.getCuentas()).hasSize(2);
    }

    @Test
    void shouldMapDomainToEntity() {
        ClienteEntity entity = clienteRepository
            .findById("11111111A")
            .orElseThrow();

        Cliente clienteDomain = ClienteMapper.toDomain(entity);

        ClienteEntity newEntity = ClienteMapper.toEntity(clienteDomain);

        assertThat(newEntity.getDni()).isEqualTo("11111111A");
        assertThat(newEntity.getCuentas()).hasSize(2);
    }
}
