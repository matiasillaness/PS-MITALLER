package com.mitaller.modulos.inventario.servicios;


import com.mitaller.modulos.inventario.modelos.FilterServicio;
import com.mitaller.modulos.inventario.modelos.ServicioRequest;
import com.mitaller.modulos.inventario.modelos.ServicioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ServicioService {
    ResponseEntity<ServicioResponse> obtenerServicio(Long idServicio) throws IllegalAccessException;
    ResponseEntity<ServicioResponse> crearServicio(ServicioRequest servicioRequest) throws IllegalAccessException;
    ResponseEntity<ServicioResponse> actualizarServicio(Long idServicio, ServicioRequest servicioRequest) throws IllegalAccessException;
    ResponseEntity<ServicioResponse> eliminarServicio(Long idServicio);
    ResponseEntity<List<ServicioResponse>> obtenerServicioPorPrecioMayorOMenorQue(BigDecimal precio, String tipo) throws IllegalAccessException;
    ResponseEntity<List<ServicioResponse>> obtenerServicios(FilterServicio filters) throws IllegalAccessException;

    ResponseEntity<ServicioResponse> activarOInactivarServicio(Long idServicio, Boolean activo) throws IllegalAccessException;
}
