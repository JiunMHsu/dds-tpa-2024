package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AperturaHeladera {

  private TarjetaColaborador tarjetaColaborador;
  private Heladera heladera;
  private LocalDateTime fechaHora;

  public static AperturaHeladera by(TarjetaColaborador tarjetaColaborador,
                                    Heladera heladera,
                                    LocalDateTime fechaHora) {
    return AperturaHeladera
        .builder()
        .tarjetaColaborador(tarjetaColaborador)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }
}
