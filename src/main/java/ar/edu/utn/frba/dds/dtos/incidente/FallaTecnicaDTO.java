package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de Falla Técnica.
 */
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

  /**
   * Mapea un incidente a un DTO.
   *
   * @param incidente Incidente
   * @return DTO
   */
  public static FallaTecnicaDTO fromIncidente(Incidente incidente) {
    return FallaTecnicaDTO.builder()
        .id(incidente.getId().toString())
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .colaborador(incidente.getColaborador().getNombre())
        .descripcion(incidente.getDescripcion())
        .foto(incidente.getFoto().getRuta())
        .resuelto(incidente.getEsResuelta())
        .build();
  }

}
