package com.example.banca.application.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.banca.domain.model.Cliente;
import com.example.banca.domain.model.Exceptions.ClienteNoEncontradoException;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.domain.ports.in.ObtenerClientePorDniUseCase;
import com.example.banca.domain.ports.in.ObtenerClientesConCuentaSuperiorAUseCase;
import com.example.banca.domain.ports.in.ObtenerClientesMayoresDeEdadUseCase;
import com.example.banca.domain.ports.in.ObtenerClientesUseCase;
import com.example.banca.domain.ports.out.ClienteRepositoryPort;

@Service
public class ClienteService implements
        ObtenerClientesUseCase,
        ObtenerClientesMayoresDeEdadUseCase,
        ObtenerClientesConCuentaSuperiorAUseCase,
        ObtenerClientePorDniUseCase {

    private final ClienteRepositoryPort clienteRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = Objects.requireNonNull(
            clienteRepository,
            "clienteRepository no puede ser null"
        );
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> obtenerMayoresDeEdad() {
        return clienteRepository.findAll()
            .stream()
            .filter(Cliente::esMayorDeEdad)
            .toList();
    }

    @Override
    public List<Cliente> obtenerClientesConCuentaSuperiorA(double cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        return clienteRepository.findAll()
            .stream()
            .filter(cliente -> cliente.getTotalDineroEnCuentas() > cantidad)
            .toList();
    }

    @Override
    public Cliente obtenerPorDni(String dni) {
        Dni dniVO = Dni.of(dni);

        return clienteRepository.findByDni(dniVO)
            .orElseThrow(() ->
                new ClienteNoEncontradoException(dniVO.getValor())
            );
    }
}
