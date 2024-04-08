package com.mitaller.modulos.turnos.dominio;


import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import com.mitaller.modulos.usuarios.dominio.Cliente;
import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", nullable = false)
    private Long idOrden;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Column(name = "hora", nullable = false)
    private String hora;

    @Enumerated(EnumType.STRING)
    private ETipoOrden tipoOrden;

    //-------------------
    //todo: Relaciones con cliente, vehiculo y servicios

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Vehiculo.class)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class)
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Servicio.class)
    @JoinTable(name = "orden_servicio",
            joinColumns = @JoinColumn(name = "id_orden"),
            inverseJoinColumns = @JoinColumn(name = "id_servicio"))
    private List<Servicio> servicios;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Repuesto.class)
    @JoinTable(name = "orden_repuesto",
            joinColumns = @JoinColumn(name = "id_orden"),
            inverseJoinColumns = @JoinColumn(name = "id_repuesto"))
    private List<Repuesto> repuestos;



}
