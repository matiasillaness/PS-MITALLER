package com.mitaller.modulos.cobros.modelos;


import com.mitaller.modulos.vehiculos.modelos.VehiculoResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class VentaResponse {
    private String fecha;
    private String tipoPago;
    private String tipoFactura;
    private BigDecimal total;
    private String descripcion;
    private boolean estado;
    private boolean dadaDeBaja;
    private String razonSocial;
    private String dniCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private String nombreEmpleado;
    private String numeroFactura;
    private BigDecimal descuento;
    private double iva;
    private VehiculoResponse vehiculo;
    private List<DetalleVentaResponse> detalles;
}
