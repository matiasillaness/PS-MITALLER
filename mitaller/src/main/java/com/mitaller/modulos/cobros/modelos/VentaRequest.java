package com.mitaller.modulos.cobros.modelos;


import com.mitaller.modulos.cobros.dominio.ETipoFactura;
import com.mitaller.modulos.cobros.dominio.ETipoPago;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class VentaRequest {

    @NotBlank(message = "El campo tipoPago es obligatorio")
    private ETipoPago tipoPago;

    @NotBlank(message = "El campo tipoFactura es obligatorio")
    private ETipoFactura tipoFactura;

    @NotBlank(message = "El campo total es obligatorio")
    private String razonSocial;

    @NotBlank(message = "El campo razonSocial es obligatorio")
    private String dniCliente;

    @NotBlank(message = "El campo dniCliente es obligatorio")
    private String direccionCliente;

    private BigDecimal descuento;

    @NotBlank(message = "El campo direccionCliente es obligatorio")
    private String telefonoCliente;



    private String email;

    private Long idVehiculo;


    private List<DetalleVentaRequest> detalleVentaRequest;

    @NotBlank(message = "El campo descripcion es obligatorio")
    private String descripcion;

    private boolean mercadoPago;
}
