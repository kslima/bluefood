package com.kslima.bluefood.domain.restaurante;

import com.kslima.bluefood.util.StringUtils;
import lombok.Data;

@Data
public class SearchFilter {

    public enum SearchType {
        TEXTO,
        CATEGORIA
    }

    public enum Order {
        TAXA, TEMPO;
    }

    public enum Command {
        ENTREGA_GRATIS, MAIOR_TAXA, MENOR_TAXA, MAIOR_TEMPO, MENOR_TEMPO;
    }

    private String texto;
    private SearchType searchType;
    private Integer categoriaId;
    private Order order = Order.TAXA;
    private boolean asc;
    private boolean entregaGratis;

    public void processFilter(String cmdString) {

        if (!StringUtils.isEmpty(cmdString)) {
            Command command = Command.valueOf(cmdString);

            switch (command) {
                case ENTREGA_GRATIS:
                    entregaGratis = !entregaGratis;
                    break;

                case MAIOR_TAXA:
                    order = Order.TAXA;
                    asc = false;
                    break;

                case MENOR_TAXA:
                    order = Order.TAXA;
                    asc = true;
                    break;

                case MAIOR_TEMPO:
                    order = Order.TEMPO;
                    asc = false;
                    break;

                case MENOR_TEMPO:
                    order = Order.TEMPO;
                    asc = true;
                    break;
            }
        }

        if (searchType == SearchType.TEXTO) {
            categoriaId = null;

        }else if (searchType == SearchType.CATEGORIA) {
            texto = null;
        }
    }
}
