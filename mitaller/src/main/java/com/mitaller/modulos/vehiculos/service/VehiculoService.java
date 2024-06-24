package com.mitaller.modulos.vehiculos.service;

import com.mitaller.modulos.vehiculos.modelos.FiltersRequestService;
import com.mitaller.modulos.vehiculos.modelos.VehiculoRequest;
import com.mitaller.modulos.vehiculos.modelos.VehiculoResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface VehiculoService {
    boolean crearVehiculo(VehiculoRequest vehiculoRequest);
    boolean actualizarVehiculo(VehiculoRequest vehiculoRequest, Long id);
    boolean eliminarVehiculo(Long id);
    List<VehiculoResponse> obtenerVehiculos(FiltersRequestService filtersRequestService) throws IllegalAccessException;
    VehiculoResponse obtenerVehiculoPorId(Long id) throws IllegalAccessException;
    List<String> obtenerColores() throws IllegalAccessException;
    List<String> obtenerTiposVehiculos() throws IllegalAccessException;

    VehiculoResponse obtenerVehiculoPorPlaca(String patente) throws IllegalAccessException;
    boolean activarODesactivarVehiculo(Long id, boolean activo);
}
