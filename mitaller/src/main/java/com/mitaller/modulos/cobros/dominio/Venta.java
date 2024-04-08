package com.mitaller.modulos.cobros.dominio;


import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import com.mitaller.modulos.usuarios.dominio.Cliente;
import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta", nullable = false)
    private Long idVenta;

    @Column(name = "fecha", nullable = false, columnDefinition = "date")
    private Date fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private ETipoPago tipoPago;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "iva")
    private Double iva;

    @Column(name = "total")
    private Double total;


    //------------------------------------------------------------------------------------------


    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;


    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
