package com.mitaller.modulos.cobros.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteCompraResponse {
    private String nombreCompleto;
    private String direccion;
    private String telefono;
    private String email;
}
