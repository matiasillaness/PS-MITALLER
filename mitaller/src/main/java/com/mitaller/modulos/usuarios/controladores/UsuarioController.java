package com.mitaller.modulos.usuarios.controladores;


import com.mitaller.modulos.comun.model.UsuarioCambiarContrasenia;
import com.mitaller.modulos.usuarios.modelos.UsuarioChangePasswordResponse;
import com.mitaller.modulos.usuarios.modelos.UsuarioFilter;
import com.mitaller.modulos.usuarios.modelos.UsuarioReponse;
import com.mitaller.modulos.usuarios.modelos.UsuarioRequest;
import com.mitaller.modulos.usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }




    @PostMapping("/cambiar-contrasenia-primer-paso")
    public ResponseEntity<Boolean> cambiarContraseniaPrimerPaso(@RequestParam String email) {
        return usuarioService.cambiarContraseniaPrimerPaso(email);
    }

    @PostMapping("/cambiar-contrasenia-segundo-paso")
    public ResponseEntity<UsuarioReponse> cambiarContraseniaSegundoPaso(@RequestBody UsuarioChangePasswordResponse usuario) {
        return usuarioService.cambiarContraseniaSegundoPaso(usuario);
    }

    @GetMapping("/get-usuario")
    public ResponseEntity<UsuarioReponse> getUsuario(String token) {
        return usuarioService.getUsuario(token);
    }

    @PutMapping("/actualizar-usuario-para-admins")
    public ResponseEntity<UsuarioReponse> actualizarUsuarioParaAdmins(@RequestBody UsuarioRequest usuario) {
        return usuarioService.actualizarUsuarioParaAdmins(usuario);
    }

    @PutMapping("/dar-de-baja")
    public ResponseEntity<UsuarioReponse> darDeBaja(@RequestParam String email) {
        return usuarioService.darDeBaja(email);
    }

    @PutMapping("/dar-de-alta")
    public ResponseEntity<UsuarioReponse> darDeAlta(@RequestParam String email) {
        return usuarioService.darDeAlta(email);
    }

    @GetMapping("/obtener-todos-los-usuarios")
    public ResponseEntity<List<UsuarioReponse>> obtenerTodosLosUsuarios(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = true, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {

        UsuarioFilter filter = UsuarioFilter.builder().
                email(email)
                .nombre(nombre).
                role(rol).
                telefono(telefono).
                activo(activo).
                page(page).
                size(size
        ).build();




        return usuarioService.obtenerTodosLosUsuarios(filter);
    }


    @DeleteMapping("/borrar-cuenta")
    public ResponseEntity<Boolean> borrarCuenta(@RequestParam String email) {
        return usuarioService.borrarCuenta(email);
    }

}
