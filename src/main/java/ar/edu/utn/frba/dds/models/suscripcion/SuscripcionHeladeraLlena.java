package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuscripcionHeladeraLlena {

  private Colaborador colaborador;
  private Heladera heladera;
  private MedioDeNotificacion medioDeNotificacion;
  private Integer viandasFaltantes;

  public static SuscripcionHeladeraLlena de(Colaborador colaborador,
                                            Heladera heladera,
                                            MedioDeNotificacion medioDeNotificacion,
                                            Integer viandasFaltantes) {
    return SuscripcionHeladeraLlena
        .builder()
        .colaborador(colaborador)
        .heladera(heladera)
        .medioDeNotificacion(medioDeNotificacion)
        .viandasFaltantes(viandasFaltantes)
        .build();
  }

  public Boolean debeSerNotificado(Integer cantViandasFaltantes) {
    return cantViandasFaltantes <= viandasFaltantes;
  }
}
