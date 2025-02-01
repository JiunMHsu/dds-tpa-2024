package ar.edu.utn.frba.dds.dtos.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de persona vulnerable.
 */
@Getter
@Builder
public class PersonaVulnerableDTO {

  private String id;
  private String nombre;
  private String tipoDocumento;
  private String nroDocumento;
  private String fechaNacimiento;
  private String fechaRegistro;
  private String domicilio;
  private String menoresACargo;

  /**
   * Convierte una persona vulnerable en un DTO.
   * TODO: Revisar, puede tener atributos en null
   *
   * @param personaVulnerable Persona vulnerable
   * @return DTO
   */
  public static PersonaVulnerableDTO fromPersonaVulnerable(PersonaVulnerable personaVulnerable) {

    return PersonaVulnerableDTO
        .builder()
        .id(personaVulnerable.getId().toString())
        .nombre(personaVulnerable.getNombre())
        .tipoDocumento(personaVulnerable.getDocumento().getTipo().toString())
        .nroDocumento(personaVulnerable.getDocumento().getNumero())
        .fechaNacimiento(DateTimeParser.parseFecha(personaVulnerable.getFechaNacimiento()))
        .fechaRegistro(DateTimeParser.parseFecha(personaVulnerable.getFechaRegistro()))
        .domicilio(personaVulnerable.getDomicilio().toString())
        .menoresACargo(personaVulnerable.getMenoresACargo().toString())
        .build();
  }
}
