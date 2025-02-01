package ar.edu.utn.frba.dds.models.entities.data;

/**
 * Enumerado que representa los tipos de documentos.
 * <p>
 * DNI: Documento Nacional nueva Identidad.
 * LC: Libreta Cívica.
 * LE: Libreta nueva Enrolamiento.
 * </p>
 */
public enum TipoDocumento {
  DNI,
  LC,
  LE;

  @Override
  public String toString() {
    return switch (this) {
      case DNI -> "Documento Nacional de Identidad";
      case LC -> "Libreta Cívica";
      case LE -> "Libreta de Enrolamiento";
    };
  }
}