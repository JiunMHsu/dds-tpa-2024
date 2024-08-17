package ar.edu.utn.frba.dds.models.personaVulnerable;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonaVulnerable {

  private String nombre;
  private Documento documento;
  private LocalDate fechaNacimiento;
  private LocalDate fechaRegistro;
  private Direccion domicilio;
  private Integer menoresACargo;

  public static PersonaVulnerable con(String nombre,
                                      Documento documento,
                                      LocalDate fechaNacimiento,
                                      LocalDate fechaRegistro,
                                      Direccion domicilio,
                                      Integer menoresACargo) {
    return PersonaVulnerable
        .builder()
        .nombre(nombre)
        .documento(documento)
        .fechaNacimiento(fechaNacimiento)
        .fechaRegistro(fechaRegistro)
        .domicilio(domicilio)
        .menoresACargo(menoresACargo)
        .build();
  }

  public static PersonaVulnerable con(String nombre, Documento documento, Integer menoresACargo) {
    return PersonaVulnerable
        .builder()
        .nombre(nombre)
        .documento(documento)
        .menoresACargo(menoresACargo)
        .build();
  }

  public static PersonaVulnerable con(String nombre, Documento documento) {
    return PersonaVulnerable
        .builder()
        .nombre(nombre)
        .documento(documento)
        .build();
  }

  public static PersonaVulnerable con(String nombre, Integer menoresACargo) {
    return PersonaVulnerable
        .builder()
        .nombre(nombre)
        .menoresACargo(menoresACargo)
        .build();
  }

  public static PersonaVulnerable con(String nombre) {
    return PersonaVulnerable
        .builder()
        .nombre(nombre)
        .build();
  }
}

