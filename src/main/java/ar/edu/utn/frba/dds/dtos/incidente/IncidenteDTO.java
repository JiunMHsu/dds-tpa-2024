package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IncidenteDTO {

  private String id;

  private String heladera;

  private String fechaHora;

  private String tipo;

  private String colaborador;

  private String descripcion;

  private String foto;

  public static IncidenteDTO completa(Incidente incidente) {

    return IncidenteDTO
        .builder()
        .heladera(incidente.getHeladera().getNombre())
        .fechaHora(incidente.getFechaHora().toString())
        .tipo(incidente.getTipo().toString())
        .colaborador(incidente.getColaborador().getUsuario().getNombre())
        .descripcion(incidente.getDescripcion())
        .foto(incidente.getFoto().getRuta())
        .build();
  }

  public static IncidenteDTO reporte(Incidente incidente) {

    return IncidenteDTO
        .builder()
        .heladera(incidente.getHeladera().getNombre())
        .descripcion(incidente.getDescripcion())
        .build();
  }

  public static IncidenteDTO alerta(Incidente incidente) {

    return IncidenteDTO
        .builder()
        .heladera(incidente.getHeladera().getNombre())
        .fechaHora(incidente.getFechaHora().toString())
        .descripcion(incidente.getDescripcion())
        .build();
  }
}
