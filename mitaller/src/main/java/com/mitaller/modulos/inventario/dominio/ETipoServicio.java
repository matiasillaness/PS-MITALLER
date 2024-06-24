package com.mitaller.modulos.inventario.dominio;

public enum ETipoServicio {
    MANTENIMIENTO,
    REPARACION,
    CAMBIO_DE_PIEZAS,

    INSTALACION,
    PINTURA,
    LAVADO,
    ALINEACION,
    BALANCEO,
    CAMBIO_DE_ACEITE,
    CAMBIO_DE_FILTRO,
    OTROS;

    @Override
    public String toString() {
        return switch (this) {
            case MANTENIMIENTO -> "Mantenimiento";
            case REPARACION -> "Reparación";
            case CAMBIO_DE_PIEZAS -> "Cambio de piezas";
            case INSTALACION -> "Instalación";
            case PINTURA -> "Pintura";
            case LAVADO -> "Lavado";
            case ALINEACION -> "Alineación";
            case BALANCEO -> "Balanceo";
            case CAMBIO_DE_ACEITE -> "Cambio de aceite";
            case CAMBIO_DE_FILTRO -> "Cambio de filtro";
            case OTROS -> "Otros";
            default -> super.toString();
        };
    }
}
