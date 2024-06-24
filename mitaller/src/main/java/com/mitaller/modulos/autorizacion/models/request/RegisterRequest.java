package com.mitaller.modulos.autorizacion.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @JsonProperty("email")
    @NotBlank(message = "email is required")
    @Email
    String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 100)
    String password;

    @NotBlank(message = "firstname is required")
    @Size(min = 3, max = 100)
    String nombre;

    @NotBlank(message = "lastname is required")
    @Size(min = 3, max = 100)
    String apellido;

    @NotBlank(message = "phone is required")
    String phone;

    String direccion;

}