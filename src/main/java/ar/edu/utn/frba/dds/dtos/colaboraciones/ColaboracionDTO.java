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
      case DONACION_VIANDAS -> "/donacion-vianda";
      case DONACION_DINERO -> "/donacion-dinero";
      case DISTRIBUCION_VIANDAS -> "/distribucion-viandas";
      case HACERSE_CARGO_HELADERA -> "/encargarse-de-heladeras";
      case OFERTA_DE_PRODUCTOS -> "/oferta-producto-servicio";
      case REPARTO_DE_TARJETAS -> "/registro-persona-vulnerable";
    };
  }

  /**
   * Obtiene una vista previa de la colaboración.
   *
   * @param colaboracion     colaboración
   * @param tipoColaboracion tipo de colaboración
   * @return DTO
   */
  public static ColaboracionDTO fromColaboracion(Object colaboracion,
                                                 TipoColaboracion tipoColaboracion) {
    return switch (tipoColaboracion) {
      case DONACION_VIANDAS -> DonacionViandaDTO.fromColaboracion((DonacionVianda) colaboracion);
      case DONACION_DINERO -> DonacionDineroDTO.fromColaboracion((DonacionDinero) colaboracion);
      case DISTRIBUCION_VIANDAS ->
          DistribucionViandasDTO.fromColaboracion((DistribucionViandas) colaboracion);
      case HACERSE_CARGO_HELADERA ->
          HacerseCargoHeladeraDTO.fromColaboracion((HacerseCargoHeladera) colaboracion);
      case OFERTA_DE_PRODUCTOS ->
          OfertaDeProductosDTO.fromColaboracion((OfertaDeProductos) colaboracion);
      case REPARTO_DE_TARJETAS ->
          RepartoDeTarjetasDTO.fromColaboracion((RepartoDeTarjetas) colaboracion);
    };
  }
}
