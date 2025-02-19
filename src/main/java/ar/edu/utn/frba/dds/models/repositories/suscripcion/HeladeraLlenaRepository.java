package ar.edu.utn.frba.dds.models.repositories.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;

/**
 * Repositorio de Heladera Llena.
 */
public class HeladeraLlenaRepository extends SuscripcionRepository<SuscripcionHeladeraLlena> {

  public HeladeraLlenaRepository() {
    super(SuscripcionHeladeraLlena.class);
  }

}
