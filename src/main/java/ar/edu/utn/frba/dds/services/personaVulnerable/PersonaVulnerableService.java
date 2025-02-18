package ar.edu.utn.frba.dds.services.personaVulnerable;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 * Servicio para manejar las operaciones relacionadas con personas vulnerables.
 */
@Getter
@Setter
public class PersonaVulnerableService {

  private final PersonaVulnerableRepository personaVulnerableRepository;

  /**
   * Constructor para inicializar el repositorio de personas vulnerables.
   *
   * @param personaVulnerableRepository El repositorio de personas vulnerables.
   */
  public PersonaVulnerableService(PersonaVulnerableRepository personaVulnerableRepository) {
    this.personaVulnerableRepository = personaVulnerableRepository;
  }

  /**
   * Busca todas las personas vulnerables en el repositorio.
   *
   * @return Una lista de todas las personas vulnerables.
   */
  public List<PersonaVulnerable> buscarTodosPV() {
    return this.personaVulnerableRepository.buscarTodos();
  }

  /**
   * Busca una persona vulnerable por su ID.
   *
   * @param id El ID de la persona vulnerable.
   * @return Un Optional que contiene la persona vulnerable si se encuentra.
   * @throws IllegalArgumentException si el ID es null o vacío.
   */
  public Optional<PersonaVulnerable> buscarPVPorId(String id) {

    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("El ID de la persona en situacion vulnerable no puede ser null o vacío");
    }

    return this.personaVulnerableRepository.buscarPorId(id);
  }

  /**
   * Guarda una nueva persona vulnerable en el repositorio.
   *
   * @param personaVulnerable La persona vulnerable a guardar.
   * @throws IllegalArgumentException si los datos de la persona vulnerable son incompletos
   * o si el documento ya está registrado en el sistema.
   */
  public void guardarPV(PersonaVulnerable personaVulnerable) {

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

  /**
   * Elimina una persona vulnerable del repositorio.
   *
   * @param id El ID de la persona vulnerable a eliminar.
   * @throws IllegalArgumentException si el ID es null o vacío o si la persona no existe en el sistema.
   */
  public void eliminarPV(String id) {

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


  /**
   * Actualiza los datos de una persona vulnerable existente en el repositorio.
   *
   * @param id    El ID de la persona vulnerable a actualizar.
   * @param input Los nuevos datos de la persona vulnerable.
   * @throws ResourceNotFoundException si la persona vulnerable no se encuentra en el sistema.
   */
  public void actualizarPersonaVulnerable(String id, PersonaVulnerable input) {
    PersonaVulnerable personaVulnerable = personaVulnerableRepository
        .buscarPorId(id).orElseThrow(ResourceNotFoundException::new);

    personaVulnerable.setNombre(input.getNombre());
    personaVulnerable.setDocumento(input.getDocumento());
    personaVulnerable.setFechaNacimiento(input.getFechaNacimiento());
    personaVulnerable.setDomicilio(input.getDomicilio());
    personaVulnerable.setMenoresACargo(input.getMenoresACargo());

    this.personaVulnerableRepository.actualizar(personaVulnerable);
  }


}
