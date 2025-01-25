package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FallaHeladeraService implements WithSimplePersistenceUnit {

  private final FallaHeladeraRepository fallaHeladeraRepository;
  private final IColaboradorRepository colaboradorRepository;

  public FallaHeladeraService(FallaHeladeraRepository fallaHeladeraRepository,
                              ColaboradorRepository colaboradorRepository) {
    this.fallaHeladeraRepository = fallaHeladeraRepository;
    this.colaboradorRepository = colaboradorRepository;
  }

  public void registrar(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, String infoContacto) {

    if (colaborador.getContactos().isEmpty()) {
      List<Contacto> contactos = new ArrayList<>(Arrays.asList(Contacto.vacio()));
      colaborador.setContactos(contactos);
    }

    boolean contactoActualizado = false;

    if (colaborador.getContacto(medioDeNotificacion).isEmpty()) {
      colaborador.agregarContacto(Contacto.con(medioDeNotificacion, infoContacto));
      contactoActualizado = true;
    }

    SuscripcionFallaHeladera nuevaSuscripcion = SuscripcionFallaHeladera.de(
        colaborador,
        heladera,
        medioDeNotificacion);

    if (contactoActualizado) {
      beginTransaction();
      colaboradorRepository.actualizar(colaborador);
      fallaHeladeraRepository.guardar(nuevaSuscripcion);
      commitTransaction();
    } else {
      beginTransaction();
      fallaHeladeraRepository.guardar(nuevaSuscripcion);
      commitTransaction();
    }
  }

  public List<SuscripcionFallaHeladera> obtenerPorHeladera(Heladera heladera) {
    return fallaHeladeraRepository.obtenerPorHeladera(heladera);
  }

}
