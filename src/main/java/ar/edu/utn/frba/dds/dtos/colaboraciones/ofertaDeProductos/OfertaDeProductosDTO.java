package ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Oferta de productos DTO.
 */
@Getter
public class OfertaDeProductosDTO extends ColaboracionDTO {

  private final String nombreProducto;
  private final String rubro;
  private final String pathImagen;

  private OfertaDeProductosDTO(OfertaDeProductos ofertaDeProductos) {
    super(ofertaDeProductos.getId().toString(),
        TipoColaboracion.OFERTA_DE_PRODUCTOS.getDescription(),
        DateTimeParser.parseFechaHora(ofertaDeProductos.getFechaHora()),
        getPath(TipoColaboracion.OFERTA_DE_PRODUCTOS),
        ofertaDeProductos.getColaborador().getUsuario().getNombre()
    );
    this.nombreProducto = (ofertaDeProductos.getNombre() != null)
        ? ofertaDeProductos.getNombre()
        : "Producto desconocido";
    this.rubro = (ofertaDeProductos.getRubro() != null)
        ? ofertaDeProductos.getRubro().toString()
        : "Rubro desconocido";
    this.pathImagen = (ofertaDeProductos.getImagen() != null)
        ? ofertaDeProductos.getImagen().getRuta()
        : "Sin imagen";
  }

  public static OfertaDeProductosDTO fromColaboracion(OfertaDeProductos ofertaDeProductos) {
    return new OfertaDeProductosDTO(ofertaDeProductos);
  }
}
