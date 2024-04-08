package com.mitaller.modulos.inventario.dominio;


import com.mitaller.modulos.cobros.dominio.DetalleVenta;
import com.mitaller.modulos.cobros.dominio.Venta;
import com.mitaller.modulos.turnos.dominio.Orden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio", nullable = false)
    private Long idServicio;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private ETipoServicio tipo;

    //-------------------
    //todo: relaciones con venta, orden de trabajo y presupuesto

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DetalleVenta.class, mappedBy = "servicio")
    private List<DetalleVenta> detalleVentas;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Orden.class, mappedBy = "servicios")
    private List<Orden> ordenes;

}
