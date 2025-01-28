package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HacerseCargoHeladeraDTO extends ColaboracionDTO {

  private String colaborador;

  private String heladeraACargo;

  public static HacerseCargoHeladeraDTO completa(HacerseCargoHeladera hacerseCargoHeladera) {

    return HacerseCargoHeladeraDTO.builder()
        .id(hacerseCargoHeladera.getId().toString())
        .nombre(TipoColaboracion.HACERSE_CARGO_HELADERA.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(hacerseCargoHeladera.getFechaHora()))
        .path(getPath(TipoColaboracion.HACERSE_CARGO_HELADERA))
        .colaborador(hacerseCargoHeladera.getColaborador().getUsuario().getNombre())
        .heladeraACargo(hacerseCargoHeladera.getHeladera().getNombre())
        .build();
  }

  public static ColaboracionDTO preview(HacerseCargoHeladera hacerseCargoHeladera) { // TODO - ver si se ajusta a la vista

    return ColaboracionDTO.builder()
        .id(hacerseCargoHeladera.getId().toString())
        .nombre(TipoColaboracion.HACERSE_CARGO_HELADERA.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(hacerseCargoHeladera.getFechaHora()))
        .path(getPath(TipoColaboracion.HACERSE_CARGO_HELADERA))
        .build();
  }
}
