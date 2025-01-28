package ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Donación de dinero DTO.
 */
@Getter
public class DonacionDineroDTO extends ColaboracionDTO {

  private final String monto;

  private DonacionDineroDTO(DonacionDinero donacionDinero) {
    super(donacionDinero.getId().toString(),
        TipoColaboracion.DONACION_DINERO.getDescription(),
        DateTimeParser.parseFechaHora(donacionDinero.getFechaHora()),
        getPath(TipoColaboracion.DONACION_DINERO),
        donacionDinero.getColaborador().getUsuario().getNombre()
    );
    this.monto = donacionDinero.getMonto().toString();
  }

  public static DonacionDineroDTO fromColaboracion(DonacionDinero donacionDinero) {
    return new DonacionDineroDTO(donacionDinero);
  }
}

