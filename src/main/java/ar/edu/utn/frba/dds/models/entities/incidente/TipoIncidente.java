package ar.edu.utn.frba.dds.models.entities.incidente;

/**
 * Enumerado que representa los tipos de incidentes que se pueden generar en el sistema.
 */
public enum TipoIncidente {
  FALLA_TEMPERATURA,
  FRAUDE,
  FALLA_CONEXION,
  FALLA_TECNICA;

  /**
   * Obtiene la descripción del tipo de incidente.
   *
   * @return Descripción del tipo de incidente.
   */
  public String getDescription() {
    return switch (this) {
      case FALLA_TEMPERATURA -> "Falla nueva Temperatura";
      case FRAUDE -> "Fraude";
      case FALLA_CONEXION -> "Falla de Conexión";
      case FALLA_TECNICA -> "Falla Técnica";
    };
  }
}
