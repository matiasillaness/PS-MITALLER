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

    public String inverseToString(String tipo) {
        return switch (tipo) {
            case "Sedán" -> SEDAN.toString();
            case "Coupé" -> COUPE.toString();
            case "Hatchback" -> HATCHBACK.toString();
            case "Descapotable" -> DESCAPOTABLE.toString();
            case "Station Wagon" -> STATION_WAGON.toString();
            case "SUV" -> SUV.toString();
            case "Crossover" -> CROSSOVER.toString();
            case "Monovolumen" -> MONOVOLUMEN.toString();
            default -> throw new IllegalArgumentException();
        };
    }

}
