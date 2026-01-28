package com.example.banca.domain.model;

import com.example.banca.domain.model.ValueObjects.Dni;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Cliente {

    private final Dni dni;
    private final String nombre;
    private final String apellido1;
    private final String apellido2;
    private final LocalDate fechaNacimiento;
    private final List<CuentaBancaria> cuentas;

    private Cliente(
        Dni dni,
        String nombre,
        String apellido1,
        String apellido2,
        LocalDate fechaNacimiento,
        List<CuentaBancaria> cuentas
    ) {
        this.dni = Objects.requireNonNull(dni, "dni no puede ser null");
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        // Ahora la lista es mutable
        this.cuentas = cuentas != null ? new ArrayList<>(cuentas) : new ArrayList<>();
    }

    public static Cliente crearParcial(Dni dni) {
        return new Cliente(dni, null, null, null, null, new ArrayList<>());
    }

    public static Cliente crearCompleto(
        Dni dni,
        String nombre,
        String apellido1,
        String apellido2,
        LocalDate fechaNacimiento,
        List<CuentaBancaria> cuentas
    ) {
        Objects.requireNonNull(nombre, "nombre no puede ser null");
        Objects.requireNonNull(apellido1, "apellido1 no puede ser null");
        Objects.requireNonNull(fechaNacimiento, "fechaNacimiento no puede ser null");

        return new Cliente(dni, nombre, apellido1, apellido2, fechaNacimiento, cuentas);
    }

    public boolean estaCompleto() {
        return nombre != null && apellido1 != null && fechaNacimiento != null;
    }

    public boolean esMayorDeEdad() {
        return estaCompleto() &&
            Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    public double getTotalDineroEnCuentas() {
        return cuentas.stream()
            .mapToDouble(CuentaBancaria::getTotal)
            .sum();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        if (cuenta != null) {
            cuentas.add(cuenta);
        }
    }
}
