package com.mitaller.modulos.autorizacion.servicios;

import com.mitaller.modulos.autorizacion.models.request.LoginRequest;
import com.mitaller.modulos.autorizacion.models.request.RegisterRequest;
import com.mitaller.modulos.autorizacion.models.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface AuthService {
    ResponseEntity<AuthResponse> register(RegisterRequest request);
    ResponseEntity<AuthResponse> registerEmployer(RegisterRequest request);
    ResponseEntity<AuthResponse> login(LoginRequest request);
    //Method to register an admin only for the first time
    void registerOfAdmin(RegisterRequest request);
    ResponseEntity<AuthResponse> refreshToken(String refreshToken);
}
