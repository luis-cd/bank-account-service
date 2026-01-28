package com.example.banca.application.services;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.ports.out.CuentaRepositoryPort;

import com.example.banca.application.services.CuentaService;

import com.example.banca.domain.ports.out.ClienteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CuentaServiceTest {

    private CuentaRepositoryPort cuentaRepo;
    private ClienteRepositoryPort clienteRepo;
    private CuentaService cuentaService;

    @BeforeEach
    void setup() {
        cuentaRepo = mock(CuentaRepositoryPort.class);
        clienteRepo = mock(ClienteRepositoryPort.class);
        cuentaService = new CuentaService(cuentaRepo, clienteRepo);
    }

    @Test
    void crearCuentaParaClienteExistente() {
        Dni dni = Dni.of("11111111A");
        Cliente cliente = Cliente.crearParcial(dni);

        when(clienteRepo.findByDni(dni)).thenReturn(Optional.of(cliente));
        when(clienteRepo.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        CuentaBancaria cuenta = cuentaService.crearCuenta(dni, TipoCuenta.NORMAL, 1000);

        assertThat(cuenta.getDniCliente()).isEqualTo(dni);
        assertThat(cuenta.getTipoCuenta()).isEqualTo(TipoCuenta.NORMAL);
        assertThat(cuenta.getTotal()).isEqualTo(1000);

        verify(clienteRepo, times(1)).save(cliente);
    }

    @Test
    void crearCuentaClienteNuevo() {
        Dni dni = Dni.of("22222222B");

        when(clienteRepo.findByDni(dni)).thenReturn(Optional.empty());
        when(clienteRepo.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        CuentaBancaria cuenta = cuentaService.crearCuenta(dni, TipoCuenta.PREMIUM, 5000);

        assertThat(cuenta.getDniCliente()).isEqualTo(dni);
        assertThat(cuenta.getTipoCuenta()).isEqualTo(TipoCuenta.PREMIUM);
        assertThat(cuenta.getTotal()).isEqualTo(5000);

        verify(clienteRepo, times(1)).save(any(Cliente.class));
    }

    @Test
    void actualizarSaldoExistente() {
        Long idCuenta = 1L;

        when(cuentaRepo.actualizarSaldo(idCuenta, 3000.0))
                .thenReturn(true);

        cuentaService.actualizarSaldo(idCuenta, 3000.0);

        verify(cuentaRepo, times(1))
                .actualizarSaldo(idCuenta, 3000.0);

        verify(cuentaRepo, never()).save(any());
        verify(cuentaRepo, never()).findById(any());
    }

    @Test
    void actualizarSaldoCuentaNoExistente() {
        Long idCuenta = 99L;
        when(cuentaRepo.findById(idCuenta)).thenReturn(Optional.empty());

        try {
            cuentaService.actualizarSaldo(idCuenta, 1000);
        } catch (RuntimeException e) {
            assertThat(e).hasMessageContaining("Cuenta no encontrada");
        }

        verify(cuentaRepo, never()).save(any());
    }
}