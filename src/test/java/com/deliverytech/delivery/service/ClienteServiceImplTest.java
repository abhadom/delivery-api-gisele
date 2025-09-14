package com.deliverytech.delivery.service;

import com.deliverytech.delivery.model.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = Cliente.builder()
                .id(1L)
                .nome("Carlos Silva")
                .email("carlos@email.com")
                .ativo(true)
                .build();
    }

    @Test
    void deveCadastrarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente salvo = clienteService.cadastrar(cliente);

        assertEquals("Carlos Silva", salvo.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void deveAtualizarCliente() {
        Cliente atualizado = Cliente.builder().nome("Carlos Silva Atualizado").build();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(atualizado);

        Cliente resultado = clienteService.atualizar(1L, atualizado);

        assertEquals("Carlos Silva Atualizado", resultado.getNome());
    }

    @Test
    void deveAtivarDesativarCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        clienteService.ativarDesativar(1L);
        assertFalse(cliente.getAtivo());
    }

    @Test
    void deveListarAtivos() {
        when(clienteRepository.findByAtivoTrue()).thenReturn(List.of(cliente));

        var ativos = clienteService.listarAtivos();

        assertEquals(1, ativos.size());
        assertTrue(ativos.get(0).getAtivo());
    }
}
