package com.mitaller.modulos.cobros.modelos;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetalleCompraResponse {
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private String nombreRepuesto;
}
