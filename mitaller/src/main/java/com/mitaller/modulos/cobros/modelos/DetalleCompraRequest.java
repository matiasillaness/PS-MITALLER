package com.mitaller.modulos.cobros.modelos;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class DetalleCompraRequest {
    private int cantidad;
    private BigDecimal precioUnitario;
    private Long idRepuesto;
}
