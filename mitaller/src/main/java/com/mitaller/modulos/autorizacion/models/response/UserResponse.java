package com.mitaller.modulos.autorizacion.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitaller.modulos.usuarios.dominio.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    String firstname;
    String lastname;
    String email;
    String telefono;
    String direccion;
    Role role;
}
