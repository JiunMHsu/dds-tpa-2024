package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlertaDTO {

  private String id;

  private String heladera;

  private String fecha;

  private String hora;

  private String tipo;

  private boolean resuelto;

  // En la vista completa podría mapearse más info del colaborador y la heladera
  // puede servir paraColaborador redirecciones
  public static AlertaDTO completa(Incidente incidente) {


    return AlertaDTO.builder()
        .id(incidente.getId().toString())
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .tipo(incidente.getTipo().getDescription())
        .resuelto(incidente.getEsResuelta())
        .build();
  }

  public static AlertaDTO preview(Incidente incidente) {
    return AlertaDTO.builder()
        .id(incidente.getId().toString())
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .tipo(incidente.getTipo().getDescription())
        .resuelto(incidente.getEsResuelta())
        .build();
  }
}
