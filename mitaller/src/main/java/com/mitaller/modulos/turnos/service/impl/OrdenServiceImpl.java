package com.mitaller.modulos.turnos.service.impl;

import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mitaller.modulos.comun.servicios.SendMessageService;
import com.mitaller.modulos.turnos.dominio.EMercadoPagoEstado;
import com.mitaller.modulos.turnos.dominio.EOrdenEstado;
import com.mitaller.modulos.turnos.dominio.ETipoOrden;
import com.mitaller.modulos.turnos.dominio.Orden;
import com.mitaller.modulos.turnos.modelos.*;
import com.mitaller.modulos.turnos.repositorio.OrdenRepository;
import com.mitaller.modulos.turnos.service.MercadoPagoService;
import com.mitaller.modulos.turnos.service.OrdenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class OrdenServiceImpl implements OrdenService {

    @Value("${codigo.mercadolibre}")
    private String codigoMercadoLibre;

    private final OrdenRepository ordenRepository;
    private final MercadoPagoService mercadoPagoService;

    private final MerchantOrderClient merchantOrderClient;

    private final SendMessageService sendMessageService;

    public OrdenServiceImpl(OrdenRepository ordenRepository, MercadoPagoService mercadoPagoService, MerchantOrderClient merchantOrderClient, SendMessageService sendMessageService) {
        this.ordenRepository = ordenRepository;
        this.mercadoPagoService = mercadoPagoService;
        this.merchantOrderClient = merchantOrderClient;
        this.sendMessageService = sendMessageService;
    }
    @Override
    public List<OrdenResponse> obtenerOrdenes() {
        return null;
    }

    @Override
    public Boolean guardarOrdenDesdeEmpleadoOAdministrador(List<OrdenParcialRequest> ordenes) {
        if (ordenes == null) {
            throw new IllegalArgumentException("La lista de órdenes no puede ser nula");
        }

        for (OrdenParcialRequest orden : ordenes) {
            if (!orden.comprobarFechaYFormato()) {
                throw new IllegalArgumentException("El formato de fecha de la orden no es válido");
            }
        }

        try {
            for (OrdenParcialRequest orden : ordenes) {
                Orden nuevaOrden = Orden.builder()
                        .fecha(orden.getFecha())
                        .total(orden.getTotal())
                        .ocupada(false)
                        .build();
                ordenRepository.save(nuevaOrden);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public List<OrdenDisponibleResponse> obtenerOrdenesDisponibles() {
        try {
            // Obtener todas las órdenes no ocupadas
            List<Orden> ordenes = ordenRepository.findAllByOcupada(false);

            // Definir un formateador de fecha en caso de ser necesario
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            // Filtrar las órdenes que tienen una fecha posterior a la fecha y hora actual
            List<Orden> ordenesFiltradas = ordenes.stream()
                    .filter(orden -> {
                        try {
                            // Analizar la fecha usando el formateador
                            LocalDateTime fechaOrden = LocalDateTime.parse(orden.getFecha(), formatter);
                            return fechaOrden.isAfter(LocalDateTime.now());
                        } catch (DateTimeParseException e) {
                            // Si ocurre un error al analizar la fecha, se ignora esta orden
                            System.err.println("Error al analizar la fecha de la orden: " + e.getMessage());
                            return false;
                        }
                    })
                    .collect(Collectors.toList());

            // Convertir las órdenes filtradas a respuestas disponibles
            return convertirOrdenesADisponibles(ordenesFiltradas);
        } catch (Exception e) {
            // Manejar la excepción y devolver una lista vacía en lugar de null
            System.err.println("Error al obtener las órdenes disponibles: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<OrdenResponse> obtenerFechasOcupadas() {
        try {
            List<Orden> ordenes = ordenRepository.findAllByOcupada(true);
            return convertirOrdenesAOrdenesResponse(ordenes);
        } catch (Exception e) {
            return null;
        }
    }



    @Transactional
    @Override
    public String guardarOrden(OrdenRequest orden, Long idOrden) {
        Orden ordenGuardada = ordenRepository.findById(idOrden).orElse(null);
        String mensaje;

        if (ordenGuardada == null) throw new IllegalArgumentException("La orden no existe");
        if (ordenGuardada.getOcupada()) throw new IllegalArgumentException("La orden ya está ocupada");
        if (idOrden == null) throw new IllegalArgumentException("El id de la orden no puede ser nulo");

        ordenGuardada.setTipoOrden(ETipoOrden.valueOf(orden.getTipoOrden()));
        ordenGuardada.setOcupada(false);
        ordenGuardada.setPatenteDelVehiculo(orden.getPatenteDelVehiculo());
        ordenGuardada.setModeloDelVehiculo(orden.getModeloDelVehiculo());
        ordenGuardada.setRecordatorioEnviado(false);
        ordenGuardada.setEstadoOrden(EOrdenEstado.PENDIENTE);
        ordenGuardada.setEstadoMercadoPago(EMercadoPagoEstado.PENDIENTE);

        OrdenPreferenciaRequest ordenPreferenciaRequest = OrdenPreferenciaRequest.builder()
                .idOrden(ordenGuardada.getIdOrden())
                .total(ordenGuardada.getTotal())
                .ordenRequest(orden)
                .build();

        return mercadoPagoService.crearPreferencia(ordenPreferenciaRequest, ordenGuardada.getFecha());
    }

    @Override
    public void borrarOrdenesViejasQueNoEstanOcupadas() {

    }


    @Transactional
    @Override
    public Boolean confirmarOrden(String idOrden) throws MPException, MPApiException {
        String idMerchantOrder = extractNumberFromUrl(idOrden);

        assert idMerchantOrder != null;
        MerchantOrder merchantOrder  = merchantOrderClient.get(Long.valueOf(idMerchantOrder));

        if (merchantOrder == null) {
            return false;
        }

        String ordenId = merchantOrder.getItems().get(0).getId();
        String email = extractEmail(merchantOrder.getItems().get(0).getDescription());

        System.out.println("OrdenId: " + ordenId);
        System.out.println("Email: " + email);

        Orden orden = ordenRepository.findById(Long.valueOf(ordenId)).orElse(null);
        if (orden == null) {
            return false;
        }
        if (orden.getEstadoMercadoPago() == EMercadoPagoEstado.APROBADO) {
            return false;
        }
        if (merchantOrder.getPayments().get(0).getStatus().equals("rejected")) {
            return false;
        }

        sendMessageService.sendOrderRecordeToClient(email, orden.getFecha());

        orden.setEstadoMercadoPago(EMercadoPagoEstado.APROBADO);
        orden.setMercadoPagoId(idMerchantOrder);
        orden.setOcupada(true);
        orden.setRecordatorioEnviado(true);

        ordenRepository.save(orden);

        return true;
    }


    @Override
    public boolean eliminarOrdenDisponible(Long idOrden) {
        Orden orden = ordenRepository.findById(idOrden).orElse(null);

        if (orden == null) {
            return false;
        }
        if (orden.getOcupada()) {
            return false;
        }

        ordenRepository.delete(orden);

        return true;
    }

    public List<OrdenDisponibleResponse> convertirOrdenesADisponibles(List<Orden> ordenes) {
        return ordenes.stream().map(orden -> OrdenDisponibleResponse.builder()
                .fecha(orden.getFecha())
                .id(orden.getIdOrden())
                .total(orden.getTotal())
                .build()).collect(Collectors.toList());
    }

    public List<OrdenResponse> convertirOrdenesAOrdenesResponse(List<Orden> ordenes) {
        return ordenes.stream().map(orden -> OrdenResponse.builder()
                .fecha(orden.getFecha())
                .total(orden.getTotal())
                .estado(String.valueOf(orden.getEstadoOrden()))
                .estadoMercadoPago(String.valueOf(orden.getEstadoMercadoPago()))
                .tipoOrden(String.valueOf(orden.getTipoOrden()))
                .patenteDelVehiculo(orden.getPatenteDelVehiculo())
                .modeloDelVehiculo(orden.getModeloDelVehiculo())
                .recordatorioEnviado(orden.getRecordatorioEnviado())
                .nombreDelCliente("Completar")
                .emailDelCliente("Completar")
                .id(orden.getIdOrden())
                .mercadoPagoId(orden.getMercadoPagoId())
                .build()).collect(Collectors.toList());
    }

    public static String extractNumberFromUrl(String url) {
        String[] parts = url.split("/");

        String lastPart = parts[parts.length - 1];

        if (lastPart.matches("\\d+")) {
            return lastPart;
        } else {
            return null;
        }
    }

    public static String extractEmail(String description) {
        String regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(description);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

}
