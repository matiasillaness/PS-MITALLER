package com.mitaller.modulos.usuarios.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioRequest {
    String email;
    String nombre;
    String apellido;
    String telefono;
    String direccion;
    String role;
}
