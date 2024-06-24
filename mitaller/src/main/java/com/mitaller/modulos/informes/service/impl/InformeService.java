package com.mitaller.modulos.informes.service.impl;

import java.math.BigDecimal;
import java.util.List;

public interface InformeService {
    public List<BigDecimal> mesesConMasPlataFacturada();

    public List<BigDecimal> mesesConMasPlataInvertida() ;

    public List<Integer> cantidadDeVentaPorMes() ;
}
