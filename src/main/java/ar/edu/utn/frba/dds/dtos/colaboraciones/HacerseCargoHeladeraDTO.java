package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Hacerse cargo de heladera DTO.
 */
@Getter
public class HacerseCargoHeladeraDTO extends ColaboracionDTO {

  private final String heladeraACargo;

  private HacerseCargoHeladeraDTO(HacerseCargoHeladera hacerseCargoHeladera) {
    super(hacerseCargoHeladera.getId().toString(),
        TipoColaboracion.HACERSE_CARGO_HELADERA.getDescription(),
        DateTimeParser.parseFechaHora(hacerseCargoHeladera.getFechaHora()),
        getPath(TipoColaboracion.HACERSE_CARGO_HELADERA),
        hacerseCargoHeladera.getColaborador().getUsuario().getNombre()
    );
    this.heladeraACargo = hacerseCargoHeladera.getHeladera().getNombre();
  }

  public static HacerseCargoHeladeraDTO fromColaboracion(HacerseCargoHeladera hacerseCargoHeladera) {
    return new HacerseCargoHeladeraDTO(hacerseCargoHeladera);
  }
}
