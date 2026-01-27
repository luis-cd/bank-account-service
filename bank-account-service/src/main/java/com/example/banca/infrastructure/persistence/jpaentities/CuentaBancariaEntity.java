package com.example.banca.infrastructure.persistence.jpaentities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.banca.infrastructure.persistence.jpaentities.ClienteEntity;

@Entity
@Table(name = "cuentas_bancarias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaBancariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_cliente", nullable = false)
    private ClienteEntity cliente;
}
