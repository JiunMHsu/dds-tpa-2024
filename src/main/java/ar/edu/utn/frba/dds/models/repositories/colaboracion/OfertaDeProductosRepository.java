package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import java.util.Optional;
import java.util.UUID;

public class OfertaDeProductosRepository extends ColaboracionRepository<OfertaDeProductos> {

    public OfertaDeProductosRepository() {
        super(OfertaDeProductos.class);
    }

    public Optional<OfertaDeProductos> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(OfertaDeProductos.class, uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
