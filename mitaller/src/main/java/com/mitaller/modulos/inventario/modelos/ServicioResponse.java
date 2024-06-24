package com.mitaller.modulos.inventario.modelos;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicioResponse {
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String tipo;
    private boolean activo;
}
