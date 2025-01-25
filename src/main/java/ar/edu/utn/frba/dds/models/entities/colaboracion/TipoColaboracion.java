package ar.edu.utn.frba.dds.models.entities.colaboracion;

public enum TipoColaboracion {
  DONACION_VIANDAS,
  DONACION_DINERO,
  DISTRIBUCION_VIANDAS,
  HACERSE_CARGO_HELADERA,
  OFERTA_DE_PRODUCTOS,
  REPARTO_DE_TARJETAS;

  public String getDescription() {
    return switch (this) {
      case DONACION_VIANDAS -> "Donacion de Viandas";
      case DONACION_DINERO -> "Donacion de Dinero";
      case DISTRIBUCION_VIANDAS -> "Distribucion de Viandas";
      case HACERSE_CARGO_HELADERA -> "Encargarse de Heladeras";
      case OFERTA_DE_PRODUCTOS -> "Oferta de Productos / Servicios";
      case REPARTO_DE_TARJETAS -> "Reparto de Tarjetas";
    };
  }
}