package com.example.banca.infrastructure.persistence.repositories;

import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	void testGuardarClienteConCuentas_bidirectional() {
		ClienteEntity cliente = ClienteEntity.builder()
			.dni("99999999Z")
			.nombre("Test")
			.apellido1("Prueba")
			.apellido2("Unitario")
			.fechaNacimiento(LocalDate.of(1990, 1, 1))
			.build();

		CuentaBancariaEntity cuenta1 = CuentaBancariaEntity.builder()
			.tipoCuenta("PREMIUM")
			.total(10000.0)
			.build();

		CuentaBancariaEntity cuenta2 = CuentaBancariaEntity.builder()
			.tipoCuenta("NORMAL")
			.total(5000.0)
			.build();

		cliente.addCuenta(cuenta1);
		cliente.addCuenta(cuenta2);

		clienteRepository.save(cliente);

		ClienteEntity savedCliente = clienteRepository.findById("99999999Z").orElseThrow();

		assertThat(savedCliente.getCuentas()).hasSize(2);
		double total = savedCliente.getCuentas().stream()
			.mapToDouble(CuentaBancariaEntity::getTotal)
			.sum();
		assertThat(total).isEqualTo(15000.0);

		savedCliente.getCuentas().forEach(c -> assertThat(c.getCliente()).isSameAs(savedCliente));
	}

	@Test
	void testFindClientesConTotalMayorQue() {
		ClienteEntity cliente = ClienteEntity.builder()
			.dni("77777777Y")
			.nombre("Agregado")
			.apellido1("Test")
			.fechaNacimiento(LocalDate.of(1985,1,1))
			.build();

		cliente.addCuenta(CuentaBancariaEntity.builder().tipoCuenta("PREMIUM").total(20000.0).build());
		cliente.addCuenta(CuentaBancariaEntity.builder().tipoCuenta("NORMAL").total(30000.0).build());

		clienteRepository.save(cliente);

		List<ClienteEntity> result = clienteRepository.findClientesConTotalMayorQue(40000.0);
		assertThat(result).extracting("dni").contains("77777777Y");

		List<ClienteEntity> emptyResult = clienteRepository.findClientesConTotalMayorQue(60000.0);
		assertThat(emptyResult).extracting("dni").doesNotContain("77777777Y");
	}

	@Test
	void testFindByFechaNacimientoBefore() {
		ClienteEntity cliente = ClienteEntity.builder()
			.dni("66666666X")
			.nombre("Fecha")
			.apellido1("Test")
			.fechaNacimiento(LocalDate.of(2000,1,1))
			.build();

		clienteRepository.save(cliente);

		List<ClienteEntity> result = clienteRepository.findByFechaNacimientoBefore(LocalDate.of(2010,1,1));
		assertThat(result).extracting("dni").contains("66666666X");
	}
}
