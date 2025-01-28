package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;

/**
 * Repositorio de ofertas de productos.
 */
public class OfertaDeProductosRepository extends ColaboracionRepository<OfertaDeProductos> {

  public OfertaDeProductosRepository() {
    super(OfertaDeProductos.class);
  }

  /**
   * Elimina una oferta de productos.
   *
   * @param oferta la oferta a eliminar
   */
  public void eliminar(OfertaDeProductos oferta) {
    withTransaction(() -> {
      oferta.setAlta(false);
      entityManager().merge(oferta);
    });
  }
}
