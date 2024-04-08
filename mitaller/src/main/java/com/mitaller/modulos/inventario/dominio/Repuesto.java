package com.mitaller.modulos.inventario.dominio;


import com.mitaller.modulos.cobros.dominio.Compra;
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
public class Repuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repuesto", nullable = false)
    private Long idRepuesto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    //-------------------


    @OneToMany(fetch = FetchType.LAZY, targetEntity = DetalleVenta.class, mappedBy = "repuesto")
    private List<DetalleVenta> detalleVentas;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Compra.class, mappedBy = "repuestos")
    private List<Compra> compras;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Orden.class, mappedBy = "repuestos")
    private List<Orden> ordenes;

}
