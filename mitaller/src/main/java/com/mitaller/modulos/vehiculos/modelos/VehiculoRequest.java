package com.mitaller.modulos.vehiculos.modelos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehiculoRequest {

    @NotBlank(message = "La patente es obligatoria")
    private String patente;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;


    private String observaciones;

    @JsonProperty("tipo_vehiculo")
    private String tipoVehiculo;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @JsonProperty("marca_id")
    private Long marcaId;


    private Integer kilometraje;

    @JsonProperty("numero_chasis")
    private String numeroChasis;

    public void setAllToUpperCase() {
        this.patente = this.patente.toUpperCase();
        this.modelo = this.modelo.toUpperCase();
        this.observaciones = this.observaciones.toUpperCase();
        this.tipoVehiculo = this.tipoVehiculo.toUpperCase();
        this.color = this.color.toUpperCase();
    }
}
