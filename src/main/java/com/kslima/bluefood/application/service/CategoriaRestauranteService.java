package com.kslima.bluefood.application.service;

import com.kslima.bluefood.domain.restaurante.CategoriaRestaurante;
import com.kslima.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaRestauranteService {

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    public List<CategoriaRestaurante> findAll() {
        return categoriaRestauranteRepository.findAll();
    }
    public List<CategoriaRestaurante> findAllSortByName() {
        return categoriaRestauranteRepository.findAll(Sort.by("nome"));
    }

}
