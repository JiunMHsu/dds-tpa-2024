package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;

/**
 * Repositorio de donaciones de dinero.
 */
public class DonacionDineroRepository extends ColaboracionRepository<DonacionDinero> {

  public DonacionDineroRepository() {
    super(DonacionDinero.class);
  }

  // TODO: Métodos de Donación periodica
}
