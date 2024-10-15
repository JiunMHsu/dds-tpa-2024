package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;

import java.util.Optional;
import java.util.UUID;

public class DonacionDineroRepository extends ColaboracionRepository<DonacionDinero> {

    public DonacionDineroRepository() {
        super(DonacionDinero.class);
    }

}
