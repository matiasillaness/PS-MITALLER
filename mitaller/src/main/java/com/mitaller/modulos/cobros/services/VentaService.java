package com.mitaller.modulos.cobros.services;

import com.mitaller.modulos.cobros.dto.InfoServicioUtilizado;
import com.mitaller.modulos.cobros.modelos.FilterVenta;
import com.mitaller.modulos.cobros.modelos.VentaRequest;
import com.mitaller.modulos.cobros.modelos.VentaResponse;
import com.mitaller.modulos.comun.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface VentaService {
   ResponseEntity<VentaResponse> registrarVenta(VentaRequest venta) ;
    ResponseEntity<Boolean> anularVenta(String idVenta) throws ApiException;
    ResponseEntity<List<String>> tiposPago();
    ResponseEntity<List<String>> tiposFactura();
    ResponseEntity<List<VentaResponse>> obtenerVentas(FilterVenta filterVenta);
    ResponseEntity<VentaResponse> obtenerVenta(String nombreComprobante) throws ApiException;
   List<InfoServicioUtilizado> obtenerServiciosMasUtilizados();
}
