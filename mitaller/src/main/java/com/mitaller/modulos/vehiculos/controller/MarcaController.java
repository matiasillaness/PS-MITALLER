package com.mitaller.modulos.vehiculos.controller;


import com.mitaller.modulos.vehiculos.modelos.MarcaResponse;
import com.mitaller.modulos.vehiculos.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @Operation(summary = "Obtener marcas", description = "Obtiene todas las marcas", tags = {"marcas"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Marcas encontradas"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/marcas")
    public ResponseEntity<List<MarcaResponse>> obtenerMarcas(
            @RequestParam(required = false) @Parameter(description = "Nombre de la marca") String nombre,
            @RequestParam(required = false) @Parameter(description = "Id de la marca") Long id,
            @RequestParam(required = false) @Parameter(description = "Estado de la marca") Boolean activo
            ) throws IllegalAccessException {
        return marcaService.obtenerMarcas(nombre, id, activo);
    }

    @Operation(summary = "Obtener marca por id", description = "Obtiene una marca por su id", tags = {"marcas"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Marca encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/marcas/{id}")
    public ResponseEntity<MarcaResponse> obtenerMarcaPorId(@PathVariable Long id) {
        return marcaService.obtenerMarcaPorId(id);
    }

    @Operation(summary = "Crear marca", description = "Crea una nueva marca", tags = {"marcas"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Marca creada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Marca ya existe"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PostMapping("/marcas/{nombre}")
    public ResponseEntity<Boolean> crearMarca(@PathVariable String nombre) {
        return marcaService.crearMarca(nombre);
    }


    @Operation(summary = "Actualizar marca", description = "Actualiza el nombre de una marca", tags = {"marcas"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Marca actualizada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PutMapping("/marcas/{id}/{nuevoNombre}")
    public ResponseEntity<Boolean> actualizarMarca( @PathVariable Long id,@PathVariable String nuevoNombre) {
        return marcaService.actualizarMarca(id, nuevoNombre);
    }

    @Operation(summary = "Eliminar marca", description = "Elimina una marca", tags = {"marcas"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Marca eliminada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<Boolean> eliminarMarca(@PathVariable Long id) {
        return marcaService.eliminarMarca(id);
    }



    @Operation(summary = "Activar o desactivar marca", description = "Activa o desactiva una marca", tags = {"marcas"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Marca activada o desactivada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PatchMapping("/marcas/activar-desactivar/{id}")
    public ResponseEntity<Boolean> activarODesactivarMarca(@PathVariable Long id,
                                                           @RequestParam(required = true)
                                                           @Parameter(description = "Estado de la marca") Boolean activo){
        return marcaService.activarODesactivarMarca(id, activo);
    }



}
