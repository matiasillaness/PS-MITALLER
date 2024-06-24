package com.mitaller.modulos.cobros.modelos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mitaller.modulos.proveedores.modelos.ProveedorResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CompraResponse {

    @JsonProperty("nombreCompra")
    private String nombreComprobante;
    private String fecha;
    private String tipoPago;
    private BigDecimal total;
    private String descripcion;
    private boolean dadaDeBaja;
    private ProveedorResponseDTO proveedor;
    private List<DetalleCompraResponse> detalles;
}
