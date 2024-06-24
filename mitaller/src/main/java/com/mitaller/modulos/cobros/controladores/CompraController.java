package com.mitaller.modulos.cobros.controladores;


import com.mitaller.modulos.cobros.modelos.CompraRequest;
import com.mitaller.modulos.cobros.modelos.CompraResponse;
import com.mitaller.modulos.cobros.modelos.FilterCompra;
import com.mitaller.modulos.cobros.services.CobroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
//si anda mal cambiar el nombrexd
@RestController
public class CompraController {
    private final CobroService cobroService;

    public CompraController(CobroService cobroService) {
        this.cobroService = cobroService;
    }




    @PostMapping("/compras")
    public ResponseEntity<CompraResponse> registrarCompra(
            @RequestBody CompraRequest compra
    ) {
        return cobroService.registrarCompra(compra);
    }



    @GetMapping("/compras")
    public ResponseEntity<List<CompraResponse>> obtenerCompras(
            @RequestParam(value = "nombreComprobante", required = false) String nombreComprobante,
            @RequestParam(value = "fecha_inicio", required = false) String fechaInicio,
            @RequestParam(value = "fecha_fin", required = false) String fechaFin,
            @RequestParam(value = "proveedor", required = false) String proveedor,
            @RequestParam(value = "tipoPago", required = false) String tipoPago,
            @RequestParam(value = "totalMayorA", required = false) BigDecimal totalMayorA,
            @RequestParam(value = "totalMenorA", required = false) BigDecimal totalMenorA,
            @RequestParam(value = "dadaDeBaja", required = false) Boolean dadaDeBaja,
            @RequestParam(value = "numeroDeTelefono", required = false) String numeroDeTelefono,
            @RequestParam(value = "email", required = false) String email

    ) {
        FilterCompra filterCompra = FilterCompra.builder()
                .nombreCompra(nombreComprobante)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .proveedor(proveedor)
                .tipoPago(tipoPago)
                .totalMayorA(totalMayorA)
                .totalMenorA(totalMenorA)
                .fechaFin(fechaInicio)
                .numeroDeTelefono(numeroDeTelefono)
                .email(email)
                .dadaDeBaja(dadaDeBaja)
                .build();
        return cobroService.obtenerCompras(filterCompra);
    }



    @GetMapping("/compras/{nombreComprobante}")
    public ResponseEntity<CompraResponse> obtenerCompra(@PathVariable String nombreComprobante) {
        return cobroService.obtenerCompra(nombreComprobante);
    }



    @DeleteMapping("/compras/{nombreComprobante}/darDeBaja")
    public ResponseEntity<CompraResponse> darDeBajaCompra(@PathVariable String nombreComprobante) {
        return cobroService.darDeBajaCompra(nombreComprobante);
    }






    @GetMapping("/compras/tipoPagos")
    public ResponseEntity<List<String>> getTipoPagos() {
        return cobroService.getTipoPagos();
    }



    @GetMapping("/compras/tipoFacturas")
    public ResponseEntity<List<String>> getTipoFacturas() {
        return cobroService.getTipoFacturas();
    }

}
