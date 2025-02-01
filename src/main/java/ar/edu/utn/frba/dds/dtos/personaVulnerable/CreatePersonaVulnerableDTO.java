package ar.edu.utn.frba.dds.dtos.personaVulnerable;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de creación de persona vulnerable.
 */
@Getter
@AllArgsConstructor
public class CreatePersonaVulnerableDTO {
  private final String nombre;
  private final String tipoDocumento;
  private final String nroDocumento;
  private final LocalDate fechaNacimiento;
  private final String barrio;
  private final String calle;
  private final int altura;
  private final int menoresACargo;
  private final String tarjetaAsignada;
}
