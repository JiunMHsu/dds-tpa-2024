package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;

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
}
