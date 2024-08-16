package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuscripcionFallaHeladera {

  private Colaborador colaborador;
  private Heladera heladera;
  private MedioDeNotificacion medioDeNotificacion;

  public static SuscripcionFallaHeladera of(Colaborador colaborador,
                                            Heladera heladera,
                                            MedioDeNotificacion medioDeNotificacion) {
    return SuscripcionFallaHeladera
        .builder()
        .colaborador(colaborador)
        .heladera(heladera)
        .medioDeNotificacion(medioDeNotificacion)
        .build();
  }

}
