package ar.edu.utn.frba.dds.models.repositories.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;

/**
 * Repositorio de Falta de Vianda.
 */
public class FaltaViandaRepository extends SuscripcionRepository<SuscripcionFaltaVianda> {

  public FaltaViandaRepository() {
    super(SuscripcionFaltaVianda.class);
  }
}
