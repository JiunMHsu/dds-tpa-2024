package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import com.aspose.pdf.operators.Do;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DonacionDineroRepository implements WithSimplePersistenceUnit{
  public void agregar(DonacionDinero colaboracion) {
    entityManager().persist(colaboracion);
  }

  public List<DonacionDinero> obtenerPorColaborador(Colaborador unColaborador) {
    return entityManager()
            .createQuery("from DonacionDinero c where c.colaborador =: id_colaborador", DonacionDinero.class)
            .setParameter("id_colaborador", unColaborador.getId())
            .getResultList();
  }

  public List<DonacionDinero> obtenerPorColaboradorAPartirDe(Colaborador unColaborador,
                                                                    LocalDateTime fecha) {
    return entityManager()
            .createQuery("from DonacionDinero d where d.colaborador = :id_colaborador and d.fechaHora >= :fecha", DonacionDinero.class)
            .setParameter("id_colaborador", unColaborador.getId())
            .setParameter("fecha", fecha)
            .getResultList();  }

}
