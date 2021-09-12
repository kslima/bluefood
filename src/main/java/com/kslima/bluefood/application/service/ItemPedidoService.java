package com.kslima.bluefood.application.service;

import com.kslima.bluefood.domain.pedido.ItemPedido;
import com.kslima.bluefood.domain.pedido.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    public void save(ItemPedido itemPedido) {
        itemPedidoRepository.save(itemPedido);
    }
}
