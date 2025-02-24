package ar.edu.utn.frba.dds.dtos.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object de Visita Técnica.
 */
@Getter
@Builder
public class VisitaTecnicaDTO {

  private String id;
  private String fecha;
  private String hora;
  private String heladera;
  private String descripcion;
  private String foto;
  private boolean pudoResolverse;

  /**
   * Convierte una Visita Técnica en un VisitaTecnicaDTO.
   *
   * @param visitaTecnica Visita Técnica
   * @return VisitaTecnicaDTO
   */
  public static VisitaTecnicaDTO fromVisitaTecnica(VisitaTecnica visitaTecnica) {
    return builder()
        .id(visitaTecnica.getId().toString())
        .fecha(DateTimeParser.parseFecha(visitaTecnica.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(visitaTecnica.getFechaHora().toLocalTime()))
        .heladera(visitaTecnica.getHeladera().getNombre())
        .descripcion(visitaTecnica.getDescripcion())
        .foto(visitaTecnica.getFoto().getRuta())
        .pudoResolverse(visitaTecnica.isPudoResolverse())
        .build();
  }

}
