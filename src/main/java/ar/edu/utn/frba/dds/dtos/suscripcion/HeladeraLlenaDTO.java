package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de HeladeraLlena.
 * Warning: Unused
 */
@Getter
@Builder
public class HeladeraLlenaDTO {

  private final String colaborador;
  private final String heladera;
  private final String medioDeNotificacion;
  private final String espacioRestante;

  /**
   * Genera el DTO completo de una HeladeraLlena.
   *
   * @param suscripcionHeladeraLlena SuscripcionHeladeraLlena
   * @return HeladeraLlenaDTO
   */
  public static HeladeraLlenaDTO completa(SuscripcionHeladeraLlena suscripcionHeladeraLlena) {

    return HeladeraLlenaDTO
        .builder()
        .colaborador(suscripcionHeladeraLlena.getColaborador().getUsuario().getNombre())
        .heladera(suscripcionHeladeraLlena.getHeladera().getNombre())
        .medioDeNotificacion(suscripcionHeladeraLlena.getMedioDeNotificacion().toString())
        .espacioRestante(suscripcionHeladeraLlena.getUmbralEspacio().toString())
        .build();
  }
}
