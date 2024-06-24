package com.mitaller.modulos.inventario.dominio;


import com.mitaller.modulos.cobros.dominio.Compra;
import com.mitaller.modulos.cobros.dominio.DetalleVenta;
import com.mitaller.modulos.turnos.dominio.Orden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
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
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    //-------------------

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MarcaRepuesto.class)
    private MarcaRepuesto marca;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = DetalleVenta.class, mappedBy = "repuesto")
    private List<DetalleVenta> detalleVentas;



}
