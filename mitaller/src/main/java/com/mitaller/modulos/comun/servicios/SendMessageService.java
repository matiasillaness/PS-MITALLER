package com.mitaller.modulos.comun.servicios;


import com.mitaller.modulos.comun.model.UsuarioCambiarContrasenia;
import org.springframework.stereotype.Service;

@Service
public interface SendMessageService {
    boolean sendEmailToChangePassword(UsuarioCambiarContrasenia usuarioCambiarContrasenia);
    boolean sendEmailWithPdfOfCar(String email);

    boolean sendOrderRecordeToClient(String email, String fecha);
}
