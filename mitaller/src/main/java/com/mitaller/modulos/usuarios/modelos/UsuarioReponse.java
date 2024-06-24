package com.mitaller.modulos.usuarios.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioReponse {
    String email;
    String nombre;
    String apellido;
    String telefono;
    String role;
    String direccion;
    Boolean activo;
}


