package com.kslima.bluefood.controller;

import com.kslima.bluefood.application.service.*;
import com.kslima.bluefood.domain.cliente.Cliente;
import com.kslima.bluefood.domain.restaurante.CategoriaRestaurante;
import com.kslima.bluefood.domain.restaurante.ItemCardapio;
import com.kslima.bluefood.domain.restaurante.Restaurante;
import com.kslima.bluefood.domain.restaurante.SearchFilter;
import com.kslima.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CategoriaRestauranteService categoriaRestauranteService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ItemCardapioService itemCardapioService;

    @GetMapping(path = "/home")
    public String home(Model model) {
        List<CategoriaRestaurante> categorias = categoriaRestauranteService.findAllSortByName();
        model.addAttribute("categorias", categorias);
        model.addAttribute("searchFilter", new SearchFilter());
        return "cliente-home";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model) {
        Integer clienteId = SecurityUtils.loggetdCliente().getId();
        Cliente cliente = clienteService.findById(clienteId);
        model.addAttribute("cliente", cliente);
        ControlleHelper.setEditModel(model, true);
        return "cliente-cadastro";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute("cliente") @Valid Cliente cliente,
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

    @GetMapping(path = "/search")
    public String search(@ModelAttribute("searchFilter") SearchFilter filter,
                         @RequestParam(value = "cmd", required = false) String cmdString, Model model) {

        filter.processFilter(cmdString);

        List<Restaurante> restaurantes = restauranteService.search(filter);
        model.addAttribute("restaurantes", restaurantes);

        ControlleHelper.addCategoriasToRequest(categoriaRestauranteService, model);

        model.addAttribute("searchFilter", filter);
        model.addAttribute("cep", SecurityUtils.loggetdCliente().getCep());

        return "cliente-busca";
    }

    @GetMapping(path = "/restaurante")
    public String viewRestaurante(@RequestParam("restauranteId") Integer restauranteId,
                                  @RequestParam(value = "categoria", required = false) String categoria,
                                  Model model) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("cep", SecurityUtils.loggetdCliente().getCep());

        List<String> categorias = itemCardapioService.findCategorias(restauranteId);
        model.addAttribute("categorias", categorias);

        List<ItemCardapio> itensCardapioDestaque;
        List<ItemCardapio> itensCardapioNaoDestaque;

        if (categoria == null) {
            itensCardapioDestaque = itemCardapioService
                    .findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, true);

            itensCardapioNaoDestaque = itemCardapioService
                    .findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, false);

        } else {
            itensCardapioDestaque = itemCardapioService
                    .findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true, categoria);

            itensCardapioNaoDestaque = itemCardapioService
                    .findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false, categoria);
        }

        model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
        model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
        model.addAttribute("categoriaSelecionada", categoria);
        return "cliente-restaurante";
    }

}
