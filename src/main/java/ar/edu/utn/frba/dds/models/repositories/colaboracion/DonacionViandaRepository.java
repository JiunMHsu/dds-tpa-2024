package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import java.util.List;

public class DonacionViandaRepository extends ColaboracionRepository<DonacionVianda> {

  public DonacionViandaRepository() {
    super(DonacionVianda.class);
  }

  public List<DonacionVianda> buscarNoEntregadas() {
    return entityManager()
        .createQuery("from DonacionVianda d where d.alta = :alta and d.esEntregada = :entregada", DonacionVianda.class)
        .setParameter("entregada", false)
        .setParameter("alta", true)
        .getResultList();
  }
}
