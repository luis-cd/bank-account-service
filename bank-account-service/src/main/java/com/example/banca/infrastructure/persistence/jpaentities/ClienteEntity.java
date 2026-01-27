package com.example.banca.infrastructure.persistence.jpaentities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.banca.infrastructure.persistence.jpaentities.CuentaBancariaEntity;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ClienteEntity {

    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido1", nullable = false)
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // Relación con cuentas
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CuentaBancariaEntity> cuentas = new ArrayList<>();

    public void addCuenta(CuentaBancariaEntity cuenta) {
        cuentas.add(cuenta);
        cuenta.setCliente(this);
    }
}