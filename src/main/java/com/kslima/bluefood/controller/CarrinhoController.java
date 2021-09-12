package com.kslima.bluefood.controller;

import com.kslima.bluefood.application.service.ItemCardapioService;
import com.kslima.bluefood.application.service.PedidoService;
import com.kslima.bluefood.domain.pedido.Carrinho;
import com.kslima.bluefood.domain.pedido.Pedido;
import com.kslima.bluefood.domain.pedido.RestauranteDiferenteException;
import com.kslima.bluefood.domain.restaurante.ItemCardapio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCardapioService itemCardapioService;

    @Autowired
    private PedidoService pedidoService;

    @ModelAttribute("carrinho")
    public Carrinho carrinho() {
        return new Carrinho();
    }

    @GetMapping(path = "/visualizar")
    public String viewCarrinho() {
        return "cliente-carrinho";
    }

    @GetMapping(path = "/adicionar")
    public String adicionarItem(
      @RequestParam("itemId") Integer itemId,
      @RequestParam("quantidade") Integer quantidade,
      @RequestParam("observacoes") String observacoes,
      @ModelAttribute("carrinho") Carrinho carrinho,
      Model model) {

        ItemCardapio itemCardapio = itemCardapioService.findById(itemId);

        try {
            carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
        } catch (RestauranteDiferenteException e) {
            model.addAttribute("msg", "Não é possível misturar comidas de restaurantes diferentes");
        }

        return "cliente-carrinho";
    }

    @GetMapping(path = "/remover")
    public String adicionarItem(
            @RequestParam("itemId") Integer itemId,
            @ModelAttribute("carrinho") Carrinho carrinho,
            SessionStatus sessionStatus,
            Model model) {

        ItemCardapio itemCardapio = itemCardapioService.findById(itemId);

        carrinho.removerItem(itemCardapio);

        if ( carrinho.vazio()) {
            sessionStatus.setComplete();
        }
        return "cliente-carrinho";
    }

    @GetMapping(path = "/refazerCarrinho")
    public String refazerCarrinho(
            @RequestParam("pedidoId") Integer pedidoId,
            @ModelAttribute("carrinho") Carrinho carrinho,
            Model model) {
        Pedido pedido = pedidoService.findById(pedidoId);
        carrinho.limpar();

        pedido.getItens().forEach(carrinho::adicionarItem);
        return "cliente-carrinho";
    }
}
