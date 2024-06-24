package com.mitaller.modulos.turnos.service.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mitaller.modulos.turnos.modelos.OrdenPreferenciaRequest;
import com.mitaller.modulos.turnos.repositorio.OrdenRepository;
import com.mitaller.modulos.turnos.service.MercadoPagoService;
import com.mitaller.modulos.usuarios.dominio.Usuario;
import com.mitaller.modulos.usuarios.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Value("${codigo.mercadolibre}")
    private String codigoMercadoLibre;

    @Value("${codigo.mercadolibre.backurl.success}")
    private String backUrlSuccess;

    @Value("${codigo.mercadolibre.backurl.failure}")
    private String backUrlFailure;

    @Value("${codigo.mercadolibre.backurl.notification}")
    private String backUrlNotification;

    private final OrdenRepository ordenRepository;
    private final UsuarioRepository usuarioRepository;

    public MercadoPagoServiceImpl(OrdenRepository ordenRepository, UsuarioRepository usuarioRepository) {
        this.ordenRepository = ordenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void cambiarEstadoOrden(String ordenId, String estado) {

    }

    @Override
    public String crearPreferencia(OrdenPreferenciaRequest ordenRequest, String dia) {
        if (ordenRequest == null) return null;
        String title = ordenRequest.getOrdenRequest().getPatenteDelVehiculo();
        String description = ordenRequest.getOrdenRequest().getTipoOrden();
        int quantity = 1;
        BigDecimal unitPrice = ordenRequest.getTotal();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(ordenRequest.getOrdenRequest().getEmailDelCliente());

        if (usuario == null) return null;

        try{
            MercadoPagoConfig.setAccessToken(codigoMercadoLibre);
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title("Orden de servicio" + " - " + dia)
                    .id(ordenRequest.getIdOrden().toString())
                    .quantity(quantity)
                    .description(description + " - Patente:" +
                            ordenRequest.getOrdenRequest().getPatenteDelVehiculo() + " - Cliente:" +
                            usuario.getNombre() + " " + usuario.getApellido() + " - Email:" +
                            usuario.getEmail() + " - Telefono:" + usuario.getTelefono()
                            )
                    .unitPrice(unitPrice)
                    .currencyId("ARS")
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                    .success("https://youtube.com")
                    .failure("https://facebook.com")
                    .build();

            // Preferencia general ENSAMBLE DE PREFERENCIAS
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .operationType("regular_payment")
                    .binaryMode(true)
                    .backUrls(backUrlsRequest)
                    .notificationUrl(backUrlNotification)
                    .autoReturn("approved")
                    .build();

            //Creo un objeto tipo cliente para comunicarme con MP
            PreferenceClient preferenceClient = new PreferenceClient();
            Preference preference = preferenceClient.create(preferenceRequest);

            return preference.getId();
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return "No se ha podido realizar la compra";
        }
    }
}
