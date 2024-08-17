package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolicitudDeApertura {

  private TarjetaColaborador tarjeta;
  private Heladera heladera;
  private LocalDateTime fechaHora;
  private MotivoDeApertura motivo;

  public static SolicitudDeApertura por(TarjetaColaborador tarjeta,
                                        Heladera heladera,
                                        LocalDateTime fechaHora,
                                        MotivoDeApertura motivo) {
    return SolicitudDeApertura
        .builder()
        .tarjeta(tarjeta)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .motivo(motivo)
        .build();
  }

  public static SolicitudDeApertura por(TarjetaColaborador tarjeta,
                                        Heladera heladera) {
    return SolicitudDeApertura
        .builder()
        .tarjeta(tarjeta)
        .heladera(heladera)
        .fechaHora(LocalDateTime.now())
        .build();
  }
}
