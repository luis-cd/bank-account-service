package com.example.banca.infrastructure.persistence.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;
import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;
import com.example.banca.infrastructure.persistence.repositories.CuentaBancariaRepository;

@DataJpaTest
class CuentaBancariaRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CuentaBancariaRepository cuentaRepository;


	@Test
	void testEncuentraCuentasByClienteDni() {
		ClienteEntity cliente = ClienteEntity.builder()
			.dni("55555555A")
			.nombre("Cuenta")
			.apellido1("Test")
			.fechaNacimiento(LocalDate.of(1995, 1, 1))
			.build();

		CuentaBancariaEntity cuenta1 = CuentaBancariaEntity.builder()
			.tipoCuenta("NORMAL")
			.total(1000.0)
			.build();

		CuentaBancariaEntity cuenta2 = CuentaBancariaEntity.builder()
			.tipoCuenta("PREMIUM")
			.total(5000.0)
			.build();

		cliente.addCuenta(cuenta1);
		cliente.addCuenta(cuenta2);
		clienteRepository.save(cliente);

		var cuentas = cuentaRepository.findByClienteDni("55555555A");
		assertThat(cuentas).hasSize(2);
	}

	// Esto repite un poco lo que se testea en la capa de application
	@Test
	void testCrearCuentaClienteExiste() {
		ClienteEntity clienteExistente = ClienteEntity.builder()
				.dni("77777777B")
				.nombre("Cuenta3")
				.apellido1("Test")
				.fechaNacimiento(LocalDate.of(1995, 1, 1))
				.build();
		clienteRepository.save(clienteExistente);

		clienteRepository.findById("77777777B").ifPresent(cliente -> {
			CuentaBancariaEntity nuevaCuenta = CuentaBancariaEntity.builder()
					.tipoCuenta("PREMIUM")
					.total(12000.0)
					.build();

			cliente.addCuenta(nuevaCuenta); // Mantener la relación bidireccional
			cuentaRepository.save(nuevaCuenta);
		});

		var cuentas = cuentaRepository.findByClienteDni("77777777B");
		assertThat(cuentas).hasSize(1);
		assertThat(cuentas.get(0).getTotal()).isEqualTo(12000.0);
	}

	@Test
	void testCrearCuentaConClienteParcial() {
		String dni = "88888888B";

		// Asegurarse de que no existe
		assertThat(clienteRepository.findById(dni)).isEmpty();

		// Crear cliente parcial (solo DNI)
		ClienteEntity nuevoCliente = ClienteEntity.builder()
				.dni(dni)
				.build();

		CuentaBancariaEntity nuevaCuenta = CuentaBancariaEntity.builder()
				.tipoCuenta("NORMAL")
				.total(5000.0)
				.build();

		// Asociar cuenta
		nuevoCliente.addCuenta(nuevaCuenta);

		// Guardar cliente (cascade guarda la cuenta)
		clienteRepository.save(nuevoCliente);

		// Verificar
		var clienteGuardado = clienteRepository.findById(dni).orElseThrow();
		var cuentas = cuentaRepository.findByClienteDni(dni);

		assertThat(clienteGuardado.getCuentas()).hasSize(1);
		assertThat(cuentas).hasSize(1);
		assertThat(cuentas.get(0).getTotal()).isEqualTo(5000.0);
		assertThat(clienteGuardado.getNombre()).isNull();
		assertThat(clienteGuardado.getApellido1()).isNull();
		assertThat(clienteGuardado.getFechaNacimiento()).isNull();
	}
}
