package com.deliverytech.delivery.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deliverytech.delivery.model.Produto;
import com.deliverytech.delivery.repository.ProdutoRepository;

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

class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void deveListarProdutos() throws Exception {
        when(produtoRepository.findAll())
                .thenReturn(List.of(
                        new Produto(1L, "Pizza", "Comida", "Pizza de queijo", BigDecimal.valueOf(30), true, null)
                ));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCadastrarProduto() throws Exception {
        Produto p = new Produto(null, "Sushi", "Comida", "Sushi de salmão", BigDecimal.valueOf(50), true, null);
        when(produtoRepository.save(any(Produto.class))).thenReturn(p);

        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content("{\"nome\":\"Sushi\",\"categoria\":\"Comida\",\"descricao\":\"Sushi de salmão\",\"preco\":50.0}"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFoundParaProdutoInexistente() throws Exception {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isNotFound());
    }
}
