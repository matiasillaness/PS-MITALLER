package com.mitaller.modulos.cobros.modelos;

import ch.qos.logback.core.joran.conditional.PropertyEvalScriptBuilder;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetalleVentaResponse {
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    private String descripcion;
    private String nombreRepuesto;
    private String nombreServicio;

}
