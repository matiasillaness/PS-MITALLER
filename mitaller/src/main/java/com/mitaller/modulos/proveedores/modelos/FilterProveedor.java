package com.mitaller.modulos.proveedores.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterProveedor {
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    private String tipoProveedor;
    private int page;
    private int size;
}
