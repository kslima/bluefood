package com.kslima.bluefood.application.service;

import com.kslima.bluefood.domain.restaurante.ItemCardapio;
import com.kslima.bluefood.domain.restaurante.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCardapioService {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    public List<String> findCategorias(Integer restauranteId) {
        return itemCardapioRepository.findCategorias(restauranteId);
    }

    public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId) {
        return itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
    }

    public List<ItemCardapio> findByRestaurante_IdAndDestaqueOrderByNome(Integer restauranteId, boolean destaque) {
        return itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, destaque);
    }

    public List<ItemCardapio> findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(Integer restauranteId,
                                                                                     boolean destaque,
                                                                                     String categoria) {
        return itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, destaque,
                categoria);
    }

}
