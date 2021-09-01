package com.kslima.bluefood.controller;

import com.kslima.bluefood.application.service.CategoriaRestauranteService;
import com.kslima.bluefood.domain.restaurante.CategoriaRestaurante;
import com.kslima.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.List;

public class ControlleHelper {

    public static void setEditModel(Model model, boolean isEdit) {
        model.addAttribute("editMode", isEdit);
    }

    public static void addCategoriasToRequest(CategoriaRestauranteService categoriaRestauranteService, Model model) {
        List<CategoriaRestaurante> categorias = categoriaRestauranteService.findAllSortByName();
        model.addAttribute("categorias", categorias);
    }
    
}
