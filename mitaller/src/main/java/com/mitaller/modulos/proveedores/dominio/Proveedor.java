package com.mitaller.modulos.proveedores.dominio;


import com.mitaller.modulos.cobros.dominio.Compra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor", nullable = false)
    private Long idProveedor;


    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "descripcion")
    private String descripcion;


    //------------------------------------------------------------------------------------------
    //TODO: RELACIONES CON COMPRAS
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proveedor")
    private List<Compra> compras;

}
