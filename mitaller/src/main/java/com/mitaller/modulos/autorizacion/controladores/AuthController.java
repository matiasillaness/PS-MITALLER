package com.mitaller.modulos.autorizacion.controladores;

import com.mitaller.modulos.autorizacion.models.request.LoginRequest;
import com.mitaller.modulos.autorizacion.models.request.RegisterRequest;
import com.mitaller.modulos.autorizacion.models.response.AuthResponse;
import com.mitaller.modulos.autorizacion.servicios.AuthService;
import com.mitaller.modulos.autorizacion.servicios.AuthServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-employer")
    public ResponseEntity<AuthResponse> registerEmployer(@RequestBody RegisterRequest request) {
        return authService.registerEmployer(request);
    }
}
