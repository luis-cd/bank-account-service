package com.example.banca.application.services;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.domain.ports.out.ClienteRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    private ClienteRepositoryPort clienteRepo;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        // Solo necesitamos el ClienteRepositoryPort
        clienteRepo = mock(ClienteRepositoryPort.class);
        clienteService = new ClienteService(clienteRepo);
    }

    @Test
    void obtenerClientesDevuelveLista() {
        Cliente c1 = Cliente.crearCompleto(
            Dni.of("11111111A"), "Juan", "Pérez", null,
            LocalDate.of(1980, 1, 1),
            List.of()
        );

        when(clienteRepo.findAll()).thenReturn(List.of(c1));

        List<Cliente> result = clienteService.obtenerTodos();

        assertThat(result).hasSize(1).contains(c1);
        verify(clienteRepo).findAll();
    }

    @Test
    void obtenerClientesMayoresDeEdadFiltraCorrectamente() {
        Cliente mayor = Cliente.crearCompleto(
            Dni.of("11111111A"), "Juan", "Pérez", null,
            LocalDate.now().minusYears(30),
            List.of()
        );
        Cliente menor = Cliente.crearCompleto(
            Dni.of("22222222B"), "Ana", "López", null,
            LocalDate.now().minusYears(10),
            List.of()
        );

        when(clienteRepo.findAll()).thenReturn(List.of(mayor, menor));

        List<Cliente> result = clienteService.obtenerMayoresDeEdad();

        assertThat(result).hasSize(1).contains(mayor);
    }

    @Test
    void obtenerClientesConCuentaSuperiorAFiltraCorrectamente() {
        Cliente c1 = Cliente.crearCompleto(
            Dni.of("11111111A"), "Juan", "Pérez", null,
            LocalDate.of(1980,1,1),
            List.of(new CuentaBancaria(1L, 2000, Dni.of("11111111A"), TipoCuenta.NORMAL))
        );

        Cliente c2 = Cliente.crearCompleto(
            Dni.of("22222222B"), "Ana", "López", null,
            LocalDate.of(1990,1,1),
            List.of(new CuentaBancaria(2L, 500, Dni.of("22222222B"), TipoCuenta.PREMIUM))
        );

        when(clienteRepo.findAll()).thenReturn(List.of(c1, c2));

        List<Cliente> result = clienteService.obtenerClientesConCuentaSuperiorA(1000);

        assertThat(result).hasSize(1).contains(c1);
    }

    @Test
    void obtenerClientePorDniDevuelveCliente() {
        Dni dni = Dni.of("11111111A");
        Cliente cliente = Cliente.crearCompleto(
            dni, "Juan", "Pérez", null, LocalDate.of(1980,1,1), List.of()
        );

        when(clienteRepo.findByDni(dni)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.obtenerPorDni(dni.getValor());

        assertThat(result).isEqualTo(cliente);
    }

    @Test
    void obtenerClientePorDniNoExistenteLanzaExcepcion() {
        Dni dni = Dni.of("99999999Z");
        when(clienteRepo.findByDni(dni)).thenReturn(Optional.empty());

        try {
            clienteService.obtenerPorDni(dni.getValor());
        } catch (RuntimeException e) {
            assertThat(e).hasMessageContaining("No existe cliente");
        }
    }
}
