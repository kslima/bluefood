package com.kslima.bluefood.domain.pedido;

import com.kslima.bluefood.domain.restaurante.ItemCardapio;
import com.kslima.bluefood.domain.restaurante.Restaurante;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Carrinho implements Serializable {

    private final List<ItemPedido> itens = new ArrayList<>();
    private Restaurante restaurante;

    public void adicionarItem(ItemCardapio itemCardapio, Integer quantidade, String observacoes) throws RestauranteDiferenteException {
        if (itens.size() == 0) {
            restaurante = itemCardapio.getRestaurante();

        } else if (!itemCardapio.getRestaurante().getId().equals(restaurante.getId())) {
            throw new RestauranteDiferenteException();
        }

        if (!exists(itemCardapio)) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setItemCardapio(itemCardapio);
            itemPedido.setQuantidade(quantidade);
            itemPedido.setObservacoes(observacoes);
            itemPedido.setPreco(itemCardapio.getPreco());
            itens.add(itemPedido);
        }

    }

    public void adicionarItem(ItemPedido itemPedido) {
        try {
            adicionarItem(itemPedido.getItemCardapio(), itemPedido.getQuantidade(), itemPedido.getObservacoes());

        } catch (RestauranteDiferenteException ignored) {
        }
    }

    public void removerItem(ItemCardapio itemCardapio) {
        itens.removeIf(ip -> ip.getItemCardapio().getId().equals(itemCardapio.getId()));

        if (itens.size() == 0) {
            restaurante = null;
        }
    }

    private boolean exists(ItemCardapio itemCardapio) {
        return itens.stream()
                .anyMatch(i -> i.getItemCardapio().getId().equals(itemCardapio.getId()));
    }

    public BigDecimal getPrecoTotal(boolean adicionarTaxaEntrega) {
        BigDecimal soma = BigDecimal.ZERO;

        for (ItemPedido item : itens) {
            soma = soma.add(item.getPrecoCalculado());
        }

        if ( adicionarTaxaEntrega) {
            soma = soma.add(restaurante.getTaxaEntrega());
        }

        return soma;
    }

    public void limpar() {
        itens.clear();
        restaurante = null;
    }

    public boolean vazio() {
        return itens.size() == 0;
    }
}
