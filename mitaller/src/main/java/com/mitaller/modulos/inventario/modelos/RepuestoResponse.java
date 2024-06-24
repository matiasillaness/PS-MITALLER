package com.mitaller.modulos.inventario.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepuestoResponse {
    private Long idRepuesto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String marca;
    private boolean activo;
    private int stock;
}
