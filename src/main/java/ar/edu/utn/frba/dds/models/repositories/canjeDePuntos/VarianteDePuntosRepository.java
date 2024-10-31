package ar.edu.utn.frba.dds.models.repositories.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteDePuntos;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class VarianteDePuntosRepository implements WithSimplePersistenceUnit {

    public void guardar(VarianteDePuntos varianteDePuntos) {
        entityManager().persist(varianteDePuntos);
    }

    public Optional<VarianteDePuntos> buscarPorId(String id) {
        try {
            Long idLong = Long.parseLong(id);
            return Optional.ofNullable(entityManager().find(VarianteDePuntos.class, idLong));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<VarianteDePuntos> buscarUltimo() {
        return Optional.ofNullable(entityManager()
                .createQuery("from VarianteDePuntos v order by v.fechaConfiguracion desc", VarianteDePuntos.class)
                .setMaxResults(1)
                .getSingleResult());
    }
}
