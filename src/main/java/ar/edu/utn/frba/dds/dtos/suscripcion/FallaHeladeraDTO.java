package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de FallaHeladera.
 * Warning: Unused
 */
@Getter
@Builder
public class FallaHeladeraDTO {

  private final String colaborador;
  private final String heladera;
  private final String medioDeNotificacion;

  /**
   * Genera el DTO completo de una FallaHeladera.
   *
   * @param suscripcionFallaHeladera SuscripcionFallaHeladera
   * @return FallaHeladeraDTO
   */
  public static FallaHeladeraDTO completa(SuscripcionFallaHeladera suscripcionFallaHeladera) {

    return FallaHeladeraDTO
        .builder()
        .colaborador(suscripcionFallaHeladera.getColaborador().getUsuario().getNombre())
        .heladera(suscripcionFallaHeladera.getHeladera().getNombre())
        .medioDeNotificacion(suscripcionFallaHeladera.getMedioDeNotificacion().toString())
        .build();
  }
}
