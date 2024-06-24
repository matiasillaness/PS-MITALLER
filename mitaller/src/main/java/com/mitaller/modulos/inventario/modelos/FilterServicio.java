package com.mitaller.modulos.inventario.modelos;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FilterServicio {
    private String nombre;
    private BigDecimal precio;
    private String tipo;
    private Boolean activo;
    private int size;
    private int page;

    public void setAllUpperCase() {
        if (nombre != null) {
            nombre = nombre.toUpperCase();
        }
        if (tipo != null) {
            tipo = tipo.replace(" ", "_");
            tipo = tipo.toUpperCase();
        }

    }
}
