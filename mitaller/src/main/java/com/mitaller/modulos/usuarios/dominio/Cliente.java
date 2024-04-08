package com.mitaller.modulos.usuarios.dominio;


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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TipoIdentificacion.class)
    @JoinColumn(name = "tipo_identificacion", nullable = false)
    private TipoIdentificacion tipoIdentificacion;


    @OneToMany(fetch = FetchType.LAZY, targetEntity = Orden.class, mappedBy = "cliente")
    private List<Orden> ordenes;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Venta.class, mappedBy = "cliente")
    private List<Venta> ventas;

}
