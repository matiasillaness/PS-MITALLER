package com.mitaller.modulos.inventario.modelos;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepuestoRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @NotEmpty(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    @PositiveOrZero(message = "El precio no puede ser negativo o cero")
    private BigDecimal precio;

    @NotNull(message = "El stock no puede estar vacío")
    @PositiveOrZero(message = "El stock no puede ser negativo o cero")
    private int stock;

    @NotNull(message = "El id de la marca no puede estar vacío")
    private Long idMarca;

    public void setAllToUpperCase() {
        this.nombre = this.nombre.toUpperCase();
        this.descripcion = this.descripcion.toUpperCase();
    }
}
