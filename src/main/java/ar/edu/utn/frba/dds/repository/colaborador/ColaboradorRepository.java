package ar.edu.utn.frba.dds.repository.colaborador;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class ColaboradorRepository implements WithSimplePersistenceUnit {

    public void guardar(Colaborador colaborador) {
        entityManager().persist(colaborador);
    }

    public void actualizar(Colaborador colaborador) {
        withTransaction(() -> entityManager().merge(colaborador));
    }

    public void eliminar(Colaborador colaborador) {
        withTransaction(() -> {
            colaborador.setAlta(false);
            entityManager().merge(colaborador);
        });
    }

    public Optional<Colaborador> buscarPorEmail(String email) {
        return Optional.ofNullable(
                entityManager()
                        .createQuery("from Colaborador c where c.usuario.email = :email", Colaborador.class)
                        .setParameter("email", email)
                        .getSingleResult()
        );
    }
}
