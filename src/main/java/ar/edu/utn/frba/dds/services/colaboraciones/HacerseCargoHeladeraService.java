package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.hacerseCargoHeladera.HacerseCargoHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;

/**
 * Servicio de colaboraciones de tipo HacerseCargoHeladera.
 */
public class HacerseCargoHeladeraService implements WithSimplePersistenceUnit {

  private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
  private final ColaboradorRepository colaboradorRepository;
  private final HeladeraRepository heladeraRepository;

  /**
   * Constructor de HacerseCargoHeladeraService.
   *
   * @param hacerseCargoHeladeraRepository Repositorio de colaboraciones de tipo HacerseCargoHeladera
   * @param colaboradorRepository          Repositorio de colaboradores
   * @param heladeraRepository             Repositorio de heladeras
   */
  public HacerseCargoHeladeraService(HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                                     ColaboradorRepository colaboradorRepository,
                                     HeladeraRepository heladeraRepository) {
    this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.heladeraRepository = heladeraRepository;
  }

  /**
   * Registra una colaboración de tipo HacerseCargoHeladera.
   *
   * @param colaborador Colaborador que se hace cargo de la heladera
   * @param heladeraId  Id de la heladera
   */
  public void registrar(Colaborador colaborador, String heladeraId) {
    Heladera heladera = heladeraRepository.buscarPorId(heladeraId)
        .orElseThrow(ResourceNotFoundException::new);

    HacerseCargoHeladera hacerseCargoHeladera = HacerseCargoHeladera.por(
        colaborador,
        LocalDateTime.now(),
        heladera
    );

    colaborador.invalidarPuntos();

    beginTransaction();
    hacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera);
    colaboradorRepository.actualizar(colaborador);
    commitTransaction();
  }

  /**
   * Busca una colaboración de tipo HacerseCargoHeladera por su id.
   *
   * @param id Id de la colaboración
   * @return DTO de la colaboración
   * @throws ResourceNotFoundException Si no se encuentra la colaboración
   */
  public HacerseCargoHeladeraDTO buscarPorId(String id) {
    return HacerseCargoHeladeraDTO.fromColaboracion(
        hacerseCargoHeladeraRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new)
    );
  }
}
