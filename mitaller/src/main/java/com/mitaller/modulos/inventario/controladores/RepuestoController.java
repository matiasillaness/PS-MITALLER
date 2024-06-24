package com.mitaller.modulos.inventario.controladores;


import com.mitaller.modulos.inventario.modelos.FiltersRepuesto;
import com.mitaller.modulos.inventario.modelos.RepuestoRequest;
import com.mitaller.modulos.inventario.modelos.RepuestoResponse;
import com.mitaller.modulos.inventario.servicios.RepuestoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class RepuestoController {
    private final RepuestoService repuestoService;

    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }


    @Operation(summary = "Obtener repuestos", description = "Obtiene todos los repuestos", tags = {"repuestos"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuestos encontrados"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/repuestos")
    public ResponseEntity<List<RepuestoResponse>> obtenerRepuestos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) BigDecimal precio,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(required = false) Boolean activo
    ) {
        FiltersRepuesto filtersRepuesto = FiltersRepuesto.builder()
                .activo(activo)
                .nombre(nombre)
                .marca(marca)
                .size(size)
                .precio(precio)
                .page(page)
                .build();

        filtersRepuesto.convertirAMayusculas();

        return repuestoService.obtenerRepuestos(filtersRepuesto);
    }

    @Operation(summary = "Crear repuesto", description = "Crea un nuevo repuesto", tags = {"repuestos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto creado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto ya existe"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PostMapping("/repuestos")
    public ResponseEntity<RepuestoResponse> crearRepuesto(@Valid @RequestBody RepuestoRequest repuestoRequest) {
        return repuestoService.crearRepuesto(repuestoRequest);
    }


    @Operation(summary = "Actualizar repuesto", description = "Actualiza un repuesto", tags = {"repuestos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto actualizado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PutMapping("/repuestos/{id}")
    public ResponseEntity<RepuestoResponse> actualizarRepuesto(@PathVariable Long id, @Valid @RequestBody RepuestoRequest repuestoRequest) throws IllegalAccessException {
        return repuestoService.actualizarRepuesto(id, repuestoRequest);
    }


    @Operation(summary = "Eliminar repuesto", description = "Elimina un repuesto", tags = {"repuestos"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto eliminado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @DeleteMapping("/repuestos/{id}")
    public ResponseEntity<RepuestoResponse> eliminarRepuesto(@PathVariable Long id) throws IllegalAccessException {
        return repuestoService.eliminarRepuesto(id);
    }


    @Operation(summary = "obtener un repuesto mayor o menor que segun el precio", description = "Obtiene un repuesto mayor o menor que segun el precio", tags = {"repuestos"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/repuestos/precio")
    public ResponseEntity<List<RepuestoResponse>> obtenerRepuestoPorPrecioMayorOMenorQue(
            @RequestParam BigDecimal precio,
            @RequestParam String tipo
    ) throws IllegalAccessException {
        return repuestoService.obtenerRepuestoPorPrecioMayorOMenorQue(precio, tipo);
    }

    @Operation(summary = "Obtener repuesto por id", description = "Obtiene un repuesto por id", tags = {"repuestos"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/repuestos/{id}")
    public ResponseEntity<RepuestoResponse> obtenerRepuestoPorId(@PathVariable Long id){
        return repuestoService.obtenerRepuestoPorId(id);
    }


    @Operation(summary = "Activar o desactivar repuesto", description = "Activa o desactiva un repuesto", tags = {"repuestos"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto activado o desactivado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PatchMapping("/repuestos/activar-inactivar")
    public ResponseEntity<RepuestoResponse> activarOInactivarRepuesto(@RequestParam Long id, @RequestParam Boolean activo) throws IllegalAccessException {
        return repuestoService.activarOInactivarRepuesto(id, activo);
    }

}
