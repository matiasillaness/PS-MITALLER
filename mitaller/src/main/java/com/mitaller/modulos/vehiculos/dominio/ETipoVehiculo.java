package com.mitaller.modulos.vehiculos.dominio;

public enum ETipoVehiculo {
    SEDAN,
    COUPE,
    HATCHBACK,
    DESCAPOTABLE,
    STATION_WAGON,
    SUV,
    CROSSOVER,
    MONOVOLUMEN;

    @Override
    public String toString() {
        return switch (this) {
            case SEDAN -> "Sedán";
            case COUPE -> "Coupé";
            case HATCHBACK -> "Hatchback";
            case DESCAPOTABLE -> "Descapotable";
            case STATION_WAGON -> "Station Wagon";
            case SUV -> "SUV";
            case CROSSOVER -> "Crossover";
            case MONOVOLUMEN -> "Monovolumen";
            default -> throw new IllegalArgumentException();
        };
    }
}
