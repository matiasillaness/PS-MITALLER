package com.mitaller.modulos.proveedores.controladores;


import com.mitaller.modulos.proveedores.dominio.Proveedor;
import com.mitaller.modulos.proveedores.modelos.FilterProveedor;
import com.mitaller.modulos.proveedores.modelos.ProveedorRequest;
import com.mitaller.modulos.proveedores.modelos.ProveedorResponseDTO;
import com.mitaller.modulos.proveedores.service.ProveedorService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProveedorController {

    public final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }


    @Operation(summary = "Crear proveedor", description = "Crea un nuevo proveedor", tags = {"proveedores"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor creado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Proveedor ya existe"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PostMapping("/proveedor")
    public ResponseEntity<Boolean> crearProveedor(@Valid @RequestBody ProveedorRequest proveedorRequest) {
        return proveedorService.crearProveedor(proveedorRequest);
    }


    @Operation(summary = "Actualizar proveedor", description = "Actualiza un proveedor", tags = {"proveedores"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor actualizado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @PutMapping("/proveedor/{idProveedor}")
    public ResponseEntity<Boolean> actualizarProveedor(@Valid @RequestBody ProveedorRequest proveedorRequest, @PathVariable Long idProveedor) {
        return proveedorService.actualizarProveedor(proveedorRequest, idProveedor);
    }


    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor", tags = {"proveedores"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedor eliminado"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @DeleteMapping("/proveedor/{idProveedor}")
    public ResponseEntity<Boolean> eliminarProveedor(@PathVariable Long idProveedor) {
        return proveedorService.eliminarProveedor(idProveedor);
    }


    @Operation(summary = "Obtener proveedores", description = "Obtiene todos los proveedores", tags = {"proveedores"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Proveedores encontrados"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno")
            })
    @GetMapping("/proveedores")
    public ResponseEntity<List<ProveedorResponseDTO>> obtenerProveedores(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "numeroDeTelefono", required = false) String numeroDeTelefono,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "tipoProveedor", required = false) String tipoProveedor,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "300") int size
    ) {
        FilterProveedor filterProveedor = FilterProveedor.builder()
                .nombre(nombre)
                .telefono(numeroDeTelefono)
                .size(size)
                .page(page)
                .tipoProveedor(tipoProveedor)
                .email(email)
                .build();
        return proveedorService.obtenerProveedor(filterProveedor);
    }

}
