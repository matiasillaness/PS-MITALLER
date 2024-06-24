package com.mitaller.modulos.vehiculos.service;

import com.mitaller.modulos.vehiculos.modelos.MarcaResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarcaService {
    ResponseEntity<List<MarcaResponse>> obtenerMarcas(String nombre, Long id, Boolean activo) throws IllegalAccessException;

    ResponseEntity<MarcaResponse> obtenerMarcaPorId(Long id);

    ResponseEntity<Boolean> crearMarca(String nombre);

    ResponseEntity<Boolean> actualizarMarca(Long id, String nuevoNombre);

    ResponseEntity<Boolean> eliminarMarca(Long id);

    ResponseEntity<Boolean> activarODesactivarMarca(Long id, boolean activo);
}
