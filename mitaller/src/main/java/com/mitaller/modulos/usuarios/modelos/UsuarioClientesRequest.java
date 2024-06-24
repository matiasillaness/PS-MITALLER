package com.mitaller.modulos.usuarios.modelos;


import lombok.Data;

@Data
public class UsuarioClientesRequest {
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
}
