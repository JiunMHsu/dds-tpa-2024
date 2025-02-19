package ar.edu.utn.frba.dds.dtos.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Producto DTO.
 */
@Getter
@Setter
@SuperBuilder
public class ProductoDTO {

  private String id;

  private String nombre;

  private String rubro;

  private String puntosNecesarios;

  private String pathImagen;

  /**
   * Builder completo del DTO Producto.
   *
   * @param producto Producto
   * @return DTO Producto
   */
  public static ProductoDTO completa(OfertaDeProductos producto) {
    return ProductoDTO.builder()
        .id(producto.getId().toString())
        .nombre(producto.getNombre())
        .rubro(producto.getRubro().toString())
        .puntosNecesarios(Double.toString(producto.getPuntosNecesarios()))
        .pathImagen(producto.getImagen().getRuta())
        .build();
  }

  /**
   * Builder preview del DTO Producto.
   *
   * @param producto Producto
   * @return DTO Producto
   */
  public static ProductoDTO preview(OfertaDeProductos producto) {

    return ProductoDTO.builder()
        .id(producto.getId().toString())
        .nombre(producto.getNombre())
        .puntosNecesarios(Double.toString(producto.getPuntosNecesarios()))
        .pathImagen(producto.getImagen().getRuta())
        .build();
  }
}
