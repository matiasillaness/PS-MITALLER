package com.mitaller.modulos.turnos.modelos;

import com.mitaller.modulos.turnos.dominio.ETipoOrden;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrdenRequest {
    private String patenteDelVehiculo;
    private String modeloDelVehiculo;
    private String tipoOrden;
    private String emailDelCliente;
}
