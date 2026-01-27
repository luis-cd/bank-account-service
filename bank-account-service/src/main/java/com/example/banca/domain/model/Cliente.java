package com.example.banca.domain.model;

import com.example.banca.domain.model.ValueObjects.Dni;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
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

    public Cliente(
            Dni dni,
            String nombre,
            String apellido1,
            String apellido2,
            LocalDate fechaNacimiento,
            List<CuentaBancaria> cuentas
    ) {
        this.dni = Objects.requireNonNull(dni, "dni no puede ser null");
        this.nombre = Objects.requireNonNull(nombre, "nombre no puede ser null");
        this.apellido1 = Objects.requireNonNull(apellido1, "apellido1 no puede ser null");
        this.apellido2 = apellido2; // opcionalmente el apellido2 puede ser null, aunque esto es una decisión de negocio
        this.fechaNacimiento = Objects.requireNonNull(fechaNacimiento, "fechaNacimiento no puede ser null");
        this.cuentas = cuentas == null ? List.of() : List.copyOf(cuentas);
    }

    public static Cliente crear(
        String dni,
        String nombre,
        String apellido1,
        String apellido2,
        LocalDate fechaNacimiento
    ) {
        return new Cliente(
            Dni.of(dni),
            nombre,
            apellido1,
            apellido2,
            fechaNacimiento,
            List.of()
        );
    }

    public boolean esMayorDeEdad() {
        // Nota: en sistemas internacionales la mayoría de edad puede variar
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    public double getTotalDineroEnCuentas() {
        return cuentas.stream()
            .mapToDouble(CuentaBancaria::getTotal)
            .sum();
    }
}
