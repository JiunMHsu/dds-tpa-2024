package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;

public class DonacionDineroRepository extends ColaboracionRepository<DonacionDinero> {

    public DonacionDineroRepository() {
        super(DonacionDinero.class);
    }
}
