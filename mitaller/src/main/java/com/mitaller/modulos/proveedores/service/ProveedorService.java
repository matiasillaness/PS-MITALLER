package com.mitaller.modulos.proveedores.service;


import com.mitaller.modulos.proveedores.modelos.FilterProveedor;
import com.mitaller.modulos.proveedores.modelos.ProveedorRequest;
import com.mitaller.modulos.proveedores.modelos.ProveedorResponseDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProveedorService {
    ResponseEntity<Boolean> crearProveedor(ProveedorRequest proveedorRequest);
    ResponseEntity<Boolean> actualizarProveedor(ProveedorRequest proveedorRequest, Long idProveedor);
    ResponseEntity<Boolean> eliminarProveedor(Long id);

    //ResponseEntity<Boolean> bajaLogicaProveedor(Long id);
    ResponseEntity<List<ProveedorResponseDTO>> obtenerProveedor(FilterProveedor filterProveedor);
    ResponseEntity<ProveedorResponseDTO> obtenerProveedorPorId(Long idProveedor);
}
