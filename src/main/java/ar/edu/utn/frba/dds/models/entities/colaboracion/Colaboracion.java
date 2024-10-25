package ar.edu.utn.frba.dds.models.entities.colaboracion;

public enum Colaboracion {
    DONACION_VIANDAS,
    DONACION_DINERO,
    DISTRIBUCION_VIANDAS,
    ENTREGA_VIANDA,
    HACERSE_CARGO_HELADERA,
    OFERTA_DE_PRODUCTOS,
    REPARTO_DE_TARJETAS;

    public String getDescription() {
        return switch (this) {
            case DONACION_VIANDAS -> "Donacion por Viandas";
            case DONACION_DINERO -> "Donacion por Dinero";
            case DISTRIBUCION_VIANDAS -> "Distribucion por Viandas";
            case ENTREGA_VIANDA -> "Entrega por Viandas";
            case HACERSE_CARGO_HELADERA -> "Encargarse por Heladeras";
            case OFERTA_DE_PRODUCTOS -> "Oferta por Productos";
            case REPARTO_DE_TARJETAS -> "Reparto por Tarjetas";
        };
    }
}