package com.kslima.bluefood.controller;

import javax.validation.Valid;

import com.kslima.bluefood.application.service.ClienteService;
import com.kslima.bluefood.application.service.RestauranteService;
import com.kslima.bluefood.application.service.ValidationException;
import com.kslima.bluefood.domain.cliente.Cliente;
import com.kslima.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.kslima.bluefood.domain.restaurante.Restaurante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @GetMapping("/cliente/new")
    public String newCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        ControlleHelper.setEditModel(model, false);
        return "cliente-cadastro";
    }

    @PostMapping(path = "/cliente/save")
    public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
                              Errors errors,
                              Model model) {

        if (!errors.hasErrors()) {
            try {
                clienteService.saveCliente(cliente);
                model.addAttribute("msg", "Cliente gravado com sucesso!");

            } catch (ValidationException e) {
                errors.rejectValue("email", null, e.getMessage());
            }

        }

        ControlleHelper.setEditModel(model, false);
        return "cliente-cadastro";
    }

    @GetMapping("/restaurante/new")
    public String newRestaurante(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        ControlleHelper.setEditModel(model, false);
        ControlleHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }

    @PostMapping(path = "/restaurante/save")
    public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante, Errors errors,
                                  Model model) {
        System.out.println(errors.toString());
        if (!errors.hasErrors()) {
            try {
                restauranteService.saveRestaurante(restaurante);
                model.addAttribute("msg", "Restaurante gravado com sucesso!");

            } catch (ValidationException e) {
                errors.rejectValue("email", null, e.getMessage());
            }

        }

        ControlleHelper.setEditModel(model, false);
        ControlleHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }


}
