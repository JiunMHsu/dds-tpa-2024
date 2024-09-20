package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import java.time.LocalDateTime;
import java.util.List;

public class DonacionViandaRepository extends ColaboracionRepository<DonacionVianda> {

    public DonacionViandaRepository() {
        super(DonacionVianda.class);
    }

    public List<DonacionVianda> obtenerAPartirDe(LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from " + DonacionVianda.class.getName() + " d where  d.fechaHora >= :fecha_hora", DonacionVianda.class)
                .setParameter("fecha_hora", fechaHora)
                .getResultList();
    }

}
