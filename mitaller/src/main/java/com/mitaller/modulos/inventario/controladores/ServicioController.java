package com.mitaller.modulos.inventario.controladores;


import com.mitaller.modulos.inventario.modelos.FilterServicio;
import com.mitaller.modulos.inventario.modelos.ServicioRequest;
import com.mitaller.modulos.inventario.modelos.ServicioResponse;
import com.mitaller.modulos.inventario.servicios.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ServicioController {
    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }


    @Operation(summary = "Crear servicio", description = "Crea un nuevo servicio", tags = {"servicios"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicio creado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Servicio ya existe"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PostMapping("/servicios")
    public ResponseEntity<ServicioResponse> crearServicio(@Valid @RequestBody ServicioRequest servicioRequest) throws IllegalAccessException {
        return servicioService.crearServicio(servicioRequest);
    }


    @Operation(summary = "Actualizar servicio", description = "Actualiza un servicio", tags = {"servicios"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicio actualizado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PutMapping("/servicios/{id}")
    public ResponseEntity<ServicioResponse> actualizarServicio( @Valid @RequestBody ServicioRequest servicioRequest,
                                                                @PathVariable Long id) {
        try {
            return servicioService.actualizarServicio(id, servicioRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio", tags = {"servicios"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicio eliminado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<ServicioResponse> eliminarServicio(@PathVariable Long id) {
        try {
            return servicioService.eliminarServicio(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @Operation(summary = "Obtener servicios", description = "Obtiene todos los servicios", tags = {"servicios"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicios encontrados"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/servicios")
    public ResponseEntity<List<ServicioResponse>> obtenerServicios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) BigDecimal precio,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size

    ) throws IllegalAccessException {

        FilterServicio filtersServicio = FilterServicio.builder()
                .activo(activo)
                .nombre(nombre)
                .tipo(tipo)
                .size(size)
                .precio(precio)
                .page(page)
                .build();

        return servicioService.obtenerServicios(filtersServicio);
    }


    @Operation(summary = "Obtener servicio por id", description = "Obtiene un servicio por su id", tags = {"servicios"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicio encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/servicios/{id}")
    public ResponseEntity<ServicioResponse> obtenerServicioPorId(@PathVariable Long id) {
        try {
            ServicioResponse servicioResponse = servicioService.obtenerServicio(id).getBody();
            if (servicioResponse == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(servicioResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Obtener servicios por precio mayor o menor que", description = "Obtiene los servicios por precio mayor o menor que", tags = {"servicios"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicios encontrados"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/servicios/mayor-menor")
    public ResponseEntity<List<ServicioResponse>> obtenerServicioPorPrecioMayorOMenorQue(
            @RequestParam(required = false) BigDecimal precio,
            @RequestParam(required = false) String tipo
    ) {
        try {
            return servicioService.obtenerServicioPorPrecioMayorOMenorQue(precio, tipo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Activar o inactivar servicio", description = "Activa o inactiva un servicio", tags = {"servicios"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Servicio activado o inactivado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PatchMapping("/servicios/activar-inactivar")
    public ResponseEntity<ServicioResponse> activarOInactivarServicio(@RequestParam Long id, @RequestParam Boolean activo) {
        try {
            return servicioService.activarOInactivarServicio(id, activo);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al activar o inactivar el servicio");
        }
    }
}
