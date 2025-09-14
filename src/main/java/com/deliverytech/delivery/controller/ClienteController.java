package com.deliverytech.delivery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery.dto.request.ClienteRequest;
import com.deliverytech.delivery.dto.response.ClienteResponse;
import com.deliverytech.delivery.model.Cliente;
import com.deliverytech.delivery.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();

        Cliente salvo = clienteService.cadastrar(cliente);
        ClienteResponse response = new ClienteResponse(
                salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(
            @PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        Cliente atualizado = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();

        Cliente salvo = clienteService.atualizar(id, atualizado);
        ClienteResponse response = new ClienteResponse(
                salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativo")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteResponse>> listarAtivos() {
        List<ClienteResponse> ativos = clienteService.listarAtivos().stream()
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ativos);
    }
}
