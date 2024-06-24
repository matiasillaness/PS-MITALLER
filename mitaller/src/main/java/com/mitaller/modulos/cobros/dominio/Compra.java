package com.mitaller.modulos.cobros.dominio;


import com.mitaller.modulos.inventario.dominio.ETipoServicio;
import com.mitaller.modulos.proveedores.dominio.Proveedor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private BigDecimal total;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre_comprobante")
    private String nombreComprobante;

    @Column(name = "iva")
    private BigDecimal iva;

    @Column(name = "dada_de_baja")
    private boolean dadaDeBaja = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago")
    private ETipoPago tipoPago;
    //------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Proveedor.class)
    private Proveedor proveedor;

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DetalleCompra> detalleCompras;


    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleCompra detalleCompra : detalleCompras) {
            total = total.add(detalleCompra.getSubtotal());
        }
        return total;
    }

    public BigDecimal calcularIva(BigDecimal porcentajeIva) {
        return calcularTotal().multiply(porcentajeIva);
    }

    public String crearNombreComprobante() {
        return "COMPRA-" + idCompra;
    }

    public String obtenerFechaActual() {
        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return fecha.format(formatter);
    }

    //envez de add de a uno add de a muchos
    public void addDetalleCompras(List<DetalleCompra> detalles) {
        if (this.detalleCompras == null) {
            this.detalleCompras = new ArrayList<>();
        }
        this.detalleCompras.addAll(detalles);
        for (DetalleCompra detalle : detalles) {
            detalle.setCompra(this); // Establecer la relación inversa
        }
    }

}
