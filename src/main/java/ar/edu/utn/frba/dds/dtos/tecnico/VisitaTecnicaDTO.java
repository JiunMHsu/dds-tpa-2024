package ar.edu.utn.frba.dds.dtos.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitaTecnicaDTO {

  private String id;

  private String fecha;

  private String hora;

  private String descripcion;

  private String foto;

  private boolean pudoResolverse;

  public static VisitaTecnicaDTO completa(VisitaTecnica visitaTecnica) {
    return builder()
        .id(visitaTecnica.getId().toString())
        .fecha(DateTimeParser.parseFecha(visitaTecnica.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(visitaTecnica.getFechaHora().toLocalTime()))
        .descripcion(visitaTecnica.getDescripcion())
        .foto(visitaTecnica.getFoto().getRuta())
        .pudoResolverse(visitaTecnica.isPudoResolverse())
        .build();
  }

  public static VisitaTecnicaDTO preview(VisitaTecnica visitaTecnica) {
    return builder()
        .id(visitaTecnica.getId().toString())
        .fecha(DateTimeParser.parseFecha(visitaTecnica.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(visitaTecnica.getFechaHora().toLocalTime()))
        .foto(visitaTecnica.getFoto().getRuta())
        .pudoResolverse(visitaTecnica.isPudoResolverse())
        .build();
  }

}
