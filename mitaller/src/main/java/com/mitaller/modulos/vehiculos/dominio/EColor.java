package com.mitaller.modulos.vehiculos.dominio;

import jakarta.persistence.OneToMany;

public enum EColor {
    BLANCO,
    NEGRO,
    ROJO,
    AZUL,
    AMARILLO,
    VERDE,
    PLATEADO,
    GRIS,
    ROSA,
    VIOLETA;



    @Override
    public String toString() {
        return switch (this) {
            case BLANCO -> "Blanco";
            case NEGRO -> "Negro";
            case ROJO -> "Rojo";
            case AZUL -> "Azul";
            case AMARILLO -> "Amarillo";
            case VERDE -> "Verde";
            case PLATEADO -> "Plateado";
            case GRIS -> "Gris";
            case ROSA -> "Rosa";
            case VIOLETA -> "Violeta";
            default -> throw new IllegalArgumentException();
        };
    }
}
