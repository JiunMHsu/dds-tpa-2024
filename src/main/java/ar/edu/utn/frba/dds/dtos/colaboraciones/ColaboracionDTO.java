package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
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
    return switch (colaboracion) {
      case DONACION_VIANDAS -> "donacion-vianda";
      case DONACION_DINERO -> "donacion-dinero";
      case DISTRIBUCION_VIANDAS -> "distribucion-viandas";
      case HACERSE_CARGO_HELADERA -> "encargarse-de-heladeras";
      case OFERTA_DE_PRODUCTOS -> "oferta-producto-servicio";
      case REPARTO_DE_TARJETAS -> "registro-persona-vulnerable";
    };
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
    } else if (colaboracion instanceof RepartoDeTarjetas) {
      return RepartoDeTarjetasDTO.fromColaboracion((RepartoDeTarjetas) colaboracion);
    } else {
      throw new IllegalArgumentException(colaboracion.getClass().getName() + " es inválido");
    }
  }
}
