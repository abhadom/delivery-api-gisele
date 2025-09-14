package com.deliverytech.delivery.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery.exception.EntityNotFoundException;
import com.deliverytech.delivery.model.Cliente;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    @Override
    public Cliente atualizar(Long id, Cliente atualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", id));
        cliente.setNome(atualizado.getNome());
        cliente.setEmail(atualizado.getEmail());
        return clienteRepository.save(cliente);
    }

    @Override
    public void ativarDesativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", id));
        cliente.setAtivo(!cliente.getAtivo());
        clienteRepository.save(cliente);
    }
}
