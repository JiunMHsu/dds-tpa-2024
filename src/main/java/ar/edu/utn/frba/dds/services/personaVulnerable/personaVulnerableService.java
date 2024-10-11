package ar.edu.utn.frba.dds.services.personaVulnerable;

import ar.edu.utn.frba.dds.dtos.personaVulnerable.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;

import java.io.PipedWriter;
import java.time.LocalDate;

public class personaVulnerableService {

    private PersonaVulnerableRepository personaVulnerableRepository;

    // por ahora dejo este nombre no c si seguimos un patron
    public PersonaVulnerable crearPersonaVulnerable(PersonaVulnerableDTO dto) {

        TipoDocumento tipoDocumento = TipoDocumento.valueOf(dto.getTipoDocumento().toUpperCase());

        Documento documento = new Documento(tipoDocumento, dto.getNroDocumento());

        LocalDate fechaNacimiento = LocalDate.parse(dto.getFechaNacimiento());

        LocalDate fechaRegistro = LocalDate.parse(dto.getFechaRegistro());

        Direccion direccion = new Direccion(); // TODO - ver que onda, quizas conviene delegar la instanciacion a otro lado

        Integer menoresACargo = Integer.valueOf(dto.getMenoresACargo());

        PersonaVulnerable nuevaPersonaVulnerable = new PersonaVulnerable(
                dto.getNombre(),
                documento,
                fechaNacimiento,
                fechaRegistro,
                direccion,
                menoresACargo);

        personaVulnerableRepository.agregar(nuevaPersonaVulnerable);

        return nuevaPersonaVulnerable;
    }

}
