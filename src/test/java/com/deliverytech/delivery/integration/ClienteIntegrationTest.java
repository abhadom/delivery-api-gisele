package com.deliverytech.delivery.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deliverytech.delivery.DeliveryApplication;
import com.deliverytech.delivery.model.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = DeliveryApplication.class)
@AutoConfigureMockMvc
class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        String jsonCliente = "{\"nome\":\"João\",\"email\":\"joao@email.com\"}";

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void deveListarClientes() throws Exception {
        Cliente c = new Cliente(null, "Maria", "maria@email.com", true, null);
        clienteRepository.save(c);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Maria"))
                .andExpect(jsonPath("$[0].email").value("maria@email.com"));
    }

    @Test
    void deveRetornarNotFoundParaClienteInexistente() throws Exception {
        mockMvc.perform(get("/clientes/999"))
                .andExpect(status().isNotFound());
    }
}
