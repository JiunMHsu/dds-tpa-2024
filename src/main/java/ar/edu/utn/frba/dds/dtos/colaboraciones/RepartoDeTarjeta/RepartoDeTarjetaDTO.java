package ar.edu.utn.frba.dds.dtos.colaboraciones.RepartoDeTarjeta;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Reparto de tarjeta DTO.
 */
@Getter
public class RepartoDeTarjetaDTO extends ColaboracionDTO {

  private final String tarjeta;
  private final String personaVulnerable;

  private RepartoDeTarjetaDTO(RepartoDeTarjeta repartoDeTarjeta) {
    super(repartoDeTarjeta.getId().toString(),
        TipoColaboracion.REPARTO_DE_TARJETAS.getDescription(),
        DateTimeParser.parseFechaHora(repartoDeTarjeta.getFechaHora()),
        getPath(TipoColaboracion.REPARTO_DE_TARJETAS),
        repartoDeTarjeta.getColaborador().getUsuario().getNombre()
    );
    this.tarjeta = repartoDeTarjeta.getTarjeta().getCodigo();
    this.personaVulnerable = repartoDeTarjeta.getPersonaVulnerable().getNombre();
  }

  public static RepartoDeTarjetaDTO fromColaboracion(RepartoDeTarjeta repartoDeTarjeta) {
    return new RepartoDeTarjetaDTO(repartoDeTarjeta);
  }
}

