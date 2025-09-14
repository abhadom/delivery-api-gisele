package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.model.Restaurante;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    @Override
    public Restaurante cadastrar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    @Override
    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll();
    }

    @Override
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria);
    }

    @Override
    public Restaurante atualizar(Long id, Restaurante atualizado) {
        return restauranteRepository.findById(id)
                .map(r -> {
                    r.setNome(atualizado.getNome());
                    r.setTelefone(atualizado.getTelefone());
                    r.setCategoria(atualizado.getCategoria());
                    r.setTaxaEntrega(atualizado.getTaxaEntrega());
                    r.setTempoEntregaMinutos(atualizado.getTempoEntregaMinutos());
                    return restauranteRepository.save(r);
                }).orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    }

    @Override
    public boolean findByNome(String nome) {
        return restauranteRepository.findByNome(nome).isPresent();
    }

    @Override
    public void ativarDesativar(Long id) {
    restauranteRepository.findById(id).ifPresent(r -> {
        r.setAtivo(!r.getAtivo());
        restauranteRepository.save(r);
    });
}

    @Override
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }


}
