package com.mitaller.modulos.cobros.controladores;


import com.mitaller.modulos.cobros.modelos.FilterVenta;
import com.mitaller.modulos.cobros.modelos.VentaRequest;
import com.mitaller.modulos.cobros.modelos.VentaResponse;
import com.mitaller.modulos.cobros.services.VentaService;
import com.mitaller.modulos.comun.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }


    @PostMapping("/venta")
    public ResponseEntity<VentaResponse> registrarVenta(@RequestBody VentaRequest venta) {
        return ventaService.registrarVenta(venta);
    }

    @DeleteMapping("/venta/eliminar/{nombre}")
    public ResponseEntity<Boolean> eliminarVenta(@PathVariable String nombre) throws ApiException {
        return ventaService.anularVenta(nombre);
    }

    @GetMapping("/venta/{nombre}")
    public ResponseEntity<VentaResponse> obtenerVenta(@PathVariable String nombre) throws ApiException {
        return ventaService.obtenerVenta(nombre);
    }

    @GetMapping("/venta")
    public ResponseEntity<List<VentaResponse>> obtenerVentas(@RequestParam (required = false) String fechaInicio, @RequestParam (required = false) String fechaFin, @RequestParam (required = false) String tipoPago, @RequestParam (required = false) String tipoFactura,@RequestParam (required = false) String numeroFactura, @RequestParam (required = false) String descripcion, @RequestParam (required = false) String cliente, @RequestParam (required = false) Boolean dadaDeBaja) {
        FilterVenta filterVenta = FilterVenta.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .tipoPago(tipoPago)
                .tipoFactura(tipoFactura)
                .numeroFactura(numeroFactura)
                .descripcion(descripcion)
                .cliente(cliente)
                .dadaDeBaja(dadaDeBaja)
                .build();

        return ventaService.obtenerVentas(filterVenta);
    }
}
