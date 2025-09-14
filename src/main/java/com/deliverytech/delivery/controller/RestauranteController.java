package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.request.RestauranteRequest;
import com.deliverytech.delivery.dto.response.RestauranteResponse;
import com.deliverytech.delivery.exception.ConflictException;
import com.deliverytech.delivery.model.Restaurante;
import com.deliverytech.delivery.service.RestauranteService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<RestauranteResponse> cadastrar(@Valid @RequestBody RestauranteRequest request) {
        if (restauranteService.findByNome(request.getNome())) {
            throw new ConflictException("Já existe um restaurante cadastrado com este nome.", "nome", request.getNome());
        }

        Restaurante restaurante = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .ativo(true)
                .build();

        Restaurante salvo = restauranteService.cadastrar(restaurante);

        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCategoria(),
                salvo.getTelefone(),
                salvo.getTaxaEntrega(),
                salvo.getTempoEntregaMinutos(),
                salvo.getAtivo()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody RestauranteRequest request) {
        Restaurante atualizado = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .build();

        Restaurante salvo = restauranteService.atualizar(id, atualizado);

        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCategoria(),
                salvo.getTelefone(),
                salvo.getTaxaEntrega(),
                salvo.getTempoEntregaMinutos(),
                salvo.getAtivo()
        ));
    }

    @GetMapping
    public List<RestauranteResponse> listarTodos() {
        return restauranteService.listarTodos().stream()
                .map(r -> new RestauranteResponse(
                        r.getId(),
                        r.getNome(),
                        r.getCategoria(),
                        r.getTelefone(),
                        r.getTaxaEntrega(),
                        r.getTempoEntregaMinutos(),
                        r.getAtivo()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(r -> new RestauranteResponse(
                        r.getId(),
                        r.getNome(),
                        r.getCategoria(),
                        r.getTelefone(),
                        r.getTaxaEntrega(),
                        r.getTempoEntregaMinutos(),
                        r.getAtivo()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public List<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria).stream()
                .map(r -> new RestauranteResponse(
                        r.getId(),
                        r.getNome(),
                        r.getCategoria(),
                        r.getTelefone(),
                        r.getTaxaEntrega(),
                        r.getTempoEntregaMinutos(),
                        r.getAtivo()
                ))
                .collect(Collectors.toList());
    }

    // Caso queira adicionar ativar/desativar no futuro
    @PatchMapping("/{id}/ativar-desativar")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        // Implementar no serviço se necessário
        // restauranteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }
}
