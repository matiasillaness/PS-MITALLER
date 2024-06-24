package com.mitaller.modulos.turnos.service;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mitaller.modulos.turnos.modelos.OrdenDisponibleResponse;
import com.mitaller.modulos.turnos.modelos.OrdenParcialRequest;
import com.mitaller.modulos.turnos.modelos.OrdenRequest;
import com.mitaller.modulos.turnos.modelos.OrdenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public interface OrdenService {
   Boolean guardarOrdenDesdeEmpleadoOAdministrador(List<OrdenParcialRequest> ordenes);
   List<OrdenDisponibleResponse> obtenerOrdenesDisponibles();
   List<OrdenResponse> obtenerFechasOcupadas();
   List<OrdenResponse> obtenerOrdenes(); //revisar porque tiene relacion con mercado pago
   @Transactional
   String guardarOrden(OrdenRequest orden, Long idOrden);
   void borrarOrdenesViejasQueNoEstanOcupadas();
   Boolean confirmarOrden(String urlMerchantOrder) throws MPException, MPApiException;
   public boolean eliminarOrdenDisponible(Long idOrden);
}
