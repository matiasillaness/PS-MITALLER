package com.mitaller.modulos.cobros.modelos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class DetalleVentaRequest {

    @Max(value = 100, message = "La cantidad no puede ser mayor a 100")
    @Min(value = 1, message = "La cantidad no puede ser menor a 1")
    private int cantidad;

    private Long idRepuesto;
    private Long idServicio;
}
