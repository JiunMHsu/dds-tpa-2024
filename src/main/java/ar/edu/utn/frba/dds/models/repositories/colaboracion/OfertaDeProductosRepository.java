package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OfertaDeProductosRepository extends ColaboracionRepository<OfertaDeProductos> {

    public OfertaDeProductosRepository() {
        super(OfertaDeProductos.class);
    }

    public void eliminar(OfertaDeProductos oferta) {
        withTransaction(() -> {
            oferta.setAlta(false);
            entityManager().merge(oferta);
        });
    }
    public List<OfertaDeProductos> buscarTodos() {
        return entityManager().createQuery("SELECT o FROM OfertaDeProductos o WHERE o.alta = true", OfertaDeProductos.class).getResultList();
    }

}
