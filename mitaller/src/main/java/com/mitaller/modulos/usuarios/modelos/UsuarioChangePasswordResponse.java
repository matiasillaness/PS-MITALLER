package com.mitaller.modulos.usuarios.modelos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioChangePasswordResponse {
    private String password;
    private Integer code;
    private String email;
}

