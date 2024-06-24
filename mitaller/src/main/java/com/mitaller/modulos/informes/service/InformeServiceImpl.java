package com.mitaller.modulos.informes.service;

import com.mitaller.modulos.cobros.repositorio.CompraRepository;
import com.mitaller.modulos.cobros.repositorio.VentaRepository;
import com.mitaller.modulos.informes.service.impl.InformeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InformeServiceImpl implements InformeService {
    private final VentaRepository ventaRepository;
    private final CompraRepository compraRepository;

    public InformeServiceImpl(VentaRepository ventaRepository, CompraRepository compraRepository) {
        this.ventaRepository = ventaRepository;
        this.compraRepository = compraRepository;
    }


    @Override
    public List<BigDecimal> mesesConMasPlataFacturada() {
        //la posicion 0 equivale a enero, la posicion 1 a febrero y asi sucesivamente
        List<BigDecimal> meses = new ArrayList<>();




        return meses;
    }

    @Override
    public List<BigDecimal> mesesConMasPlataInvertida() {
        return null;
    }

    @Override
    public List<Integer> cantidadDeVentaPorMes() {
        return null;
    }
}
