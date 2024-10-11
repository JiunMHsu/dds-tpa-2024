package ar.edu.utn.frba.dds.services.personaVulnerable;

import ar.edu.utn.frba.dds.dtos.personaVulnerable.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.IPersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class PersonaVulnerableService {

    private final IPersonaVulnerableRepository personaVulnerableRepository;

    public PersonaVulnerableService (IPersonaVulnerableRepository personaVulnerableRepository) {
        this.personaVulnerableRepository = personaVulnerableRepository;
    }

    public void guardarPV (PersonaVulnerable personaVulnerable) {

        if (personaVulnerable.getDocumento() == null || personaVulnerable.getDomicilio() == null) {
            throw new IllegalArgumentException("Datos incompletos de la persona vulnerable");
        }

        Optional<PersonaVulnerable> existente = personaVulnerableRepository.buscarPorDocumento(personaVulnerable.getDocumento().getNumero());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El documento ya está registrado en el sistema");
        }

        this.personaVulnerableRepository.guardar(personaVulnerable);
    }

    // por ahora dejo este nombre no c si seguimos un patron
//    public PersonaVulnerable crearPersonaVulnerable(PersonaVulnerableDTO dto) {
//
//        TipoDocumento tipoDocumento = TipoDocumento.valueOf(dto.getTipoDocumento().toUpperCase());
//
//        Documento documento = new Documento(tipoDocumento, dto.getNroDocumento());
//
//        LocalDate fechaNacimiento = LocalDate.parse(dto.getFechaNacimiento());
//
//        LocalDate fechaRegistro = LocalDate.parse(dto.getFechaRegistro());
//
//        Direccion direccion = new Direccion(); // TODO - ver que onda, quizas conviene delegar la instanciacion a otro lado
//
//        Integer menoresACargo = Integer.valueOf(dto.getMenoresACargo());
//
//        PersonaVulnerable nuevaPersonaVulnerable = new PersonaVulnerable(
//                dto.getNombre(),
//                documento,
//                fechaNacimiento,
//                fechaRegistro,
//                direccion,
//                menoresACargo);
//
//        personaVulnerableRepository.guardar(nuevaPersonaVulnerable);
//
//        return nuevaPersonaVulnerable;
//    }

}
