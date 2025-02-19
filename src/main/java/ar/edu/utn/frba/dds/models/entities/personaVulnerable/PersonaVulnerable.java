package ar.edu.utn.frba.dds.models.entities.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa a una persona vulnerable.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persona_vulnerable")
public class PersonaVulnerable extends EntidadPersistente {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Embedded
  private Documento documento;

  @Column(name = "fecha_nacimiento", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaNacimiento;

  @Column(name = "fecha_registro", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaRegistro;

  @Embedded
  private Direccion domicilio;

  @Column(name = "menores_a_cargo", nullable = false)
  private Integer menoresACargo;

  /**
   * Crea una persona vulnerable con los datos mínimos.
   *
   * @param nombre          Nombre de la persona.
   * @param documento       Documento de la persona.
   * @param fechaNacimiento Fecha de nacimiento de la persona.
   * @param fechaRegistro   Fecha de registro de la persona.
   * @param domicilio       Domicilio de la persona.
   * @param menoresACargo   Cantidad de menores a cargo.
   * @return Persona vulnerable creada.
   */
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

  /**
   * Crea una persona vulnerable con los datos mínimos.
   *
   * @param nombre        Nombre de la persona.
   * @param menoresACargo Cantidad
   * @return Persona vulnerable creada.
   */
  public static PersonaVulnerable con(String nombre, Integer menoresACargo) {
    return PersonaVulnerable.con(
        nombre,
        null,
        LocalDate.now(),
        LocalDate.now(),
        null,
        menoresACargo
    );
  }

}

