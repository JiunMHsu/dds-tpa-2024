package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.Optional;

public class HacerseCargoHeladeraService implements WithSimplePersistenceUnit {

  private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

  public HacerseCargoHeladeraService(HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository) {
    this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
  }

  public Optional<HacerseCargoHeladera> buscarPorId(String id) {
    return hacerseCargoHeladeraRepository.buscarPorId(id);
  }

  public void registrar(HacerseCargoHeladera hacerseCargoHeladera) {
    beginTransaction();
    hacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera);
    commitTransaction();
  }
}
