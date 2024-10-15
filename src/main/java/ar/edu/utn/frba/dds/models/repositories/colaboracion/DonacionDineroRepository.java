package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;

import java.util.Optional;
import java.util.UUID;

public class DonacionDineroRepository extends ColaboracionRepository<DonacionDinero> {

    public DonacionDineroRepository() {
        super(DonacionDinero.class);
    }

    public void eliminar(DonacionDinero donacionDinero) {
        withTransaction(() -> {
            donacionDinero.setAlta(false);
            entityManager().merge(donacionDinero);
        });
    }

    public Optional<DonacionDinero> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(DonacionDinero.class, uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
