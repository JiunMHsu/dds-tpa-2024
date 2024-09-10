package ar.edu.utn.frba.dds.models.personaVulnerable;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "canje_puntos")
public class PersonaVulnerable {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @Column(name = "nombre")
  private String nombre;

  @Embedded
  private Documento documento;

  @Column (name = "fecha_nacimiento")
  private LocalDate fechaNacimiento;

  @Column (name = "fecha_registro")
  private LocalDate fechaRegistro;

  // TODO - Hacer mapeo de la dirección, @Embedded o @OneToOne?
  private Direccion domicilio;

  @Column (name = "menores_a_cargo")
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

