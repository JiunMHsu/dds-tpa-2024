package ar.edu.utn.frba.dds.dtos.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonaVulnerableDTO {

    private String nombre;

    private String tipoDocumento; // agrego paraColaborador la instanciacion del Documento

    private String nroDocumento;

    private String fechaNacimiento;

    private String fechaRegistro;

    private String domicilio;

    private String menoresACargo;

    public static PersonaVulnerableDTO completa(PersonaVulnerable personaVulnerable) {

        String domicilioString = personaVulnerable.getDomicilio().getCalle().getNombre() + " " + personaVulnerable.getDomicilio().getAltura().toString();

        return PersonaVulnerableDTO
                .builder()
                .nombre(personaVulnerable.getNombre())
                .tipoDocumento(personaVulnerable.getDocumento().getTipo().toString())
                .nroDocumento(personaVulnerable.getDocumento().getNumero())
                .fechaNacimiento(personaVulnerable.getFechaNacimiento().toString())
                .fechaRegistro(personaVulnerable.getFechaRegistro().toString())
                .domicilio(domicilioString)
                .menoresACargo(personaVulnerable.getMenoresACargo().toString())
                .build();
    }
}
