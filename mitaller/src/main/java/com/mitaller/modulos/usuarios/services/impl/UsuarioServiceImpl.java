package com.mitaller.modulos.usuarios.services.impl;


import com.mitaller.modulos.autorizacion.jwt.JwtService;
import com.mitaller.modulos.comun.model.UsuarioCambiarContrasenia;
import com.mitaller.modulos.comun.servicios.SendMessageService;
import com.mitaller.modulos.usuarios.dominio.Role;
import com.mitaller.modulos.usuarios.dominio.Usuario;
import com.mitaller.modulos.usuarios.modelos.*;
import com.mitaller.modulos.usuarios.repositorio.UsuarioRepository;
import com.mitaller.modulos.usuarios.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    private final JwtService jwtService;
    private final SendMessageService sendMessageService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(JwtService jwtService, SendMessageService sendMessageService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.sendMessageService = sendMessageService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseEntity<UsuarioReponse> getUsuario(String token) {
        String email = jwtService.getEmailFromToken(token);
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(UsuarioReponse.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .telefono(usuario.getTelefono())
                .activo(usuario.isActivo())
                .role(usuario.getRole())
                .build());
    }

    @Override
    public ResponseEntity<Boolean> cambiarContraseniaPrimerPaso(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email);

        usuario.setCodigoCambiarContrasenia(generateRandomNumberOfLength());
        usuarioRepository.save(usuario);

        UsuarioCambiarContrasenia usuarioCambiarContrasenia = UsuarioCambiarContrasenia.builder()
                .email(usuario.getEmail())
                .code(usuario.getCodigoCambiarContrasenia())
                .asunto("Cambio de contraseña")
                .mensaje("Su código para cambiar la contraseña es: ")
                .build();

        sendMessageService.sendEmailToChangePassword(usuarioCambiarContrasenia);

        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<UsuarioReponse> cambiarContraseniaSegundoPaso(UsuarioChangePasswordResponse usuarioChangePasswordResponse) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(usuarioChangePasswordResponse.getEmail());
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        if(usuario.getCodigoCambiarContrasenia() == null){
            throw new RuntimeException("No se ha solicitado un cambio de contraseña, realizar el primer paso");
        }
        if (!Objects.equals(usuario.getCodigoCambiarContrasenia(), usuarioChangePasswordResponse.getCode())){
            throw new RuntimeException("El código ingresado no es correcto");
        }

        if(Objects.equals(usuario.getEmail(), usuarioChangePasswordResponse.getEmail())){
            if (Objects.equals(usuario.getCodigoCambiarContrasenia(), usuarioChangePasswordResponse.getCode())) {
                usuario.setContrasenia(hashPassword(usuarioChangePasswordResponse.getPassword()));
                usuario.setCodigoCambiarContrasenia(null);
                usuarioRepository.save(usuario);
                return ResponseEntity.ok(UsuarioReponse.builder()
                        .email(usuario.getEmail())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .telefono(usuario.getTelefono())
                        .direccion(usuario.getDireccion())
                        .activo(usuario.isActivo())
                        .role(usuario.getRole())
                        .build());
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<UsuarioReponse> actualizarUsuarioParaAdmins(UsuarioRequest usuario) {
        Usuario usuarioActual = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
        if (usuarioActual == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setApellido(usuario.getApellido());
        usuarioActual.setTelefono(usuario.getTelefono());
        usuarioActual.setRole(Role.valueOf(usuario.getRole()));
        usuarioActual.setDireccion(usuario.getDireccion());

        usuarioRepository.save(usuarioActual);

        return ResponseEntity.ok(UsuarioReponse.builder()
                .email(usuarioActual.getEmail())
                .nombre(usuarioActual.getNombre())
                .apellido(usuarioActual.getApellido())
                .telefono(usuarioActual.getTelefono())
                .role(usuarioActual.getRole())
                .build());
    }
    @Override
    public ResponseEntity<UsuarioReponse> actualizarUsuario(UsuarioClientesRequest usuario, String token) {
        Usuario usuarioActual = usuarioRepository.findByEmailIgnoreCase(jwtService.getEmailFromToken(token));
        if (usuarioActual == null) {
            return ResponseEntity.notFound().build();
        }


        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setApellido(usuario.getApellido());
        usuarioActual.setTelefono(usuario.getTelefono());
        usuarioActual.setDireccion(usuario.getDireccion());

        usuarioRepository.save(usuarioActual);

        return ResponseEntity.ok(UsuarioReponse.builder()
                .email(usuarioActual.getEmail())
                .nombre(usuarioActual.getNombre())
                .apellido(usuarioActual.getApellido())
                .telefono(usuarioActual.getTelefono())
                .role(usuarioActual.getRole())
                .build());
    }

    @Override
    public ResponseEntity<Boolean> borrarCuenta(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.delete(usuario);

        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<UsuarioReponse> darDeBaja(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setActivo(false);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(UsuarioReponse.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .telefono(usuario.getTelefono())
                .role(usuario.getRole())
                .build());
    }

    @Override
    public ResponseEntity<UsuarioReponse> darDeAlta(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setActivo(true);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(UsuarioReponse.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .telefono(usuario.getTelefono())
                .role(usuario.getRole())
                .build());
    }

    @Override
    public ResponseEntity<List<UsuarioReponse>> obtenerTodosLosUsuarios(UsuarioFilter filter) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        filter.convertirAMayusculas();

        //filtrar por nombre y apellido al mismo tiempo
        if (filter.getNombre() != null) {
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.getNombre().toLowerCase().contains(filter.getNombre().toLowerCase()) || usuario.getApellido().toLowerCase().contains(filter.getNombre().toLowerCase()))
                    .toList();
        }



        if (filter.getEmail() != null) {
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.getEmail().toLowerCase().contains(filter.getEmail().toLowerCase()))
                    .toList();
        }

        if (filter.getRole() != null) {
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.getRole().contains(filter.getRole()))
                    .toList();
        }
        if (filter.getTelefono() != null) {
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.getTelefono().toLowerCase().contains(filter.getTelefono().toLowerCase()))
                    .toList();
        }
        if (filter.getActivo() != null) {
            usuarios = usuarios.stream()
                    .filter(usuario -> usuario.isActivo() == filter.getActivo())
                    .toList();
        }


        //todo: paginacion

        List<Usuario> sourceList = usuarios;




        if(filter.getSize() <= 0 || filter.getPage() <= 0) {
            throw new IllegalArgumentException("invalid page size: " + filter.getPage());
        }

        int fromIndex = (filter.getPage() - 1) * filter.getSize();
        if(sourceList.size() <= fromIndex){
            return ResponseEntity.ok(Collections.emptyList());
        }

        sourceList = sourceList.subList(fromIndex, Math.min(fromIndex + filter.getSize(), sourceList.size()));

        //ordenar por nombre


        return ResponseEntity.ok(sourceList.stream().map(usuario -> UsuarioReponse.builder()
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .telefono(usuario.getTelefono())
                .direccion(usuario.getDireccion())
                .role(usuario.getRole())
                .activo(usuario.isActivo())
                .build()).toList());
    }

    @Override
    public ResponseEntity<UsuarioReponse> crearUsuarioEmployee(UsuarioRequest usuario, String token) {
        return null;
    }


    private Integer generateRandomNumberOfLength() {
        Random random = new Random();
        // Genera un número aleatorio de 1000 a 9999 (4 dígitos)
        int numeroAleatorio = random.nextInt(9000) + 1000;
        return numeroAleatorio;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
