package com.mitaller.modulos.cobros.dominio;

public enum ETipoPago {
    EFECTIVO,
    TARJETA_DEBITO,
    TARJETA_CREDITO,
    TRANSFERENCIA,
    CHEQUE;

    public String toString() {
        return switch (this) {
            case EFECTIVO -> "Efectivo";
            case TARJETA_DEBITO -> "Tarjeta de débito";
            case TARJETA_CREDITO -> "Tarjeta de crédito";
            case TRANSFERENCIA -> "Transferencia";
            case CHEQUE -> "Cheque";
            default -> throw new IllegalArgumentException();
        };
    }

    public static ETipoPago fromString(String tipoPago) {
        return switch (tipoPago) {
            case "Efectivo" -> EFECTIVO;
            case "Tarjeta de débito" -> TARJETA_DEBITO;
            case "Tarjeta de crédito" -> TARJETA_CREDITO;
            case "Transferencia" -> TRANSFERENCIA;
            case "Cheque" -> CHEQUE;
            default -> throw new IllegalArgumentException();
        };
    }

}
