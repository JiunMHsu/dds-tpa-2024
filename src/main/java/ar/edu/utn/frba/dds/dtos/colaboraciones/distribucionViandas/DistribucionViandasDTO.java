package ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
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
    this.heladeraOrigen = (distribucionViandas.getOrigen() != null)
        ? distribucionViandas.getOrigen().getNombre()
        : "Origen desconocido";
    this.heladeraDestino = (distribucionViandas.getDestino() != null)
        ? distribucionViandas.getDestino().getNombre()
        : "Destino desconocido";
    this.cantViandas = distribucionViandas.getViandas() != null
        ? distribucionViandas.getViandas().toString()
        : "Desconocida";
    this.motivo = distribucionViandas.getMotivo();
    this.estado = distribucionViandas.getEstado().toString();
  }


  public static DistribucionViandasDTO fromColaboracion(DistribucionViandas distribucionViandas) {
    return new DistribucionViandasDTO(distribucionViandas);
  }
}
