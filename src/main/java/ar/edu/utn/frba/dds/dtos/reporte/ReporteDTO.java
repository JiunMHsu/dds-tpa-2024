package ar.edu.utn.frba.dds.dtos.reporte;

import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de reporte.
 */
@Getter
@Setter
@Builder
public class ReporteDTO {

  private String id;
  private String titulo;
  private String fecha;
  private String nombreArchivo;

  /**
   * Completa un DTO de reporte.
   *
   * @param reporte reporte
   * @return DTO de reporte
   */
  public static ReporteDTO completa(Reporte reporte) {
    return ReporteDTO
        .builder()
        .id(reporte.getId().toString())
        .titulo(reporte.getTitulo())
        .fecha(DateTimeParser.parseFecha(reporte.getFecha()))
        .nombreArchivo(reporte.getNombreArchivo())
        .build();
  }

}
