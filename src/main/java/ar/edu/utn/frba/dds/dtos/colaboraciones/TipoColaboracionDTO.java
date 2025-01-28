package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TipoColaboracionDTO {

  protected String etiqueta;

  protected String valor;

  protected String path;

  private boolean estaActivo;

  public static TipoColaboracionDTO configOption(TipoColaboracion colaboracion, boolean estaActivo) {
    return TipoColaboracionDTO.builder()
        .etiqueta(colaboracion.getDescription())
        .valor(colaboracion.toString())
        .estaActivo(estaActivo)
        .build();
  }

  public static TipoColaboracionDTO redirectable(TipoColaboracion colaboracion) {
    return TipoColaboracionDTO.builder()
        .etiqueta(getAction(colaboracion))
        .path(getPath(colaboracion))
        .estaActivo(true)
        .build();
  }

  // TODO: Refactor

  protected static String getAction(TipoColaboracion colaboracion) {
    return switch (colaboracion) {
      case DONACION_VIANDAS -> "Donar Vianda";
      case DONACION_DINERO -> "Donar Dinero";
      case DISTRIBUCION_VIANDAS -> "Distribuir Viandas";
      case HACERSE_CARGO_HELADERA -> "Encargarse de Heladeras";
      case OFERTA_DE_PRODUCTOS -> "Ofrecer Producto / Servicio";
      case REPARTO_DE_TARJETAS -> "Registrar Persona Vulnerable";
    };
  }

  protected static String getPath(TipoColaboracion colaboracion) {
    return switch (colaboracion) {
      case DONACION_VIANDAS -> "donacion-vianda";
      case DONACION_DINERO -> "donacion-dinero";
      case DISTRIBUCION_VIANDAS -> "distribucion-viandas";
      case HACERSE_CARGO_HELADERA -> "encargarse-de-heladeras";
      case OFERTA_DE_PRODUCTOS -> "oferta-producto-servicio";
      case REPARTO_DE_TARJETAS -> "registro-persona-vulnerable";
    };
  }
}
