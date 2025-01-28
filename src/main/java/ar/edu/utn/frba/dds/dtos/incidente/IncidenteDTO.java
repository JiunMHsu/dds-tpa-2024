package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IncidenteDTO {

  private String id;

  private String heladera;

  private String fecha;

  private String hora;

  private String tipo;

  private boolean resuelto;

  private String path;

  public static IncidenteDTO preview(Incidente incidente) {

    String ruta = switch (incidente.getTipo()) {
      case FALLA_TECNICA -> "/fallas-tecnicas/";
      default -> "/alertas/";
    };

    return IncidenteDTO
        .builder()
        .id(incidente.getId().toString())
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .tipo(incidente.getTipo().getDescription())
        .resuelto(incidente.getEsResuelta())
        .path(ruta + incidente.getId().toString())
        .build();
  }

}
