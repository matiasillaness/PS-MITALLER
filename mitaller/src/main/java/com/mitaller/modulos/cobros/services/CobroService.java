package com.mitaller.modulos.cobros.services;

import com.mitaller.modulos.cobros.modelos.CompraRequest;
import com.mitaller.modulos.cobros.modelos.CompraResponse;
import com.mitaller.modulos.cobros.modelos.FilterCompra;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CobroService {
    ResponseEntity<CompraResponse> registrarCompra(CompraRequest compra);
    ResponseEntity<CompraResponse> obtenerCompra(String nombreComprobante);
    ResponseEntity<CompraResponse> darDeBajaCompra(String id);
    ResponseEntity<List<CompraResponse>> obtenerCompras(FilterCompra filterCompra);

    ResponseEntity<List<String>> getTipoPagos();
    ResponseEntity<List<String>> getTipoFacturas();
}
