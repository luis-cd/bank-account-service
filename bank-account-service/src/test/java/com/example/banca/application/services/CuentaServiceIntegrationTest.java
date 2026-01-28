package com.example.banca.application.services;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.infrastructure.persistence.mappers.CuentaBancariaMapper;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;
import com.example.banca.infrastructure.persistence.repositories.CuentaBancariaRepository;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepositoryAdapter;
import com.example.banca.infrastructure.persistence.repositories.CuentaRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CuentaServiceIntegrationTest {

    @Autowired
    private CuentaBancariaRepository cuentaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    private CuentaService cuentaService;

    @BeforeEach
    void setup() {
        // Adaptadores reales para JPA
        CuentaRepositoryAdapter cuentaAdapter = new CuentaRepositoryAdapter(cuentaRepo, clienteRepo);
        ClienteRepositoryAdapter clienteAdapter = new ClienteRepositoryAdapter(clienteRepo);
        cuentaService = new CuentaService(cuentaAdapter, clienteAdapter);
    }

    @Test
    void crearCuentaParaClienteExistentePersisteCorrectamente() {
        Dni dni = Dni.of("11111111A");

        // Creamos la cuenta
        CuentaBancaria cuenta = cuentaService.crearCuenta(dni, TipoCuenta.NORMAL, 1500);

        // La cuenta tiene que tener id
        assertThat(cuenta.getId()).isNotNull();
        assertThat(cuenta.getDniCliente()).isEqualTo(dni);

        // Revisamos en la DB
        var cuentas = cuentaRepo.findByClienteDni(dni.getValor());
        assertThat(cuentas).extracting("id").contains(cuenta.getId());
    }

    @Test
    void crearCuentaParaClienteNuevoPersisteCorrectamente() {
        Dni dni = Dni.of("99999977Z");

        // Creamos cuenta para cliente nuevo
        CuentaBancaria cuenta = cuentaService.crearCuenta(dni, TipoCuenta.PREMIUM, 5000);

        // La cuenta tiene que tener id
        assertThat(cuenta.getId()).isNotNull();
        assertThat(cuenta.getDniCliente()).isEqualTo(dni);

        // Cliente creado automáticamente
        var cliente = clienteRepo.findById(dni.getValor()).orElseThrow();
        assertThat(cliente.getDni()).isEqualTo(dni.getValor());
        assertThat(cliente.getCuentas()).hasSize(1);
        assertThat(cliente.getCuentas().get(0).getId()).isEqualTo(cuenta.getId());
    }

    @Test
    void actualizarSaldoPersistencia() {
        Dni dni = Dni.of("11111111A");

        // Obtenemos una cuenta existente de la DB
        var cuentas = cuentaRepo.findByClienteDni(dni.getValor());
        CuentaBancaria cuenta = CuentaBancariaMapper.toDomain(cuentas.get(0));

        // Actualizamos saldo
        cuentaService.actualizarSaldo(cuenta.getId(), 9999.0);

        // Verificamos en la DB
        var updatedCuenta = cuentaRepo.findById(cuenta.getId()).orElseThrow();
        assertThat(updatedCuenta.getTotal()).isEqualTo(9999.0);
    }
}
