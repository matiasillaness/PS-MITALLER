package com.mitaller.modulos.comun.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioCambiarContrasenia {
    String email;
    String mensaje;
    String asunto;
    Integer code;
}
