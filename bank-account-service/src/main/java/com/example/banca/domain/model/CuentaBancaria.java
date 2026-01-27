package com.example.banca.domain.model;

import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;

import java.util.Objects;

public class CuentaBancaria {

    private final Long id;
    private final double total; // en un sistema real: BigDecimal por precisión
    private final Dni dniCliente;
    private final TipoCuenta tipoCuenta;

    public CuentaBancaria(
        Long id,
        double total,
        Dni dniCliente,
        TipoCuenta tipoCuenta
    ) {
        this.id = id; // puede ser null si no está persistida
        this.total = total;
        this.dniCliente = Objects.requireNonNull(dniCliente, "dniCliente no puede ser null");
        this.tipoCuenta = Objects.requireNonNull(tipoCuenta, "tipoCuenta no puede ser null");
    }

    // Factory Method
    public static CuentaBancaria crear(
        double totalInicial,
        String dniCliente,
        String tipoCuentaStr
    ) {
        return new CuentaBancaria(
            null,
            totalInicial,
            Dni.of(dniCliente),
            TipoCuenta.fromString(tipoCuentaStr)
        );
    }

    public CuentaBancaria actualizarTotal(double nuevoTotal) {
        return new CuentaBancaria(
            this.id,
            nuevoTotal,
            this.dniCliente,
            this.tipoCuenta
        );
    }

    public boolean esTipo(TipoCuenta tipo) {
        return this.tipoCuenta == tipo;
    }

    public double getTotal() {
        return total;
    }

    public Long getId() {
        return id;
    }

    public Dni getDniCliente() {
        return dniCliente;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaBancaria)) return false;
        CuentaBancaria that = (CuentaBancaria) o;
        return Double.compare(that.total, total) == 0
            && Objects.equals(id, that.id)
            && Objects.equals(dniCliente, that.dniCliente)
            && tipoCuenta == that.tipoCuenta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dniCliente, tipoCuenta, total);
    }
}
