package com.example.banca.domain.ports.in;

import com.example.banca.domain.model.Cliente;
import java.util.List;

public interface ObtenerClientesConCuentaSuperiorAUseCase {
    List<Cliente> obtenerClientesConCuentaSuperiorA(double cantidad);
}