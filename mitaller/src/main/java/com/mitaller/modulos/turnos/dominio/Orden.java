package com.mitaller.modulos.turnos.dominio;


import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import com.mitaller.modulos.usuarios.dominio.Usuario;
import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", nullable = false)
    private Long idOrden;

    @Column(name = "id_mercado_pago")
    private String mercadoPagoId;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Enumerated(EnumType.STRING)
    private ETipoOrden tipoOrden;

    //si la crea un empleado el ocupada debe estar en false
    @Column(name = "ocupada", nullable = false)
    private Boolean ocupada;

    @Enumerated(EnumType.STRING)
    private EOrdenEstado estadoOrden;

    @Enumerated(EnumType.STRING)
    private EMercadoPagoEstado estadoMercadoPago;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "descripcion")
    private String patenteDelVehiculo;

    @Column(name = "modelo")
    private String modeloDelVehiculo;

    @Column(name = "recordatorio_enviado")
    private Boolean recordatorioEnviado;


    //-------------------

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
    private Usuario usuario;
}
