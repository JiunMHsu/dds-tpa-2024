package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.List;

public class DonacionViandaRepository extends ColaboracionRepository<DonacionVianda>{

  public DonacionViandaRepository() {
    super(DonacionVianda.class);
  }
  public List<DonacionVianda> obtenerAPartirDe(LocalDate fecha) {
    return entityManager()
            .createQuery("from " + DonacionVianda.class.getName() + " d where  d.fechaHora >= :fecha", DonacionVianda.class)
            .setParameter("fecha", fecha)
            .getResultList();
  }

}
