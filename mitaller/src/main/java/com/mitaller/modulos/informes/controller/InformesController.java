package com.mitaller.modulos.informes.controller;

import com.mitaller.modulos.cobros.dto.InfoServicioUtilizado;
import com.mitaller.modulos.cobros.dto.MesTotalDTO;
import com.mitaller.modulos.cobros.repositorio.CompraRepository;
import com.mitaller.modulos.cobros.repositorio.VentaRepository;
import com.mitaller.modulos.cobros.services.VentaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;


@RestController
public class InformesController {

    private final CompraRepository compraRepository;
    private final VentaRepository ventaRepository;

    private final VentaService ventaService;

    public InformesController(CompraRepository compraRepository, VentaRepository ventaRepository, VentaService ventaService) {
        this.compraRepository = compraRepository;
        this.ventaRepository = ventaRepository;
        this.ventaService = ventaService;
    }
    public List<BigDecimal> mesesConMasPlataFacturada() {
        return null;
    }

    @GetMapping("/mesesConMasPlataInvertida")
    public List<MesTotalDTO> mesesConMasPlataInvertida(@RequestParam(value = "year", required = false) Integer year) {
        // Si el año no se proporciona, se asume el año actual
        if (year == null) {
            year = Year.now().getValue();
        }

        // Obtener datos de la base de datos para el año dado
        List<MesTotalDTO> datosDisponibles = compraRepository.findTotalByMonth(year);

        // Crear una lista con todos los meses del año con total inicial 0
        List<MesTotalDTO> todosLosMeses = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            todosLosMeses.add(new MesTotalDTO(year, i, BigDecimal.ZERO));
        }

        // Actualizar la lista todosLosMeses con los datos disponibles
        for (MesTotalDTO dato : datosDisponibles) {
            for (MesTotalDTO mes : todosLosMeses) {
                if (mes.getMonth() == dato.getMonth()) {
                    mes.setTotal(dato.getTotal());
                    break;
                }
            }
        }

        return todosLosMeses;
    }


    @GetMapping("/cantidadDeVentaPorMes")
    public List<MesTotalDTO> cantidadDeVentaPorMes(@RequestParam(value = "year", required = false) Integer year) {
        // Si el año no se proporciona, se asume el año actual
        if (year == null) {
            year = Year.now().getValue();
        }

        // Obtener datos de la base de datos para el año dado
        List<MesTotalDTO> datosDisponibles = ventaRepository.findTotalByMonth(year);

        // Crear una lista con todos los meses del año con total inicial 0
        List<MesTotalDTO> todosLosMeses = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            todosLosMeses.add(new MesTotalDTO(year, i, BigDecimal.ZERO));
        }

        // Actualizar la lista todosLosMeses con los datos disponibles
        for (MesTotalDTO dato : datosDisponibles) {
            for (MesTotalDTO mes : todosLosMeses) {
                if (mes.getMonth() == dato.getMonth()) {
                    mes.setTotal(dato.getTotal());
                    break;
                }
            }
        }

        return todosLosMeses;
    }

    @GetMapping("/serviciosMasUtilizados")
    public List<InfoServicioUtilizado> serviciosMasUtilizados() {
       return ventaService.obtenerServiciosMasUtilizados();
    }


    @GetMapping("/totalFacturado")
    public Integer totalFacturado() {
        return ventaRepository.getTotalFacturado();
    }


    @GetMapping("/totalGastado")
    public Integer totalGastado() {
        return compraRepository.getTotalGastado();
    }
}
