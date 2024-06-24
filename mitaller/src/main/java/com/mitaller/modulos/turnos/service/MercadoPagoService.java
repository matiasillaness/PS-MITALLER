package com.mitaller.modulos.turnos.service;

import com.mitaller.modulos.turnos.modelos.OrdenPreferenciaRequest;
import com.mitaller.modulos.turnos.modelos.OrdenRequest;
import org.springframework.stereotype.Service;


@Service
public interface MercadoPagoService {

    //webhook
    void cambiarEstadoOrden(String ordenId, String estado);
    String crearPreferencia(OrdenPreferenciaRequest ordenRequest, String dia);
}
