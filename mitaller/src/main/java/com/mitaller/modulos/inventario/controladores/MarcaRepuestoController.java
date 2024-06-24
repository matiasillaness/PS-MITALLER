package com.mitaller.modulos.inventario.controladores;
import com.mitaller.modulos.inventario.servicios.MarcaRepuestoService;
import com.mitaller.modulos.inventario.servicios.impl.MarcaRepuestoServiceImpl;
import com.mitaller.modulos.vehiculos.modelos.MarcaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
public class MarcaRepuestoController {


    private final MarcaRepuestoServiceImpl marcaService;

    public MarcaRepuestoController(MarcaRepuestoServiceImpl marcaService) {
        this.marcaService = marcaService;
    }


    @Operation(summary = "Obtener marcas", description = "Obtiene todas las marcas", tags = {"marcas-repuestos"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marcas encontradas"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("repuesto-marca")
    public ResponseEntity<List<MarcaResponse>> obtenerMarcas(
            @RequestParam(required = false) @Parameter(description = "Nombre de la marca") String nombre,
            @RequestParam(required = false) @Parameter(description = "Id de la marca") Long id,
            @RequestParam(required = false) @Parameter(description = "Estado de la marca") Boolean activo
    ) {
        return marcaService.obtenerMarcas(nombre, id, activo);
    }



    @Operation(summary = "Obtener marca por id", description = "Obtiene una marca por su id", tags = {"marcas-repuestos"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca encontrada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("repuesto-marca/{id}")
    public ResponseEntity<MarcaResponse> obtenerMarcaPorId(@PathVariable Long id){
        return marcaService.obtenerMarcaPorId(id);
    }



    @Operation(summary = "Crear marca", description = "Crea una nueva marca", tags = {"marcas-repuestos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca creada"),
                    @ApiResponse(responseCode = "404", description = "Marca ya existe"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PostMapping("repuesto-marca/crear-marca/{nombre}")
    public ResponseEntity<Boolean> crearMarca(@PathVariable String nombre) {
        return marcaService.crearMarca(nombre);
    }




    @Operation(summary = "Actualizar marca", description = "Actualiza el nombre de una marca", tags = {"marcas-repuestos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca actualizada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PutMapping("repuesto-marca/{id}/{nuevoNombre}")
    public ResponseEntity<Boolean> actualizarMarca( @PathVariable Long id, @PathVariable String nuevoNombre) {
        return marcaService.actualizarMarca(id, nuevoNombre);
    }



    @Operation(summary = "Eliminar marca", description = "Elimina una marca", tags = {"marcas-repuestos"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca eliminada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            })
    @DeleteMapping("repuesto-marca/{id}")
    public ResponseEntity<Boolean> eliminarMarca(@PathVariable Long id) {
        return marcaService.eliminarMarca(id);
    }



    @Operation(summary = "Activar o desactivar marca", description = "Activa o desactiva una marca", tags = {"marcas-repuestos"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca activada o desactivada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada"),
                    @ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PatchMapping("repuesto-marca/activar-desactivar/{id}")
    public ResponseEntity<Boolean> activarODesactivarMarca(@PathVariable Long id,
                                                           @RequestParam(required = true)
                                                           @Parameter(description = "Estado de la marca") Boolean activo) {
        return marcaService.activarODesactivarMarca(id, activo);
    }
}
