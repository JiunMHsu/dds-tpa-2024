package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepartoDeTarjetas {

  private Colaborador colaborador;
  private LocalDate fechaReparto;
  private TarjetaPersonaVulnerable tarjeta;
  private PersonaVulnerable personaVulnerable;

  public static RepartoDeTarjetas by(Colaborador colaborador,
                                     TarjetaPersonaVulnerable tarjeta,
                                     PersonaVulnerable personaVulnerable) {
    return RepartoDeTarjetas
        .builder()
        .colaborador(colaborador)
        .fechaReparto(LocalDate.now())
        .tarjeta(tarjeta)
        .personaVulnerable(personaVulnerable)
        .build();
  }

  public static RepartoDeTarjetas by(Colaborador colaborador,
                                     LocalDate fechaReparto) {
    return RepartoDeTarjetas
        .builder()
        .colaborador(colaborador)
        .fechaReparto(fechaReparto)
        .build();
  }
}
