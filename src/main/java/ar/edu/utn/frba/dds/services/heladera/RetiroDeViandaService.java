package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.exceptions.AperturaDeniedException;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.RetiroDeViandaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

/**
 * Servicio de Retiro de Vianda.
 */
public class RetiroDeViandaService implements WithSimplePersistenceUnit {

  private final RetiroDeViandaRepository retiroDeViandaRepository;
  private final HeladeraRepository heladeraRepository;

  /**
   * Constructor para inicializar el servicio de retiro de viandas.
   *
   * @param retiroDeViandaRepository El repositorio de retiro de viandas.
   * @param heladeraRepository       El repositorio de heladeras.
   */
  public RetiroDeViandaService(RetiroDeViandaRepository retiroDeViandaRepository,
                               HeladeraRepository heladeraRepository) {
    this.retiroDeViandaRepository = retiroDeViandaRepository;
    this.heladeraRepository = heladeraRepository;
  }

  /**
   * Registra el retiro de una vianda por una persona vulnerable.
   *
   * @param tarjeta  Tarjeta de la persona que retira la vianda.
   * @param heladera Heladera de la que se retira la vianda.
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   */
  public void registrarRetiro(TarjetaPersonaVulnerable tarjeta, Heladera heladera)
      throws CantidadDeViandasException {
    if (!tarjeta.puedeUsarseEn(heladera)) {
      throw new AperturaDeniedException();
    }

    heladera.quitarViandas(1);
    RetiroDeVianda retiroDeVianda = RetiroDeVianda.por(tarjeta, heladera);

    beginTransaction();
    this.heladeraRepository.actualizar(heladera);
    this.retiroDeViandaRepository.guardar(retiroDeVianda);
    commitTransaction();
  }

}
