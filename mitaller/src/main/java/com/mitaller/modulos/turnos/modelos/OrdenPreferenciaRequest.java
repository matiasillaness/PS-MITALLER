package com.mitaller.modulos.turnos.modelos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrdenPreferenciaRequest {
    OrdenRequest ordenRequest;
    Long idOrden;
    BigDecimal total;
}
