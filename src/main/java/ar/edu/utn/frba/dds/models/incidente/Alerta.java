package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Alerta implements Incidente {
  private TipoIncidente tipo;
  private Heladera heladera;
  private LocalDateTime fechaHora;

  public static Alerta por(TipoIncidente tipo, Heladera heladera, LocalDateTime fechaHora) {
    return Alerta
        .builder()
        .tipo(tipo)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }

}
