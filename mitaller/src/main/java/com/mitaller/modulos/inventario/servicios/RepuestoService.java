package com.mitaller.modulos.inventario.servicios;

import com.mitaller.modulos.inventario.modelos.FiltersRepuesto;
import com.mitaller.modulos.inventario.modelos.RepuestoRequest;
import com.mitaller.modulos.inventario.modelos.RepuestoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface RepuestoService {
    ResponseEntity<RepuestoResponse> obtenerRepuestoPorId(Long idRepuesto);
    ResponseEntity<RepuestoResponse> crearRepuesto(RepuestoRequest repuestoRequest);
    ResponseEntity<RepuestoResponse> actualizarRepuesto(Long idRepuesto, RepuestoRequest repuestoRequest);
    ResponseEntity<RepuestoResponse> eliminarRepuesto(Long idRepuesto);
    ResponseEntity<List<RepuestoResponse>>  obtenerRepuestoPorPrecioMayorOMenorQue(BigDecimal precio, String tipo) throws IllegalAccessException;
    ResponseEntity<List<RepuestoResponse>> obtenerRepuestos(FiltersRepuesto filtersRepuesto);

    ResponseEntity<RepuestoResponse> activarOInactivarRepuesto(Long idRepuesto, Boolean activo);
}
