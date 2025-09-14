package com.deliverytech.delivery.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deliverytech.delivery.DeliveryApplication;
import com.deliverytech.delivery.model.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

@SpringBootTest(classes = DeliveryApplication.class)
@AutoConfigureMockMvc
class RestauranteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    void setUp() {
        restauranteRepository.deleteAll();
    }

    @Test
    void deveCadastrarRestaurante() throws Exception {
        String jsonRestaurante = "{" +
                "\"nome\":\"Pizzaria Central\"," +
                "\"categoria\":\"Comida\"," +
                "\"telefone\":\"12345678\"," +
                "\"taxaEntrega\":5.0," +
                "\"tempoEntregaMinutos\":30" +
                "}";

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRestaurante))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pizzaria Central"))
                .andExpect(jsonPath("$.categoria").value("Comida"));
    }

    @Test
    void deveListarRestaurantes() throws Exception {
        Restaurante r = new Restaurante(null, "Burger King", "Fast Food", "12345678",
                BigDecimal.valueOf(10), 25, true, null);
        restauranteRepository.save(r);

        mockMvc.perform(get("/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Burger King"))
                .andExpect(jsonPath("$[0].categoria").value("Fast Food"));
    }

    @Test
    void deveRetornarNotFoundParaRestauranteInexistente() throws Exception {
        mockMvc.perform(get("/restaurantes/999"))
                .andExpect(status().isNotFound());
    }
}
