package com.mitaller.modulos.cobros.dominio;


import com.mitaller.modulos.inventario.dominio.Repuesto;
import com.mitaller.modulos.inventario.dominio.Servicio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_venta", nullable = false)
    private Long idDetalleVenta;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precioUnitario", nullable = false)
    private double precioUnitario;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_repuesto")
    private Repuesto repuesto;


    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    public double calcularSubtotal(){
        return cantidad * precioUnitario;
    }

}
