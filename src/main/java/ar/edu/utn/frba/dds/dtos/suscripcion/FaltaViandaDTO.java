package ar.edu.utn.frba.dds.dtos.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FaltaViandaDTO {

  private String colaborador;

  private String heladera;

  private String medioDeNotificacion;

  private String viandasRestantes;

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
