package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OfertaDeProductosRepository extends ColaboracionRepository<OfertaDeProductos> {

    public OfertaDeProductosRepository() {
        super(OfertaDeProductos.class);
    }

    public Optional<OfertaDeProductos> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(OfertaDeProductos.class, uuid))
                    .filter(OfertaDeProductos::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    public void eliminar(OfertaDeProductos oferta) {
        withTransaction(() -> {
            oferta.setAlta(false);
            entityManager().merge(oferta);
        });
    }

}
