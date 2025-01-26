package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.broker.IClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorSensor;
import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.BrokerMessageHandler;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HeladeraService implements WithSimplePersistenceUnit {

  private final HeladeraRepository heladeraRepository;
  private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

  private final IClienteMqtt clienteMqtt;
  private final Set<SuscriptorSensor> suscriptores;

  public HeladeraService(HeladeraRepository heladeraRepository,
                         HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                         IClienteMqtt clienteMqtt) {
    this.heladeraRepository = heladeraRepository;
    this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;

    this.clienteMqtt = clienteMqtt;
    this.suscriptores = new HashSet<>();
  }

  public List<Heladera> buscarTodas() {
    return this.heladeraRepository.buscarTodos();
  }

  public Optional<Heladera> buscarPorId(String id) {
    if (id == null || id.isEmpty())
      throw new IllegalArgumentException("El ID nueva la heladera no puede ser null o vacío");

    return this.heladeraRepository.buscarPorId(id);
  }

  public Optional<Heladera> buscarPorNombre(String nombre) {
    return this.heladeraRepository.buscarPorNombre(nombre);
  }

  public List<Heladera> buscarPorBarrio(Barrio barrio) {
    return this.heladeraRepository.buscarPorBarrio(barrio);
  }

  public void guardarHeladera(Heladera heladera) {
    // TODO - validaciones??
    withTransaction(() -> {
      this.heladeraRepository.guardar(heladera);
    });
  }

  public void actualizarHeladera(Heladera heladeraActualizada) {
    withTransaction(() -> {
      this.heladeraRepository.actualizar(heladeraActualizada);
    });
  }

  public void eliminarHeladera(Heladera heladera) {
    this.heladeraRepository.eliminar(heladera);
  }

  public boolean puedeConfigurar(Colaborador colaborador, Heladera heladera) {
    List<HacerseCargoHeladera> encargos = hacerseCargoHeladeraRepository.buscarPorColaborador(colaborador);
    return encargos.stream().anyMatch(encargo -> encargo.getHeladeraACargo().equals(heladera));
  }

  public void suscibirPara(Heladera heladera) {
    if (!estaEnElSet(heladera)) {
      suscriptores.add(new SuscriptorSensor(ServiceLocator.instanceOf(BrokerMessageHandler.class), clienteMqtt, heladera.getBrokerTopic(), heladera.getId()));
    }

    suscriptores.stream()
        .filter(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()))
        .forEach(SuscriptorSensor::suscribir);
  }

  public void desuscribirPara(Heladera heladera) {
    suscriptores.stream()
        .filter(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()))
        .forEach(SuscriptorSensor::desuscribir);

    suscriptores.removeIf(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()));
  }

  public void iniciarSuscripciones() {
    buscarTodas()
        .stream().filter(Heladera::estaActiva)
        .forEach(this::suscibirPara);
  }

  private boolean estaEnElSet(Heladera heladera) {
    return suscriptores.stream()
        .anyMatch(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()));
  }
}

