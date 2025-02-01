package ar.edu.utn.frba.dds.dtos.colaboraciones.hacerseCargoHeladera;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Hacerse cargo de heladera DTO.
 */
@Getter
public class HacerseCargoHeladeraDTO extends ColaboracionDTO {

  private final String heladera;

  private HacerseCargoHeladeraDTO(HacerseCargoHeladera hacerseCargoHeladera) {
    super(hacerseCargoHeladera.getId().toString(),
        TipoColaboracion.HACERSE_CARGO_HELADERA.getDescription(),
        DateTimeParser.parseFechaHora(hacerseCargoHeladera.getFechaHora()),
        getPath(TipoColaboracion.HACERSE_CARGO_HELADERA),
        hacerseCargoHeladera.getColaborador().getUsuario().getNombre()
    );
    this.heladera = hacerseCargoHeladera.getHeladera().getNombre();
  }

  /**
   * Crea un DTO a partir de una colaboración de tipo HacerseCargoHeladera.
   *
   * @param colaboracion Colaboración de tipo HacerseCargoHeladera
   * @return DTO
   */
  public static HacerseCargoHeladeraDTO fromColaboracion(HacerseCargoHeladera colaboracion) {
    return new HacerseCargoHeladeraDTO(colaboracion);
  }
}
