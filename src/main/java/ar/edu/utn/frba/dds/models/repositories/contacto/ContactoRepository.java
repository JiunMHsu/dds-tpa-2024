package ar.edu.utn.frba.dds.models.repositories.contacto;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class ContactoRepository implements WithSimplePersistenceUnit {

  public void guardar(Contacto contacto) { entityManager().persist(contacto); }

  public void guardar(List<Contacto> contactos) {
    withTransaction(() -> {
      for (Contacto contacto : contactos) {
        entityManager().persist(contacto);
      }
    });
  }

  public void actualizar(Contacto contacto) { entityManager().merge(contacto); }

  public void eliminar(Contacto contacto) {
    withTransaction(() -> {
      contacto.setAlta(false);
      entityManager().merge(contacto);
    });
  }
}
