package ar.edu.utn.frba.dds.models.entities.incidente;

public enum TipoIncidente {
  FALLA_TEMPERATURA,
  FRAUDE,
  FALLA_CONEXION,
  FALLA_TECNICA;

  public String getDescription() {
    return switch (this) {
      case FALLA_TEMPERATURA -> "Falla por Temperatura";
      case FRAUDE -> "Fraude";
      case FALLA_CONEXION -> "Falla por Conexión";
      case FALLA_TECNICA -> "Falla Técnica";
    };
  }
}
