package com.mitaller.modulos.turnos.modelos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Data
@Builder
public class OrdenParcialRequest {
    // Formato de fecha: yyyy-MM-dd HH:mm
    private String fecha;
    private BigDecimal total;

    public boolean comprobarFechaYFormato() {
        try {
            LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}