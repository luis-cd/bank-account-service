package com.example.banca.application.services;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepository;
import com.example.banca.infrastructure.persistence.repositories.CuentaBancariaRepository;
import com.example.banca.infrastructure.persistence.repositories.ClienteRepositoryAdapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ClienteServiceIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepo;

    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        // Aquí usamos el Adapter que implementa ClienteRepositoryPort
        ClienteRepositoryAdapter clienteAdapter = new ClienteRepositoryAdapter(clienteRepo);
        clienteService = new ClienteService(clienteAdapter);
    }

    @Test
    void obtenerTodosLosClientesDevuelveDatos() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        assertThat(clientes).isNotEmpty();
        clientes.forEach(c -> assertThat(c.getCuentas()).isNotNull());
    }

    @Test
    void obtenerClientesMayoresDeEdadFiltraCorrectamente() {
        List<Cliente> mayores = clienteService.obtenerMayoresDeEdad();
        assertThat(mayores).allMatch(Cliente::esMayorDeEdad);
    }

    @Test
    void obtenerClientesConCuentaSuperiorADevuelveCorrectamente() {
        double limite = 100000;
        List<Cliente> result = clienteService.obtenerClientesConCuentaSuperiorA(limite);
        assertThat(result).allMatch(c -> c.getTotalDineroEnCuentas() > limite);
    }

    @Test
    void obtenerClientePorDniDevuelveClienteCorrecto() {
        Dni dni = Dni.of("11111111A");
        Cliente cliente = clienteService.obtenerPorDni(dni.getValor());

        assertThat(cliente.getDni()).isEqualTo(dni);
        assertThat(cliente.getCuentas()).isNotEmpty();
    }

    @Test
    void obtenerClientePorDniQueNoExisteLanzaExcepcion() {
        Dni dni = Dni.of("99999999Z");

        try {
            clienteService.obtenerPorDni(dni.getValor());
        } catch (RuntimeException e) {
            assertThat(e).hasMessageContaining("No existe cliente");
        }
    }
}
