package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.reportes.RegistroIncidente;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class Incidente {

  private TipoIncidente tipo;
  private Heladera heladera;
  private LocalDateTime fechaHora;

  public static void reportar(TipoIncidente tipo, Heladera heladera, LocalDateTime fechaHora) {
    Incidente incidente = Incidente
        .builder()
        .tipo(tipo)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();

    heladera.setEstadoDeFalla();
    RegistroIncidente.registrarIncidente(incidente);
    // notificar técnico
  }
}
