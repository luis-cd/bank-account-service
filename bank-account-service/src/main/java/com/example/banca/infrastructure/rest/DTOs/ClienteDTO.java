package com.example.banca.infrastructure.rest.DTOs;

import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.model.ValueObjects.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String fechaNacimiento; // ISO yyyy-MM-dd
    private List<CuentaDTO> cuentas;
}
