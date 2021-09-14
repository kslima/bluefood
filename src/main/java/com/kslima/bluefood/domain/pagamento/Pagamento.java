package com.kslima.bluefood.domain.pagamento;

import com.kslima.bluefood.domain.pedido.Pedido;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    private Integer id;

    @NotNull
    @OneToOne
    @MapsId
    private Pedido pedido;

    @NotNull
    LocalDateTime data;

    @Column(name = "num_cartao_final")
    @NotNull
    @Size(min = 4, max = 4)
    private String numCartaoFinal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandeiraCartao bandeiraCartao;

    public void definirNumeroEBandeira(String numCartao) {
        numCartaoFinal = numCartao.substring(12);
        bandeiraCartao = getBandeira(numCartao);
    }

    private BandeiraCartao getBandeira(String numCartao) {
        if (numCartao.startsWith("0000")) {
            return BandeiraCartao.AMEX;
        }

        if (numCartao.startsWith("1111")) {
            return BandeiraCartao.ELO;
        }

        if (numCartao.startsWith("2222")) {
            return BandeiraCartao.MASTER;
        }

        return BandeiraCartao.VISA;
    }

}
