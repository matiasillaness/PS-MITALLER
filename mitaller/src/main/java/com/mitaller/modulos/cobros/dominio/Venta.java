package com.mitaller.modulos.cobros.dominio;


import com.mitaller.modulos.usuarios.dominio.Usuario;
import com.mitaller.modulos.vehiculos.dominio.Vehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private ETipoPago tipoPago;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "descuento")
    private BigDecimal descuento;

    @Column(name = "iva")
    private Double iva;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "dni")
    private String dniCliente;

    @Column(name = "direccion")
    private String direccionCliente;

    @Column(name = "telefono")
    private String telefonoCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_factura", nullable = false)
    private ETipoFactura tipoFactura;

    @Column(name = "mercado_pago")
    private Boolean mercadoPago = false;

    @Column(name = "estado", nullable = false)
    private boolean dadaDeBaja = false;


    //------------------------------------------------------------------------------------------

    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;



    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas =  new ArrayList<>();


    public void AddDetalleVenta(DetalleVenta detalleVenta){
        detalleVentas.add(detalleVenta);
        detalleVenta.setVenta(this);
    }



    public void crearNumeroFactura(){
        this.numeroFactura = "FAC-" + this.idVenta;
    }


}
