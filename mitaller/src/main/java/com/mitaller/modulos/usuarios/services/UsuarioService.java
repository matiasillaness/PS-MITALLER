package com.mitaller.modulos.usuarios.services;

import com.mitaller.modulos.usuarios.modelos.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UsuarioService {
    ResponseEntity<UsuarioReponse> getUsuario(String token);
    ResponseEntity<Boolean> cambiarContraseniaPrimerPaso(String token);
    ResponseEntity<UsuarioReponse> cambiarContraseniaSegundoPaso(UsuarioChangePasswordResponse usuarioChangePasswordResponse);
    ResponseEntity<UsuarioReponse> actualizarUsuarioParaAdmins(UsuarioRequest usuario);
    ResponseEntity<UsuarioReponse> darDeBaja(String email);
    ResponseEntity<UsuarioReponse> darDeAlta(String email);
    ResponseEntity<List<UsuarioReponse>> obtenerTodosLosUsuarios(UsuarioFilter filter);
    ResponseEntity<UsuarioReponse> crearUsuarioEmployee(UsuarioRequest usuario, String token);
    ResponseEntity<UsuarioReponse> actualizarUsuario(UsuarioClientesRequest usuario, String token);
    ResponseEntity<Boolean> borrarCuenta(String email);
}
