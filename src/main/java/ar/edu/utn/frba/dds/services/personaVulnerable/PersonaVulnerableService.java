package ar.edu.utn.frba.dds.services.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.IPersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PersonaVulnerableService {

    private final IPersonaVulnerableRepository personaVulnerableRepository;

    public PersonaVulnerableService (IPersonaVulnerableRepository personaVulnerableRepository) {
        this.personaVulnerableRepository = personaVulnerableRepository;
    }

    public List<PersonaVulnerable> buscarTodosPV() {
        return this.personaVulnerableRepository.buscarTodos();
    }

    public Optional<PersonaVulnerable> buscarPVPorId (String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID de la persona en situacion vulnerable no puede ser null o vacío");
        }

        return this.personaVulnerableRepository.buscarPorId(id);
    }

    public void guardarPV (PersonaVulnerable personaVulnerable) {

        System.out.println("Antes del if documento service pv");

        if (personaVulnerable.getDocumento() == null || personaVulnerable.getDomicilio() == null) {
            throw new IllegalArgumentException("Datos incompletos de la persona vulnerable");
        }

        System.out.println("Desp del if documento service pv");

        System.out.println("Antes del if service pv");

        Optional<PersonaVulnerable> existente = personaVulnerableRepository.buscarPorDocumento(personaVulnerable.getDocumento().getNumero());

        if (existente.isPresent()) {
            throw new IllegalArgumentException("El documento ya está registrado en el sistema");
        }

        System.out.println("Desp del if service pv");


        this.personaVulnerableRepository.guardar(personaVulnerable);
    }

    public void eliminarPV (String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID de la persona en situación vulnerable no puede ser null o vacío");
        }

        Optional<PersonaVulnerable> posiblePersonaVulnerable = this.personaVulnerableRepository.buscarPorId(id);
        if (posiblePersonaVulnerable.isEmpty()) {
            throw new IllegalArgumentException("La persona en situación vulnerable vulnerable no existe en el sistema");
        }

        this.personaVulnerableRepository.eliminar(posiblePersonaVulnerable.get());

        // Deberia haber aplicar alguna logica sobre las tarjetas vinculadas a la PV?
    }

}
