package com.mitaller.modulos.vehiculos.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class MarcaResponse {

    @JsonProperty("id_brand")
    Long idMarca;

    @JsonProperty("name")
    String nombre;

    @JsonProperty("active")
    boolean activo;
}
