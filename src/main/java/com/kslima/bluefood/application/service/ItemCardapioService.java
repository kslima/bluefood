package com.kslima.bluefood.application.service;

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

}
