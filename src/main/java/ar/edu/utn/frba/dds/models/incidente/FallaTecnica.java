package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnica implements Incidente {
  private Heladera heladera;
  private LocalDateTime fechaHora;
  private Colaborador colaborador;
  private String descripcion;
  private Imagen foto;

  public static FallaTecnica de(Heladera heladera,
                                LocalDateTime fechaHora,
                                Colaborador colaborador,
                                String descripcion,
                                Imagen foto) {
    return FallaTecnica
        .builder()
        .heladera(heladera)
        .fechaHora(fechaHora)
        .colaborador(colaborador)
        .descripcion(descripcion)
        .foto(foto)
        .build();
  }

  public static FallaTecnica de(Heladera heladera, Colaborador colaborador) {
    return FallaTecnica
        .builder()
        .colaborador(colaborador)
        .fechaHora(LocalDateTime.now())
        .heladera(heladera)
        .build();
  }

  public TipoIncidente getTipo() {
    return TipoIncidente.FALLA_TECNICA;
  }
}
