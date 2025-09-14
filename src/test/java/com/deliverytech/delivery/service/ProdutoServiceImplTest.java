package com.deliverytech.delivery.service;

import com.deliverytech.delivery.model.Produto;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.service.impl.ProdutoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarProduto() {
        Produto produto = Produto.builder()
                .nome("Pizza")
                .descricao("Pizza grande")
                .preco(new BigDecimal("50.00"))
                .disponivel(true)
                .build();

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto salvo = produtoService.cadastrar(produto);

        assertThat(salvo.getNome()).isEqualTo("Pizza");
        assertThat(salvo.getDisponivel()).isTrue();
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void deveAtualizarProduto() {
        Produto produtoExistente = Produto.builder()
                .nome("Pizza")
                .descricao("Pizza pequena")
                .preco(new BigDecimal("40.00"))
                .disponivel(true)
                .build();

        Produto atualizado = Produto.builder()
                .nome("Pizza Grande")
                .descricao("Pizza grande")
                .preco(new BigDecimal("50.00"))
                .disponivel(true)
                .build();

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(atualizado);

        Produto resultado = produtoService.atualizar(1L, atualizado);

        assertThat(resultado.getNome()).isEqualTo("Pizza Grande");
        assertThat(resultado.getPreco()).isEqualTo(new BigDecimal("50.00"));
        verify(produtoRepository, times(1)).save(produtoExistente);
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = Produto.builder()
                .nome("Pizza")
                .descricao("Pizza grande")
                .preco(new BigDecimal("50.00"))
                .disponivel(true)
                .build();

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> encontrado = produtoService.buscarPorId(1L);

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Pizza");
    }
}
