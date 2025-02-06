package ar.edu.utn.frba.dds.dtos.tecnico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Create Tecnico DTO.
 */
@Getter
@AllArgsConstructor
@Builder
public class CreateTecnicoDTO {
  private final String nombre;
  private final String apellido;
  private final String tipoDocumento;
  private final String nroDocumento;
  private final String cuil;
  private final double latitud;
  private final double longitud;
  private final int radio;
  private final String barrio;
}
