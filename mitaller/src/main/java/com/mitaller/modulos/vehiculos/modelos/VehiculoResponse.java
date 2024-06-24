package com.mitaller.modulos.vehiculos.modelos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehiculoResponse {
    private String patente;
    private String modelo;
    private String observaciones;
    private Long idVehiculo;
    @JsonProperty("tipo_vehiculo")
    private String tipoVehiculo;
    private String color;
    private String marca;
    private boolean activo;
    private Integer kilometraje;
    @JsonProperty("numero_chasis")
    private String numeroChasis;
}
