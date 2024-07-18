package ar.edu.utn.frba.dds.models.tecnico;

import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitaTecnico {
  private Tecnico tecnico;
  private Heladera heladera;
  private LocalDateTime fechaHora;
  private String descripcion;
  private Imagen foto;
  private Boolean fallaResuelta;

  public static VisitaTecnico by(Tecnico tecnico,
                                 Heladera heladera,
                                 LocalDateTime fechaHora,
                                 String descripcion,
                                 Imagen foto,
                                 Boolean fallaResuelta) {
    return VisitaTecnico
        .builder()
        .tecnico(tecnico)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .descripcion(descripcion)
        .foto(foto)
        .fallaResuelta(fallaResuelta)
        .build();
  }

  public static VisitaTecnico by(Tecnico tecnico,
                                 Heladera heladera) {
    return VisitaTecnico
        .builder()
        .tecnico(tecnico)
        .heladera(heladera)
        .build();
  }
}
