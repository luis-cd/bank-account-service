package com.example.banca.infrastructure.persistence.mappers;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CuentaBancariaMapperTest {

	@Test
	void mapearEntityToDomain() {
		ClienteEntity clienteEntity = ClienteEntity.builder()
			.dni("11111111A")
			.nombre("Juan")
			.apellido1("Pérez")
			.apellido2("López")
			.build();

		CuentaBancariaEntity entity = CuentaBancariaEntity.builder()
			.id(1L)
			.total(150000.0)
			.tipoCuenta("PREMIUM")
			.cliente(clienteEntity)
			.build();

		CuentaBancaria domain = CuentaBancariaMapper.toDomain(entity);

		assertThat(domain.getId()).isEqualTo(1L);
		assertThat(domain.getTotal()).isEqualTo(150000.0);
		assertThat(domain.getDniCliente()).isEqualTo(Dni.of("11111111A"));
		assertThat(domain.getTipoCuenta()).isEqualTo(TipoCuenta.PREMIUM);
	}

	@Test
	void mapearDomainToEntity() {
		ClienteEntity clienteEntity = ClienteEntity.builder()
			.dni("22222222B")
			.nombre("Ana")
			.apellido1("García")
			.apellido2(null)
			.build();

		CuentaBancaria domain = new CuentaBancaria(
			2L,
			50000.0,
			Dni.of("22222222B"),
			TipoCuenta.NORMAL
		);

		CuentaBancariaEntity entity = CuentaBancariaMapper.toEntity(domain, clienteEntity);

		assertThat(entity.getId()).isEqualTo(2L);
		assertThat(entity.getTotal()).isEqualTo(50000.0);
		assertThat(entity.getTipoCuenta()).isEqualTo("NORMAL");
		assertThat(entity.getCliente()).isSameAs(clienteEntity);
	}
}
