package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class OfertaDeProductosDTO extends ColaboracionDTO {

  private String colaborador;

  private String nombreProducto;

  private String rubro;

  private String pathImagen;

  public static OfertaDeProductosDTO completa(OfertaDeProductos ofertaDeProductos) {

    return OfertaDeProductosDTO.builder()
        .id(ofertaDeProductos.getId().toString())
        .nombre(TipoColaboracion.OFERTA_DE_PRODUCTOS.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(ofertaDeProductos.getFechaHora()))
        .path(getPath(TipoColaboracion.OFERTA_DE_PRODUCTOS))
        .colaborador(ofertaDeProductos.getColaborador().getUsuario().getNombre())
        .nombreProducto(ofertaDeProductos.getNombre())
        .rubro(ofertaDeProductos.getRubro().toString())
        .pathImagen(ofertaDeProductos.getImagen().getRuta())
        .build();
  }

  public static ColaboracionDTO preview(OfertaDeProductos ofertaDeProductos) {

    return ColaboracionDTO.builder()
        .id(ofertaDeProductos.getId().toString())
        .nombre(TipoColaboracion.OFERTA_DE_PRODUCTOS.getDescription())
        .fechaHora(DateTimeParser.parseFechaHora(ofertaDeProductos.getFechaHora()))
        .path(getPath(TipoColaboracion.OFERTA_DE_PRODUCTOS))
        .build();
  }
}
