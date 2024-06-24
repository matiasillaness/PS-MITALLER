package com.mitaller.modulos.inventario.servicios;


import com.mitaller.modulos.vehiculos.modelos.MarcaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarcaRepuestoService {
    ResponseEntity<List<MarcaResponse>> obtenerMarcas(String nombre, Long id, Boolean activo) throws IllegalAccessException;

    ResponseEntity<MarcaResponse> obtenerMarcaPorId(Long id) throws IllegalAccessException;

    ResponseEntity<Boolean> crearMarca(String nombre);

    ResponseEntity<Boolean> actualizarMarca(Long id, String nuevoNombre);

    ResponseEntity<Boolean> eliminarMarca(Long id);

    ResponseEntity<Boolean> activarODesactivarMarca(Long id, boolean activo);
}
