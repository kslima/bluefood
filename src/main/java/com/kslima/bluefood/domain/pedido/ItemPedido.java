package com.kslima.bluefood.domain.pedido;

import com.kslima.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private ItemPedidoPK id;

    @NotNull
    @ManyToOne
    private ItemCardapio itemCardapio;

    private Integer quantidade;

    private String observacoes;

    @NotNull
    private BigDecimal preco;

    public BigDecimal getPrecoCalculado() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

}
