package com.mitaller.modulos.vehiculos.dominio;


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
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo", nullable = false)
    private Long idVehiculo;

    @Column(name = "patente", nullable = false, unique = true)
    private String patente;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo", nullable = false)
    private ETipoVehiculo tipoVehiculo;

    @Column(name = "kilometraje", nullable = true)
    private Integer kilometraje;

    @Column(name = "numero_chasis", nullable = true)
    private String numeroChasis;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private EColor color;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    //------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Marca.class)
    private Marca marca;



    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY,targetEntity = Venta.class)
    private List<Venta> ventas;

}
