package com.kslima.bluefood.controller;

import com.kslima.bluefood.application.service.PedidoService;
import com.kslima.bluefood.domain.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping(path = "/view")
    public String viewPedido(
            @RequestParam("pedidoId") Integer pedidoId,
            Model model) {

        Pedido pedido = pedidoService.findById(pedidoId);
        model.addAttribute("pedido", pedido);
        return "cliente-pedido";
    }

}
