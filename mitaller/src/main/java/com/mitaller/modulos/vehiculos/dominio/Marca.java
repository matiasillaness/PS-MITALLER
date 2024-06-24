package com.mitaller.modulos.vehiculos.dominio;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca", nullable = false)
    private Long idMarca;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    //-------------------
    //todo: Relaciones con vehiculos

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "marca")
    private List<Vehiculo> vehiculos;
}
