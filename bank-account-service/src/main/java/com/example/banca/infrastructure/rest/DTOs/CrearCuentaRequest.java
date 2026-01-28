package com.example.banca.infrastructure.rest.DTOs;

import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import lombok.Data;

@Data
public class CrearCuentaRequest {
    private String dniCliente;
    private TipoCuenta tipoCuenta;
    private double total;
}
