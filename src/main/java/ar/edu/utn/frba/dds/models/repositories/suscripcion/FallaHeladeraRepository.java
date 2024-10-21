package ar.edu.utn.frba.dds.models.repositories.suscripcion;

import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;

public class FallaHeladeraRepository extends SuscripcionRepository<SuscripcionFallaHeladera> {

    public FallaHeladeraRepository() {
        super(SuscripcionFallaHeladera.class);
    }
}
