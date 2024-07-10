package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepartoDeTarjetas {

  private Colaborador colaborador;
  private LocalDate fechaReparto;
  private Tarjeta tarjeta;
  private PersonaVulnerable personaVulnerable;

  public static RepartoDeTarjetas with(Colaborador colaborador,
                                       Tarjeta tarjeta,
                                       PersonaVulnerable personaVulnerable) {
    return RepartoDeTarjetas
        .builder()
        .colaborador(colaborador)
        .fechaReparto(LocalDate.now())
        .tarjeta(tarjeta)
        .personaVulnerable(personaVulnerable)
        .build();
  }
}
