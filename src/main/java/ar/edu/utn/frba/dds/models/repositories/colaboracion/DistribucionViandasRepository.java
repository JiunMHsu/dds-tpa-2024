package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;


public class DistribucionViandasRepository extends ColaboracionRepository<DistribucionViandas> {

    public DistribucionViandasRepository() {
        super(DistribucionViandas.class);
    }

    @Override
    public void guardar(DistribucionViandas distribucionViandas) {
        withTransaction(() -> entityManager().persist(distribucionViandas));
    }
}
