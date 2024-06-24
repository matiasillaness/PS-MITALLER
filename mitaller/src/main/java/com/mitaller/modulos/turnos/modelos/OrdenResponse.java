package com.mitaller.modulos.turnos.modelos;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrdenResponse {
    private String fecha;
    private Long id;
    private String mercadoPagoId;
    private String tipoOrden;
    private String estado;
    private String estadoMercadoPago;
    private String nombreDelCliente;
    private String emailDelCliente;
    private BigDecimal total;
    private String patenteDelVehiculo;
    private String modeloDelVehiculo;
    private Boolean recordatorioEnviado;
}
