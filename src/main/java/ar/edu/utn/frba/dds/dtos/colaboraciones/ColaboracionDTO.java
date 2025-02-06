package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.RepartoDeTarjeta.RepartoDeTarjetaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionVianda.DonacionViandaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.hacerseCargoHeladera.HacerseCargoHeladeraDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjeta;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Colaboración DTO.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColaboracionDTO {

  protected String id;
  protected String nombre;
  protected String fechaHora;
  protected String path;
  protected String colaborador;

  protected static String getPath(TipoColaboracion colaboracion) {
    return TipoColaboracionDTO.getPath(colaboracion);
  }

  /**
   * Obtiene una vista previa de la colaboración.
   *
   * @param colaboracion colaboración
   * @return DTO
   */
  public static ColaboracionDTO fromColaboracion(Object colaboracion) {
    if (colaboracion instanceof DonacionVianda) {
      return DonacionViandaDTO.fromColaboracion((DonacionVianda) colaboracion);
    } else if (colaboracion instanceof DonacionDinero) {
      return DonacionDineroDTO.fromColaboracion((DonacionDinero) colaboracion);
    } else if (colaboracion instanceof DistribucionViandas) {
      return DistribucionViandasDTO.fromColaboracion((DistribucionViandas) colaboracion);
    } else if (colaboracion instanceof HacerseCargoHeladera) {
      return HacerseCargoHeladeraDTO.fromColaboracion((HacerseCargoHeladera) colaboracion);
    } else if (colaboracion instanceof OfertaDeProductos) {
      return OfertaDeProductosDTO.fromColaboracion((OfertaDeProductos) colaboracion);
    } else if (colaboracion instanceof RepartoDeTarjeta) {
      return RepartoDeTarjetaDTO.fromColaboracion((RepartoDeTarjeta) colaboracion);
    } else {
      throw new IllegalArgumentException(colaboracion.getClass().getName() + " es inválido");
    }
  }
}
