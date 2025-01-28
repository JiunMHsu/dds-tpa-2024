package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjeta;

/**
 * Repositorio de colaboraciones de reparto de tarjetas.
 */
public class RepartoDeTarjetaRepository extends ColaboracionRepository<RepartoDeTarjeta> {

  public RepartoDeTarjetaRepository() {
    super(RepartoDeTarjeta.class);
  }
}
