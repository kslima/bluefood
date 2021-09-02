package com.kslima.bluefood.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public enum ImageDirectory {

    LOGOTIPO() {
        @Override
        public String getDirectory() {
            return LOGOTIPO.logotiposDir;
        }
    },

    COMIDA() {
        @Override
        public String getDirectory() {
            return COMIDA.comidasDir;
        }
    },

    CATEGORIA() {
        @Override
        public String getDirectory() {
            return CATEGORIA.categoriasDir;
        }
    };

    @Value("${bluefood.files.logotipo}")
    private String logotiposDir;

    @Value("${bluefood.files.comida}")
    private String comidasDir;

    @Value("${bluefood.files.categoria}")
    private String categoriasDir;

    public abstract String getDirectory();
}
