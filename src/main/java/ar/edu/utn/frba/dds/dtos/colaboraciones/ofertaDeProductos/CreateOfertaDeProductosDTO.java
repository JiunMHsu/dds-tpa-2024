package ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos;

import io.javalin.http.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para la creación de una oferta de productos.
 */
@Getter
@AllArgsConstructor
public class CreateOfertaDeProductosDTO {
  private final String nombre;
  private final double puntosNecesarios;
  private final String rubro;
  private final UploadedFile imagen;
}
