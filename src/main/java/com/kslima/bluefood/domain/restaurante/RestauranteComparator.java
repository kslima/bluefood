package com.kslima.bluefood.domain.restaurante;

import java.util.Comparator;

public class RestauranteComparator implements Comparator<Restaurante> {

    private final SearchFilter filter;
    private final String cep;

    public RestauranteComparator(SearchFilter filter, String cep) {
        this.filter = filter;
        this.cep = cep;
    }

    @Override
    public int compare(Restaurante r1, Restaurante r2) {
        int result;

        if (filter.getOrder() == SearchFilter.Order.TAXA) {
            result = r1.getTaxaEntrega().compareTo(r2.getTaxaEntrega());

        } else if (filter.getOrder() == SearchFilter.Order.TEMPO) {
            result = r1.calcularTempoEntrega(cep).compareTo(r2.calcularTempoEntrega(cep));

        } else {
            throw new IllegalStateException("O valor de ordenação " + filter.getOrder() + " não é válido");
        }
        return filter.isAsc() ? result : -result;
    }
}
