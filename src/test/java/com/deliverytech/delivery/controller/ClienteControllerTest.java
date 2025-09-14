package com.deliverytech.delivery.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deliverytech.delivery.model.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void deveListarClientes() throws Exception {
        when(clienteRepository.findAll())
                .thenReturn(List.of(new Cliente(1L, "Ana", "ana@email.com", true, null)));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFoundParaClienteInexistente() throws Exception {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isNotFound());
    }
}
