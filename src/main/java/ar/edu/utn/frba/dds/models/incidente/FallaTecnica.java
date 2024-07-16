package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import ar.edu.utn.frba.dds.reportes.RegistroDonacion;
import ar.edu.utn.frba.dds.reportes.RegistroIncidente;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnica {
  private Colaborador colaborador;
  private LocalDateTime fechaHora;
  private Heladera heladera;
  private String descripcion;
  private Imagen foto;

  public static FallaTecnica with(Colaborador colaborador, Heladera heladera, String descripcion, Imagen foto) {
    return FallaTecnica
        .builder()
        .colaborador(colaborador)
        .fechaHora(LocalDateTime.now())
        .heladera(heladera)
        .descripcion(descripcion)
        .foto(foto)
        .build();
  }

  public static FallaTecnica with(Colaborador colaborador, Heladera heladera) {
    return FallaTecnica
        .builder()
        .colaborador(colaborador)
        .fechaHora(LocalDateTime.now())
        .heladera(heladera)
        .build();
  }
}
