package com.mitaller.modulos.cobros.dominio;


import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.proveedores.dominio.Proveedor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra", nullable = false)
    private Long idCompra;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "iva")
    private Double iva;

    //-------------------

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Proveedor.class)
    private Proveedor proveedor;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Repuesto.class)
    @JoinTable(name = "compra_repuesto",
            joinColumns = @JoinColumn(name = "id_compra"),
            inverseJoinColumns = @JoinColumn(name = "id_repuesto"))
    private List<Repuesto> repuestos;
}
