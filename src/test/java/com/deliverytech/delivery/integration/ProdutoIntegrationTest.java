package com.deliverytech.delivery.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deliverytech.delivery.DeliveryApplication;
import com.deliverytech.delivery.model.Produto;
import com.deliverytech.delivery.repository.ProdutoRepository;

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
class ProdutoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll(); // limpa os dados antes de cada teste
    }

    @Test
    void deveCadastrarProduto() throws Exception {
        String jsonProduto = "{\"nome\":\"Sushi\",\"categoria\":\"Comida\",\"descricao\":\"Sushi de salmão\",\"preco\":50.0}";

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Sushi"))
                .andExpect(jsonPath("$.categoria").value("Comida"));
    }

    @Test
    void deveListarProdutos() throws Exception {
        Produto p = new Produto(null, "Pizza", "Comida", "Pizza de queijo", BigDecimal.valueOf(30), true, null);
        produtoRepository.save(p);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Pizza"))
                .andExpect(jsonPath("$[0].categoria").value("Comida"));
    }

    @Test
    void deveRetornarNotFoundParaProdutoInexistente() throws Exception {
        mockMvc.perform(get("/produtos/999"))
                .andExpect(status().isNotFound());
    }
}
