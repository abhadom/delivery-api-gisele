package com.deliverytech.delivery.service;

import com.deliverytech.delivery.model.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.impl.RestauranteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestauranteServiceImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = Restaurante.builder()
                .id(1L)
                .nome("Pizzaria Napoli")
                .categoria("Italiana")
                .telefone("(11) 98765-4321")
                .taxaEntrega(new BigDecimal("7.50"))
                .tempoEntregaMinutos(40)
                .ativo(true)
                .build();
    }

    @Test
    void deveCadastrarRestaurante() {
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante salvo = restauranteService.cadastrar(restaurante);

        assertEquals("Pizzaria Napoli", salvo.getNome());
        verify(restauranteRepository, times(1)).save(restaurante);
    }

    @Test
    void deveAtualizarRestaurante() {
        Restaurante atualizado = Restaurante.builder()
                .nome("Pizzaria Napoli Atualizada")
                .categoria("Italiana")
                .telefone("(11) 98765-4321")
                .taxaEntrega(new BigDecimal("8.50"))
                .tempoEntregaMinutos(45)
                .build();

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(atualizado);

        Restaurante resultado = restauranteService.atualizar(1L, atualizado);

        assertEquals("Pizzaria Napoli Atualizada", resultado.getNome());
        assertEquals(new BigDecimal("8.50"), resultado.getTaxaEntrega());
    }

    @Test
    void deveListarTodos() {
        when(restauranteRepository.findAll()).thenReturn(List.of(restaurante));

        var lista = restauranteService.listarTodos();

        assertEquals(1, lista.size());
        assertEquals("Pizzaria Napoli", lista.get(0).getNome());
    }

    @Test
    void deveBuscarPorId() {
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));

        var resultado = restauranteService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Pizzaria Napoli", resultado.get().getNome());
    }

    @Test
    void deveBuscarPorCategoria() {
        when(restauranteRepository.findByCategoria("Italiana")).thenReturn(List.of(restaurante));

        var lista = restauranteService.buscarPorCategoria("Italiana");

        assertEquals(1, lista.size());
        assertEquals("Italiana", lista.get(0).getCategoria());
    }

    @Test
    void deveVerificarSeNomeExiste() {
        when(restauranteRepository.findByNome("Pizzaria Napoli")).thenReturn(Optional.of(restaurante));

        boolean existe = restauranteService.findByNome("Pizzaria Napoli");

        assertTrue(existe);
    }
}
