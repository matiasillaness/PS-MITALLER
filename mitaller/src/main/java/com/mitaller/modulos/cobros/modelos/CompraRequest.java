package com.mitaller.modulos.cobros.modelos;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CompraRequest {

    @NotBlank(message = "La descripciom del comprobante es requerido")
    private String descripcion;

    @NotBlank(message = "El numero del iva es requerido")
    private BigDecimal iva;

    @NotBlank(message = "El id del proveedor es requerido")
    private Long idProveedor;

    @NotBlank(message = "El tipo de pago es requerido")
    private String tipoPago;

    List<DetalleCompraRequest> detalleCompraRequest;
}
