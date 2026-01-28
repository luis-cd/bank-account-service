package com.example.banca.infrastructure.rest;

import com.example.banca.BankAccountServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BankAccountServiceApplication.class)
@AutoConfigureMockMvc
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Opcional: cargar datos iniciales si no usas data.sql
    }

    @Test
    void testObtenerTodosClientes() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testObtenerClientesMayoresDeEdad() throws Exception {
        mockMvc.perform(get("/clientes/mayores-de-edad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testObtenerClientesConCuentaSuperiorA() throws Exception {
        double cantidad = 1000;
        mockMvc.perform(get("/clientes/con-cuenta-superior-a/{cantidad}", cantidad))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testObtenerClientePorDniExistente() throws Exception {
        String dni = "11111111A";
        mockMvc.perform(get("/clientes/{dni}", dni))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value(dni))
                .andExpect(jsonPath("$.cuentas").isArray());
    }

    @Test
    void testObtenerClientePorDniInexistente() throws Exception {
        String dni = "99999999Z";
        mockMvc.perform(get("/clientes/{dni}", dni))
                .andExpect(status().isNotFound()); // ahora espera 404
    }

    @Test
    void testCrearCuentaParaClienteExistente() throws Exception {
        String requestJson = """
            {
                "dniCliente": "11111111A",
                "tipoCuenta": "NORMAL",
                "total": 50000
            }
        """;

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dniCliente").value("11111111A"))
                .andExpect(jsonPath("$.tipoCuenta").value("NORMAL"))
                .andExpect(jsonPath("$.total").value(50000));
    }

    @Test
    void testCrearCuentaParaClienteNuevo() throws Exception {
        String requestJson = """
            {
                "dniCliente": "22222222B",
                "tipoCuenta": "PREMIUM",
                "total": 100000
            }
        """;

        mockMvc.perform(post("/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dniCliente").value("22222222B"))
                .andExpect(jsonPath("$.tipoCuenta").value("PREMIUM"))
                .andExpect(jsonPath("$.total").value(100000));
    }

    @Test
    void testActualizarSaldoCuentaExistente() throws Exception {
        Long idCuenta = 1L;
        String requestJson = """
            {
                "total": 180000
            }
        """;

        mockMvc.perform(put("/cuentas/{idCuenta}", idCuenta)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarSaldoCuentaInexistente() throws Exception {
        Long idCuenta = 999L;
        String requestJson = """
            {
                "total": 5000
            }
        """;

        mockMvc.perform(put("/cuentas/{idCuenta}", idCuenta)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound()); // ahora espera 404
    }

    @Test
    void testObtenerClientePorDniFormatoInvalido() throws Exception {
        mockMvc.perform(get("/clientes/{dni}", "ABC"))
                .andExpect(status().isBadRequest());
    }

}
