package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de FaltaVianda.
 * Warning: Unusued
 */
@Getter
@Builder
public class FaltaViandaDTO {

  private final String colaborador;
  private final String heladera;
  private final String medioDeNotificacion;
  private final String viandasRestantes;

  /**
   * Genera el DTO completo de una FaltaVianda.
   *
   * @param suscripcionFaltaVianda SuscripcionFaltaVianda
   * @return FaltaViandaDTO
   */
  public static FaltaViandaDTO completa(SuscripcionFaltaVianda suscripcionFaltaVianda) {

    return FaltaViandaDTO
        .builder()
        .colaborador(suscripcionFaltaVianda.getColaborador().getUsuario().getNombre())
        .heladera(suscripcionFaltaVianda.getHeladera().getNombre())
        .medioDeNotificacion(suscripcionFaltaVianda.getMedioDeNotificacion().toString())
        .viandasRestantes(suscripcionFaltaVianda.getUmbralViandas().toString())
        .build();
  }
}
