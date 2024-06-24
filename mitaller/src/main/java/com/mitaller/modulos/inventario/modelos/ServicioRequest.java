package com.mitaller.modulos.inventario.modelos;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ServicioRequest {
    @NotNull(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    private BigDecimal precio;

    @NotNull(message = "El tipo no puede estar vacío")
    private String tipo;

    public void setAllUpperCase() {
        if (nombre != null) {
            nombre = nombre.toUpperCase();
        }
        if (descripcion != null) {
            descripcion = descripcion.toUpperCase();
        }
        if (tipo != null) {
            tipo = tipo.replace(" ", "_");
            tipo = tipo.toUpperCase();
        }

    }
}
