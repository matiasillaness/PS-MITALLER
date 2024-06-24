package com.mitaller.modulos.comun.servicios.impl;

import com.mitaller.modulos.comun.model.UsuarioCambiarContrasenia;
import com.mitaller.modulos.comun.servicios.SendMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendMessageServiceImpl implements SendMessageService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String EMAIL;

    public SendMessageServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public boolean sendEmailToChangePassword(UsuarioCambiarContrasenia usuarioCambiarContrasenia) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(usuarioCambiarContrasenia.getEmail());
        msg.setFrom(EMAIL);

        msg.setSubject(usuarioCambiarContrasenia.getAsunto());
        msg.setText(usuarioCambiarContrasenia.getMensaje() + usuarioCambiarContrasenia.getCode());

        javaMailSender.send(msg);
        return true;
    }



    @Override
    public boolean sendEmailWithPdfOfCar(String email) {


        // Send email with pdf of car
        return true;
    }

    @Override
    public boolean sendOrderRecordeToClient(String email, String fecha) {
        // Send email with order record to client
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom(EMAIL);

        msg.setSubject("Recordatorio de turno");
        msg.setText("Recuerda que el dia " + fecha + " tienes un turno agendado en nuestro taller");

        javaMailSender.send(msg);

        return true;
    }
}
