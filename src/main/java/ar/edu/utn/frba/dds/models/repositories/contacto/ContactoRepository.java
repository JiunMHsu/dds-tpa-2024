package ar.edu.utn.frba.dds.models.repositories.contacto;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

/**
 * Repositorio de Contacto.
 */
public class ContactoRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda un Contacto.
   *
   * @param contacto Id del contacto
   * @return Contacto
   */
  public void guardar(Contacto contacto) {
    entityManager().persist(contacto);
  }

  /**
   * Guarda una lista de Contactos.
   *
   * @param contactos Lista de contactos
   */
  public void guardar(List<Contacto> contactos) {
    for (Contacto contacto : contactos) {
      entityManager().persist(contacto);
    }
  }
}
