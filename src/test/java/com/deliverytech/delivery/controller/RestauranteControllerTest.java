package com.deliverytech.delivery.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deliverytech.delivery.model.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private RestauranteController restauranteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController).build();
    }

    @Test
    void deveListarRestaurantes() throws Exception {
        when(restauranteRepository.findAll())
                .thenReturn(List.of(
                        new Restaurante(1L, "Pizzaria", "Italiana", "1234", BigDecimal.valueOf(5.0), 30, true, null)
                ));

        mockMvc.perform(get("/restaurantes"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCadastrarRestaurante() throws Exception {
        Restaurante r = new Restaurante(null, "Sushi Bar", "Japonesa", "4321", BigDecimal.valueOf(10.0), 40, true, null);
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(r);

        mockMvc.perform(post("/restaurantes")
                        .contentType("application/json")
                        .content("{\"nome\":\"Sushi Bar\",\"categoria\":\"Japonesa\",\"telefone\":\"4321\",\"taxaEntrega\":10.0,\"tempoEntregaMinutos\":40}"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFoundParaRestauranteInexistente() throws Exception {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/restaurantes/1"))
                .andExpect(status().isNotFound());
    }
}
