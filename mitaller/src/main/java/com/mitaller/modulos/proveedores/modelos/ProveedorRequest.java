package com.mitaller.modulos.proveedores.modelos;

import com.mitaller.modulos.proveedores.dominio.ETipoProveedor;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ProveedorRequest {


    @NotBlank(message = "El nombre del proveedor es requerido")
    private String nombre;

    @NotBlank(message = "El telefono del proveedor es requerido")
    private String telefono;

    @NotBlank(message = "La direccion del proveedor es requerida")
    private String direccion;

    @NotBlank(message = "El email del proveedor es requerido")
    private String email;

    private String descripcion;

    @NotBlank(message = "El tipo de proveedor es requerido")
    private String tipoProveedor;
}
