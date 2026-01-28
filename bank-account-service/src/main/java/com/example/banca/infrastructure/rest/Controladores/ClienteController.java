package com.example.banca.infrastructure.rest.Controladores;

import com.example.banca.application.services.ClienteService;
import com.example.banca.domain.model.ValueObjects.Dni;
import com.example.banca.infrastructure.rest.DTOs.ClienteDTO;
import com.example.banca.infrastructure.rest.mappers.ClienteRestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> obtenerTodos() {
        return clienteService.obtenerTodos()
			.stream()
			.map(ClienteRestMapper::toDTO)
			.collect(Collectors.toList());
    }

    @GetMapping("/mayores-de-edad")
    public List<ClienteDTO> obtenerMayoresDeEdad() {
        return clienteService.obtenerMayoresDeEdad()
			.stream()
			.map(ClienteRestMapper::toDTO)
			.collect(Collectors.toList());
    }

    @GetMapping("/con-cuenta-superior-a/{cantidad}")
    public List<ClienteDTO> obtenerClientesConCuentaSuperiorA(@PathVariable double cantidad) {
        return clienteService.obtenerClientesConCuentaSuperiorA(cantidad)
			.stream()
			.map(ClienteRestMapper::toDTO)
			.collect(Collectors.toList());
    }

    @GetMapping("/{dni}")
    public ClienteDTO obtenerPorDni(@PathVariable String dni) {
        return ClienteRestMapper.toDTO(clienteService.obtenerPorDni(dni));
    }
}
