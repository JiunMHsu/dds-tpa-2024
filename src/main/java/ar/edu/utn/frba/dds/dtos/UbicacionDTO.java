package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de ubicación.
 */
@Getter
@Setter
@Builder
public class UbicacionDTO {
  private String latitud;
  private String longitud;

  /**
   * Convierte una ubicación en un DTO.
   *
   * @param ubicacion Ubicación.
   * @return DTO.
   */
  public static UbicacionDTO fromUbicacion(Ubicacion ubicacion) {
    return UbicacionDTO
        .builder()
        .latitud(String.valueOf(ubicacion.getLatitud()))
        .longitud(String.valueOf(ubicacion.getLongitud()))
        .build();
  }
}
