package ar.edu.utn.frba.dds.dtos.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para la creación de una heladera.
 */
@Getter
@AllArgsConstructor
public class CreateHeladeraDTO {
  private final String nombre;
  private final int capacidad;
  private final String calle;
  private final int altura;
  private final String barrio;
  private final double latitud;
  private final double longitud;
  private final double tempMin;
  private final double tempMax;
}
