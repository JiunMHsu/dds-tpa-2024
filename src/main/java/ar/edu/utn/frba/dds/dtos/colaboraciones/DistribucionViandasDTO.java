package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Distribución de viandas DTO.
 */
@Getter
public class DistribucionViandasDTO extends ColaboracionDTO {

  private final String heladeraOrigen;
  private final String heladeraDestino;
  private final String cantViandas;
  private final String motivo;
  private final String estado;

  private DistribucionViandasDTO(DistribucionViandas distribucionViandas) {
    super(distribucionViandas.getId().toString(),
        TipoColaboracion.DISTRIBUCION_VIANDAS.getDescription(),
        DateTimeParser.parseFechaHora(distribucionViandas.getFechaHora()),
        getPath(TipoColaboracion.DISTRIBUCION_VIANDAS),
        distribucionViandas.getColaborador().getUsuario().getNombre()
    );
    this.heladeraOrigen = distribucionViandas.getOrigen().getNombre();
    this.heladeraDestino = distribucionViandas.getDestino().getNombre();
    this.cantViandas = distribucionViandas.getViandas().toString();
    this.motivo = distribucionViandas.getMotivo();
    this.estado = distribucionViandas.getEstado().toString();
  }

  public static DistribucionViandasDTO fromColaboracion(DistribucionViandas distribucionViandas) {
    return new DistribucionViandasDTO(distribucionViandas);
  }
}
