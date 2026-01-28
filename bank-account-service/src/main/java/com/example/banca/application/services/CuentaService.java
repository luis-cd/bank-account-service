package com.example.banca.application.services;

import com.example.banca.domain.model.CuentaBancaria;
import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import com.example.banca.domain.ports.out.CuentaRepositoryPort;
import com.example.banca.domain.ports.out.ClienteRepositoryPort;
import com.example.banca.domain.ports.in.CrearCuentaUseCase;
import com.example.banca.domain.ports.in.ActualizarSaldoUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CuentaService implements CrearCuentaUseCase, ActualizarSaldoUseCase {

    private final CuentaRepositoryPort cuentaRepository;
    private final ClienteRepositoryPort clienteRepository;

    @Override
    public CuentaBancaria crearCuenta(Dni dniCliente, TipoCuenta tipoCuenta, double total) {
        // Buscar cliente existente o crear uno nuevo parcialmente
        Cliente cliente = clienteRepository.findByDni(dniCliente)
            .orElseGet(() -> Cliente.crearParcial(dniCliente));

        CuentaBancaria cuenta = new CuentaBancaria(null, total, dniCliente, tipoCuenta);
        cliente.agregarCuenta(cuenta);

        Cliente clienteGuardado = clienteRepository.save(cliente);
    
        return clienteGuardado.getCuentas()
            .stream()
            .filter(c -> c.getTipoCuenta().equals(tipoCuenta)
                    && c.getTotal() == total)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No se pudo persistir la cuenta"));
    }

    @Override
    public void actualizarSaldo(Long idCuenta, double nuevoTotal) {
        // Llamamos directamente al adapter que ejecuta UPDATE en la DB
        cuentaRepository.actualizarSaldo(idCuenta, nuevoTotal);
    }
}
