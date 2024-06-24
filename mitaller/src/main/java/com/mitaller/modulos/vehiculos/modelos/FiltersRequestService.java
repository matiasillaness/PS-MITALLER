package com.mitaller.modulos.vehiculos.modelos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltersRequestService {
    String patente;
    String modelo;
    String tipoVehiculo;
    int size;
    int page;
    Boolean activo;


    public void convertirAMayusculas() {
        if (patente != null) {
            patente = patente.toUpperCase();
        }
        if (modelo != null) {
            modelo = modelo.toUpperCase();
        }
        if (tipoVehiculo != null) {
            tipoVehiculo = tipoVehiculo.replace(" ", "_");
            tipoVehiculo = tipoVehiculo.toUpperCase();
        }
        if (size <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + page);
        }
        if (size > 500) {
            throw new IllegalArgumentException("invalid page size: " + size);
        }

    }
}
