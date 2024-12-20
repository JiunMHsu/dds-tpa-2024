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

  private String colaborador;

  private String descripcion;

  private String foto;

  private boolean resuelto;

  public static IncidenteDTO preview(Incidente incidente) {

    return IncidenteDTO
        .builder()
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .tipo(incidente.getTipo().toString())
        .resuelto(incidente.getResuelta())
        .build();
  }

  public static IncidenteDTO reporte(Incidente incidente) {

    return IncidenteDTO
        .builder()
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .tipo(incidente.getTipo().toString())
        .colaborador(incidente.getColaborador().getUsuario().getNombre())
        .descripcion(incidente.getDescripcion())
        .foto(incidente.getFoto().getRuta())
        .resuelto(incidente.getResuelta())
        .build();
  }

  public static IncidenteDTO alerta(Incidente incidente) {

    return IncidenteDTO
        .builder()
        .heladera(incidente.getHeladera().getNombre())
        .fecha(DateTimeParser.parseFecha(incidente.getFechaHora().toLocalDate()))
        .hora(DateTimeParser.parseHora(incidente.getFechaHora().toLocalTime()))
        .resuelto(incidente.getResuelta())
        .build();
  }
}
