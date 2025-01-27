package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.IHeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class DistribucionViandasService implements WithSimplePersistenceUnit {
  private final DistribucionViandasRepository distribucionViandasRepository;
  private final IHeladeraRepository heladeraRepository;

  public DistribucionViandasService(DistribucionViandasRepository distribucionViandasRepository,
                                    IHeladeraRepository heladeraRepository) {
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.heladeraRepository = heladeraRepository;
  }

  public void registrar(DistribucionViandas distribucionViandas) throws CantidadDeViandasException {

    Heladera heladeraOrigen = distribucionViandas.getOrigen();
    Heladera heladeraDestino = distribucionViandas.getDestino();
    Integer viandas = distribucionViandas.getViandas();

    heladeraOrigen.quitarViandas(viandas);
    heladeraDestino.agregarViandas(viandas);

    beginTransaction();
    this.heladeraRepository.actualizar(heladeraOrigen);
    this.heladeraRepository.actualizar(heladeraDestino);
    this.distribucionViandasRepository.guardar(distribucionViandas);

    commitTransaction();
  }

  public Optional<DistribucionViandas> buscarPorId(String id) {
    return distribucionViandasRepository.buscarPorId(id);
  }
}
