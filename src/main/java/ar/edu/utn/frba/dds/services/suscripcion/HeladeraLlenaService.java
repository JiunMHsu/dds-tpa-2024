package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.HeladeraLlenaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class HeladeraLlenaService implements WithSimplePersistenceUnit {

  private final HeladeraLlenaRepository heladeraLlenaRepositoy;
  private final IColaboradorRepository colaboradorRepository;


  public HeladeraLlenaService(HeladeraLlenaRepository heladeraLlenaRepositoy,
                              ColaboradorRepository colaboradorRepository) {
    this.heladeraLlenaRepositoy = heladeraLlenaRepositoy;
    this.colaboradorRepository = colaboradorRepository;
  }

  public void registrar(Colaborador colaborador, Heladera heladera, Integer espacioRestante, MedioDeNotificacion medioDeNotificacion, String infoContacto) throws SuscripcionHeladeraLlenaException {

    Contacto contacto = colaborador.getContacto();

    if (contacto == null) {
      contacto = Contacto.vacio();
      colaborador.setContacto(contacto);
    }

    boolean contactoActualizado = false;

    switch (medioDeNotificacion) {
      case WHATSAPP:
        if (contacto.getWhatsApp() == null) {
          contacto.setWhatsApp(infoContacto);
          contactoActualizado = true;
        }
        break;
      case TELEGRAM:
        if (contacto.getTelegram() == null) {
          contacto.setTelegram(infoContacto);
          contactoActualizado = true;
        }
        break;
      case EMAIL:
        if (contacto.getEmail() == null) {
          contacto.setEmail(infoContacto);
          contactoActualizado = true;
        }
        break;
    }

    if (espacioRestante < 0 || espacioRestante > heladera.getCapacidad())
      throw new SuscripcionHeladeraLlenaException("El espacio restante debe ser mayor o igual a 0 y menor a la capacidad máxima por la heladera");

    SuscripcionHeladeraLlena nuevaSuscripcion = SuscripcionHeladeraLlena.de(
        colaborador,
        heladera,
        medioDeNotificacion,
        espacioRestante);

    if (contactoActualizado) {
      beginTransaction();
      colaboradorRepository.actualizar(colaborador);
      heladeraLlenaRepositoy.guardar(nuevaSuscripcion);
      commitTransaction();
    } else {
      beginTransaction();
      heladeraLlenaRepositoy.guardar(nuevaSuscripcion);
      commitTransaction();
    }
  }

}
