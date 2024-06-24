package com.mitaller.modulos.turnos.controladores;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mitaller.modulos.turnos.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
public class OrdenWebhookController {

    private final OrdenService ordenService;

    public OrdenWebhookController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }
    @PostMapping("/webhook")
    public ResponseEntity<?> handleEvent(@RequestBody Map<String, Object> event, @RequestHeader Map<String, String> headers) throws MPException, MPApiException {
       String topic = (String) event.get("topic");

       if (topic.equals("payment")) {
            String body = (String) event.get("body");
            if (body == null || body.isEmpty()) {
                return ResponseEntity.status(403).body("Missing body");
            }
        }

       //completar
       if (topic.equals("merchant_order")) {
           String merchantOrder = (String) event.get("resource");
            if (ordenService.confirmarOrden(merchantOrder)) {
                return ResponseEntity.ok().build();
            }

           }
         return ResponseEntity.status(403).body("Invalid topic");
    }

}
