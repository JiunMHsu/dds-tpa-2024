package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Reparto de tarjetas DTO.
 */
@Getter
public class RepartoDeTarjetasDTO extends ColaboracionDTO {

  private final String tarjeta;
  private final String personaVulnerable;

  private RepartoDeTarjetasDTO(RepartoDeTarjetas repartoDeTarjetas) {
    super(repartoDeTarjetas.getId().toString(),
        TipoColaboracion.REPARTO_DE_TARJETAS.getDescription(),
        DateTimeParser.parseFechaHora(repartoDeTarjetas.getFechaHora()),
        getPath(TipoColaboracion.REPARTO_DE_TARJETAS),
        repartoDeTarjetas.getColaborador().getUsuario().getNombre()
    );
    this.tarjeta = repartoDeTarjetas.getTarjeta().getCodigo();
    this.personaVulnerable = repartoDeTarjetas.getPersonaVulnerable().getNombre();
  }

  public static RepartoDeTarjetasDTO fromColaboracion(RepartoDeTarjetas repartoDeTarjetas) {
    return new RepartoDeTarjetasDTO(repartoDeTarjetas);
  }
}

