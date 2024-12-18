package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ProductoDTO {

  private String id;

  private String nombre;

  private String rubro;

  private String puntosNecesarios;

  private String pathImagen;

  public static ProductoDTO completa(OfertaDeProductos producto) {
    return ProductoDTO.builder()
        .id(producto.getId().toString())
        .nombre(producto.getNombre())
        .rubro(producto.getRubro().toString())
        .puntosNecesarios(Double.toString(producto.getPuntosNecesarios()))
        .pathImagen(producto.getImagen().getRuta())
        .build();
  }

  public static ProductoDTO preview(OfertaDeProductos producto) {

    return ProductoDTO.builder()
        .id(producto.getId().toString())
        .nombre(producto.getNombre())
        .puntosNecesarios(Double.toString(producto.getPuntosNecesarios()))
        .pathImagen(producto.getImagen().getRuta())
        .build();
  }
}
