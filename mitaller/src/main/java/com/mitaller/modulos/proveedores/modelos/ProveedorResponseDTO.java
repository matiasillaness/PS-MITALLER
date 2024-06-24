package com.mitaller.modulos.proveedores.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProveedorResponseDTO {
    private Long idProveedor;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    private String descripcion;
    private String tipoProveedor;
}
