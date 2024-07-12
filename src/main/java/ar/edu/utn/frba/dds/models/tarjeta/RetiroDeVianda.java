package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetiroDeVianda {
  private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;
  private Heladera heladera;
  private LocalDateTime fechaHora;

  public static RetiroDeVianda by(TarjetaPersonaVulnerable tarjetaPersonaVulnerable,
                                  Heladera heladera,
                                  LocalDateTime fechaHora) {
    return RetiroDeVianda
        .builder()
        .tarjetaPersonaVulnerable(tarjetaPersonaVulnerable)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }
}
