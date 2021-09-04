package com.kslima.bluefood.domain.restaurante;

import lombok.Data;

@Data
public class SearchFilter {

    public enum SearchType {
        TEXTO,
        CATEGORIA
    }

    private String texto;
    private SearchType searchType;
    private Integer categoriaId;

    public void processFilter() {
        if (searchType == SearchType.TEXTO) {
            categoriaId = null;

        }else if (searchType == SearchType.CATEGORIA) {
            texto = null;
        }
    }
}
