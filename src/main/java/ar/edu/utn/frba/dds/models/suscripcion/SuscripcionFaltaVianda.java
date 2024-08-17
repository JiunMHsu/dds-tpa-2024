package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuscripcionFaltaVianda {

  private Colaborador colaborador;
  private Heladera heladera;
  private MedioDeNotificacion medioDeNotificacion;
  private Integer viandasRestantes;

  public static SuscripcionFaltaVianda de(Colaborador colaborador,
                                          Heladera heladera,
                                          MedioDeNotificacion medioDeNotificacion,
                                          Integer viandasRestantes) {
    return SuscripcionFaltaVianda
        .builder()
        .colaborador(colaborador)
        .heladera(heladera)
        .medioDeNotificacion(medioDeNotificacion)
        .viandasRestantes(viandasRestantes)
        .build();
  }

  public Boolean debeSerNotificado(Integer cantViandasRestantes) {
    return cantViandasRestantes <= viandasRestantes;
  }
}
