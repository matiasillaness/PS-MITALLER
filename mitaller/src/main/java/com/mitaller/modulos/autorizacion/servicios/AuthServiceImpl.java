package com.mitaller.modulos.autorizacion.servicios;

import com.mitaller.modulos.autorizacion.jwt.JwtService;
import com.mitaller.modulos.autorizacion.models.request.LoginRequest;
import com.mitaller.modulos.autorizacion.models.request.RegisterRequest;
import com.mitaller.modulos.autorizacion.models.response.AuthResponse;
import com.mitaller.modulos.autorizacion.models.response.UserResponse;
import com.mitaller.modulos.usuarios.dominio.Role;
import com.mitaller.modulos.usuarios.dominio.Usuario;
import com.mitaller.modulos.usuarios.repositorio.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UsuarioRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        try {
            Usuario user = Usuario.builder()
                    .apellido(request.getApellido())
                    .contrasenia(passwordEncoder.encode(request.getPassword()))
                    .nombre(request.getNombre())
                    .telefono(request.getPhone())
                    .email(request.getEmail())
                    .direccion(request.getDireccion())
                    .role(Role.ROLE_USER)
                    .activo(true)
                    .build();

            usuarioRepository.save(user);

            String token = jwtService.getToken(user);

            log.info("User registered: {}", user);

            return ResponseEntity.ok(AuthResponse.builder()
                    .message("User registered")
                    .status(200)
                    .token(token)
                    .expiresIn(jwtService.getExpiration(user).toString())
                    .type("Bearer")
                    .userRegister(
                            UserResponse.builder()
                                    .email(user.getEmail())
                                    .firstname(user.getNombre())
                                    .lastname(user.getApellido())
                                    .telefono(user.getTelefono())
                                    .direccion(user.getDireccion())
                                    .role(Role.valueOf(user.getRole())).build()
                    ).build());

        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("Email already exists")
                    .status(409)
                    .build(), HttpStatus.CONFLICT);
        }catch (Exception e){
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("Error")
                    .status(500)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<AuthResponse> registerEmployer(RegisterRequest request) {
        try{
            Usuario user = Usuario.builder()
                    .apellido(request.getApellido())
                    .nombre(request.getNombre())
                    .email(request.getEmail())
                    .telefono(request.getPhone())
                    .contrasenia(passwordEncoder.encode(request.getPassword()))
                    .activo(true)
                    .role(Role.ROLE_EMPLOYER)
                    .build();

            usuarioRepository.save(user);

            log.info("User registered: {}", user);

            return ResponseEntity.ok(AuthResponse.builder()
                    .message("User registered")
                    .status(200)
                    .userRegister(
                            UserResponse.builder()
                                    .email(user.getEmail())
                                    .firstname(user.getNombre())
                                    .lastname(user.getApellido())
                                    .telefono(user.getTelefono())
                                    .direccion(user.getDireccion())
                                    .role(Role.valueOf(user.getRole())).build()
                    ).build());
        }
        catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("Email already exists")
                    .status(409)
                    .build(), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("Error")
                    .status(500)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            Usuario user = usuarioRepository.findByEmailIgnoreCase(request.getEmail());

            if (Objects.isNull(user)) {
                return new ResponseEntity<>(AuthResponse.builder()
                        .message("User not found")
                        .status(404)
                        .build(), HttpStatus.NOT_FOUND);
            }

            String token = jwtService.getToken(user);

            return ResponseEntity.ok(AuthResponse.builder()
                    .message("User logged in")
                    .status(200)
                    .token(token)
                    .expiresIn(jwtService.getExpiration(user).toString())
                    .type("Bearer")
                    .userRegister(
                            UserResponse.builder()
                                    .email(user.getEmail())
                                    .firstname(user.getNombre())
                                    .lastname(user.getApellido())
                                    .telefono(user.getTelefono())
                                    .direccion(user.getDireccion())
                                    .role(Role.valueOf(cleanCorchetes(user.getAuthorities().toString()))).build()
                    ).build());

    }

    @Override
    public void registerOfAdmin(RegisterRequest request) {
        try{
            Usuario user = Usuario.builder()
                    .apellido(request.getApellido())
                    .email(request.getEmail())
                    .telefono(request.getPhone())
                    .nombre(request.getNombre())
                    .contrasenia(passwordEncoder.encode(request.getPassword()))
                    .direccion(request.getDireccion())
                    .role(Role.ROLE_ADMIN)
                    .activo(true)
                    .build();

            usuarioRepository.save(user);

            log.info("User registered: {}", user);

        }
        catch (DataIntegrityViolationException e){
            log.error("User already exists");
        }
        catch (Exception e){
            log.error("Error");
        }
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(String refreshToken) {
        try {
            String username = jwtService.getEmailFromToken(refreshToken);
            UserDetails user = usuarioRepository.findByEmailIgnoreCase(username);

            if (jwtService.isTokenValid(refreshToken, user)) {
                String token = jwtService.getToken(user);

                return ResponseEntity.ok(AuthResponse.builder()
                        .token(token)
                        .expiresIn(jwtService.getExpiration(user).toString())
                        .type("Bearer")
                        .message("Token refreshed")
                        .build());
            } else {
                return new ResponseEntity<>(AuthResponse.builder()
                        .message("Invalid token")
                        .status(401)
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("Error refreshing token")
                    .status(500)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   /* public static Date parseDate(Date date) {
        // Formato de fecha para la salida
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            // Formatear la fecha y devolverla
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            // Manejar la excepción si ocurre un error al parsear la fecha
            return null;
        }
    } */

    public String cleanCorchetes(String cadena) {
        return cadena.replace("[", "").replace("]", "");
    }
}
