package com.mitaller.modulos.turnos.controladores;

import com.mitaller.modulos.turnos.modelos.OrdenDisponibleResponse;
import com.mitaller.modulos.turnos.modelos.OrdenParcialRequest;
import com.mitaller.modulos.turnos.modelos.OrdenRequest;
import com.mitaller.modulos.turnos.modelos.OrdenResponse;
import com.mitaller.modulos.turnos.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController()
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @PostMapping(value = "/orden/guardar-parcial")
    public ResponseEntity<Boolean> guardarOrdenParcial(@RequestBody List<OrdenParcialRequest> ordenes) {
        return ResponseEntity.ok(ordenService.guardarOrdenDesdeEmpleadoOAdministrador(ordenes));
    }

    @PostMapping(value = "/orden/guardar/{idOrden}")
    public ResponseEntity<String> guardarOrden(@RequestBody OrdenRequest orden, @PathVariable Long idOrden) {
        return ResponseEntity.ok(ordenService.guardarOrden(orden, idOrden));
    }

    @GetMapping(value = "/orden/disponibles")
    public ResponseEntity<List<OrdenDisponibleResponse>> obtenerOrdenesDisponibles() {
        return ResponseEntity.ok(ordenService.obtenerOrdenesDisponibles());
    }

    @DeleteMapping(value = "/orden/eliminar/{idOrden}")
    public ResponseEntity<Boolean> eliminarOrden(@PathVariable Long idOrden) {
        return ResponseEntity.ok(ordenService.eliminarOrdenDisponible(idOrden));
    }
    @GetMapping(value = "/orden/ocupadas")
    public ResponseEntity<List<OrdenResponse>> obtenerOrdenesOcupadas() {
        return ResponseEntity.ok(ordenService.obtenerFechasOcupadas());
    }
    //todo: Test this method
    @Scheduled(cron = "0 0/30 * * * ?")
    public void borrarOrdenesPasadasDeTiempo() {
        ordenService.borrarOrdenesViejasQueNoEstanOcupadas();
    }
}
