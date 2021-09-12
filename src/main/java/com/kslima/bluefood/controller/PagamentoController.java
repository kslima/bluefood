package com.kslima.bluefood.controller;

import com.kslima.bluefood.application.service.ImageService;
import com.kslima.bluefood.application.service.PedidoService;
import com.kslima.bluefood.domain.pedido.Carrinho;
import com.kslima.bluefood.domain.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/cliente/pagamento")
@SessionAttributes("carrinho")
public class PagamentoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping(path = "/pagar")
    public String pagar(
            @RequestParam("numCartao") String numCartao,
            @ModelAttribute("carrinho")Carrinho carrinho,
            SessionStatus sessionStatus,
            Model model) {
        Pedido pedido = pedidoService.criarEPagar(carrinho, numCartao);
        sessionStatus.setComplete();
        return "redirect:/cliente/pedido/view?pedidoId=" + pedido.getId();
    }

}
