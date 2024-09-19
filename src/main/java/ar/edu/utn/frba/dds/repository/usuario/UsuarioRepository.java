package ar.edu.utn.frba.dds.repository.usuario;

import ar.edu.utn.frba.dds.models.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class UsuarioRepository implements WithSimplePersistenceUnit {

    public void guardar(Usuario usuario) {
        entityManager().persist(usuario);
    }

    public void actualizar(Usuario usuario) {
        withTransaction(() -> {
            entityManager().merge(usuario);
        });
    }

    public void eliminar(Usuario usuario) {
        usuario.setAlta(false);
        entityManager().merge(usuario);
    }

    public Usuario obtenerPorEmail(String email) {
        return entityManager()
                .createQuery("from Usuario u where u.email =: email", Usuario.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
