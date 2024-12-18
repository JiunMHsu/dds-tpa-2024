package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnicaDTO {

  private String id;

  private String heladera;

  private String fecha;

  private String hora;

  private String colaborador;

  private String descripcion;

  private String foto;

  private boolean resuelto;


  // En la vista completa podría mapearse más info del colaborador y la heladera
  // puede servir paraColaborador redirecciones
  public static FallaTecnicaDTO completa(Incidente incidente) {
    return FallaTecnicaDTO.builder()
        .id(incidente.getId().toString())
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .colaborador(incidente.getColaborador().getNombre())
        .descripcion(incidente.getDescripcion())
        .foto(incidente.getFoto().getRuta())
        .resuelto(incidente.getFallaResuelta())
        .build();
  }

  public static FallaTecnicaDTO preview(Incidente incidente) {
    return FallaTecnicaDTO.builder()
        .id(incidente.getId().toString())
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .foto(incidente.getFoto().getRuta())
        .resuelto(incidente.getFallaResuelta())
        .build();
  }

}
