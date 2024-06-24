package com.mitaller.modulos.vehiculos.controller;


import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import com.mitaller.modulos.vehiculos.modelos.FiltersRequestService;
import com.mitaller.modulos.vehiculos.modelos.VehiculoRequest;
import com.mitaller.modulos.vehiculos.modelos.VehiculoResponse;
import com.mitaller.modulos.vehiculos.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
public class VehiculoController {

        private final VehiculoService vehiculoService;

        public VehiculoController(VehiculoService vehiculoService) {
            this.vehiculoService = vehiculoService;
        }

        @Operation(summary = "Obtener vehiculos", description = "Obtiene todos los vehiculos", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehiculos encontrados"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @GetMapping("/vehiculos")
        public ResponseEntity<List<VehiculoResponse>> obtenerVehiculos(
                @RequestParam(required = false) String modelo,
                @RequestParam(required = false) String tipoVehiculo,
                @RequestParam(required = false) String patente,
                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                @RequestParam(required = false) Boolean activo
        ) {
            FiltersRequestService filtersRequestService = FiltersRequestService.builder()
                    .tipoVehiculo(tipoVehiculo)
                    .size(size)
                    .page(page)
                    .modelo(modelo)
                    .patente(patente)
                    .activo(activo)
                    .build();

            try {
                return new ResponseEntity<>(vehiculoService.obtenerVehiculos(filtersRequestService), HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(null);
            }
        }


        @Operation(summary = "Obtener vehiculo por id", description = "Obtiene un vehiculo por su id", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Vehiculo no encontrado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @GetMapping("/vehiculos/{id}")
        public ResponseEntity<VehiculoResponse> obtenerVehiculoPorId(@PathVariable Long id) {
            try {
                VehiculoResponse vehiculoResponse = vehiculoService.obtenerVehiculoPorId(id);
                if (vehiculoResponse == null) {
                    return ResponseEntity.status(404).body(null);
                }
                return new ResponseEntity<>(vehiculoResponse, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(null);
            }
        }

        @Operation(summary = "Obtener vehiculo por placa", description = "Obtiene un vehiculo por su placa", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Vehiculo no encontrado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @GetMapping("/vehiculos/patente/{patente}")
        public ResponseEntity<VehiculoResponse> obtenerVehiculoPorPlaca(@PathVariable String patente) {
            try {
                VehiculoResponse vehiculoResponse = vehiculoService.obtenerVehiculoPorPlaca(patente);
                if (vehiculoResponse == null) {
                    return ResponseEntity.status(404).body(null);
                }
                return new ResponseEntity<>(vehiculoResponse, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(null);
            }
        }

        @Operation(summary = "Crear vehiculo", description = "Crea un nuevo vehiculo", tags = {"vehiculos"},
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Vehiculo creado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno, vehiculo ya existente")
                })
        @PostMapping("/vehiculos")
        public ResponseEntity<Boolean> crearVehiculo(@Valid @RequestBody VehiculoRequest vehiculoRequest) throws Exception {
          return new ResponseEntity<>(vehiculoService.crearVehiculo(vehiculoRequest), HttpStatus.CREATED);
        }


        @Operation(summary = "Actualizar vehiculo", description = "Actualiza un vehiculo", tags = {"vehiculos"},
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehiculo actualizado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @PutMapping("/vehiculos/{id}")
        public ResponseEntity<Boolean> actualizarVehiculo(@RequestBody VehiculoRequest vehiculoRequest,
                                                          @PathVariable Long id) {
            try {
                return new ResponseEntity<>(vehiculoService.actualizarVehiculo(vehiculoRequest, id), HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(false);
            }
        }


        @Operation(summary = "Eliminar vehiculo", description = "Elimina un vehiculo", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehiculo eliminado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @DeleteMapping("/vehiculos/{id}")
        public ResponseEntity<Boolean> eliminarVehiculo(@PathVariable Long id) {
            try {
                return new ResponseEntity<>(vehiculoService.eliminarVehiculo(id), HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(false);
            }
        }


        @Operation(summary = "Obtener colores", description = "Obtiene todos los colores de los vehiculos", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Colores encontrados"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @GetMapping("/vehiculos/colores")
        public ResponseEntity<List<String>> obtenerColores() {
            try {
                return new ResponseEntity<>(vehiculoService.obtenerColores(), HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(null);
            }
        }


        @Operation(summary = "Obtener tipos de vehiculos", description = "Obtiene todos los tipos de vehiculos", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tipos de vehiculos encontrados"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @GetMapping("/vehiculos/tipos")
        public ResponseEntity<List<String>> obtenerTiposVehiculos() throws Exception {
            try {
                return new ResponseEntity<>(vehiculoService.obtenerTiposVehiculos(), HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }


        @Operation(summary = "Activar o desactivar vehiculo", description = "Activa o desactiva un vehiculo", tags = {"vehiculos"},
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehiculo activado o desactivado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
                })
        @PatchMapping("/vehiculos/activar-desactivar/{id}")
        public ResponseEntity<Boolean> activarODesactivarVehiculo(@PathVariable Long id, @RequestParam  boolean activo) {
            try {
                return new ResponseEntity<>(vehiculoService.activarODesactivarVehiculo(id, activo), HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(500).body(false);
            }
        }
}
