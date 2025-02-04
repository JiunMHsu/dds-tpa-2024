package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de Incidente.
 */
@Getter
@Builder
public class IncidenteDTO {

  private String id;
  private String heladera;
  private String fecha;
  private String hora;
  private String tipo;
  private boolean resuelto;
  private String path;

  /**
   * Convierte un Incidente en un IncidenteDTO.
   *
   * @param incidente Incidente
   * @return IncidenteDTO
   */
  public static IncidenteDTO fromIncidente(Incidente incidente) {

    String ruta;
    if (incidente.getTipo().equals(TipoIncidente.FALLA_TECNICA)) {
      ruta = "/fallas-tecnicas/";
    } else {
      ruta = "/alertas/";
    }

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
