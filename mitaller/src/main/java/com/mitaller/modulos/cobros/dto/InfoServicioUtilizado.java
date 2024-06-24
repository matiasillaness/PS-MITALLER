package com.mitaller.modulos.cobros.dto;

public class InfoServicioUtilizado {
    private String nombre;
    private Long cantidad;

    public InfoServicioUtilizado(String nombre, Long cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}


