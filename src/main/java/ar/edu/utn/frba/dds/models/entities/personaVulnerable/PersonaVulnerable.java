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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persona_vulnerable")
public class PersonaVulnerable extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @Embedded
    private Documento documento;

    @Column(name = "fecha_nacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Embedded
    private Direccion domicilio;

    @Column(name = "menores_a_cargo")
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

    public static PersonaVulnerable con(String nombre, Integer menoresACargo) {
        return PersonaVulnerable.con(nombre, null, null, null, null, menoresACargo);
    }

}

