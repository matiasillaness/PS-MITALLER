package com.mitaller.modulos.usuarios.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioFilter {
    private String email;
    private String nombre;
    private String telefono;
    private String role;
    private Boolean activo;
    private Integer page;
    private Integer size;

    public void convertirAMayusculas() {
        if (email != null) {
            email = email.toUpperCase();
        }
        if (nombre != null) {
            nombre = nombre.toUpperCase();
        }
        if (telefono != null) {
            telefono = telefono.toUpperCase();
        }
        if (role != null) {
            role = role.toUpperCase();
        }
        if (size <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + page);
        }
        if (size > 500) {
            throw new IllegalArgumentException("invalid page size: " + size);
        }
    }
}
