package com.mitaller.modulos.inventario.modelos;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FiltersRepuesto {
    String nombre;
    BigDecimal precio;
    String marca;
    Boolean activo;
    int size;
    int page;

    public void convertirAMayusculas() {
        if (nombre != null) {
            nombre = nombre.toUpperCase();
        }
        if (size <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + page);
        }
        if (size > 500) {
            throw new IllegalArgumentException("invalid page size: " + size);
        }
    }
}
