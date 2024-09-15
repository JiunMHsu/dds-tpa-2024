package ar.edu.utn.frba.dds.repository.colaboracion;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DistribucionViandasRepository implements WithSimplePersistenceUnit{

  public void agregar(DistribucionViandas colaboracion) {
    entityManager().persist(colaboracion);
  }

  public List<DistribucionViandas> obtenerPorColaborador(Colaborador unColaborador) {
    return entityManager()
            .createQuery("from DistribucionViandas c where c.colaborador =: id_colaborador", DistribucionViandas.class)
            .setParameter("id_colaborador", unColaborador.getId())
            .getResultList();
  }

  public List<DistribucionViandas> obtenerPorColaboradorAPartirDe(Colaborador unColaborador, LocalDateTime fecha) {
    return entityManager()
            .createQuery("from DistribucionViandas d where d.colaborador = :id_colaborador and d.fechaHora >= :fecha", DistribucionViandas.class)
            .setParameter("id_colaborador", unColaborador.getId())
            .setParameter("fecha", fecha)
            .getResultList();
  }


}
