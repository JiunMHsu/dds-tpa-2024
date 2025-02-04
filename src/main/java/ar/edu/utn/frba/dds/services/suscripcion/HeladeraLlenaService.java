package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.HeladeraLlenaRepository;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HeladeraLlenaService implements WithSimplePersistenceUnit {

  private final HeladeraLlenaRepository heladeraLlenaRepositoy;
  private final IColaboradorRepository colaboradorRepository;
  private final MensajeriaService mensajeriaService;

  public HeladeraLlenaService(HeladeraLlenaRepository heladeraLlenaRepositoy,
                              ColaboradorRepository colaboradorRepository,
                              MensajeriaService mensajeriaService) {
    this.heladeraLlenaRepositoy = heladeraLlenaRepositoy;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
  }

  public void registrar(Colaborador colaborador, Heladera heladera, Integer espacioRestante,
                        MedioDeNotificacion medioDeNotificacion, String infoContacto)
      throws SuscripcionHeladeraLlenaException {

    if (colaborador.getContactos().isEmpty()) {
      List<Contacto> contactos = new ArrayList<>(Arrays.asList(Contacto.vacio()));
      colaborador.setContactos(contactos);
    }

    boolean contactoActualizado = false;

    if (colaborador.getContacto(medioDeNotificacion).isEmpty()) {
      colaborador.agregarContacto(Contacto.con(medioDeNotificacion, infoContacto));
      contactoActualizado = true;
    }

    if (espacioRestante < 0 || espacioRestante > heladera.getCapacidad())
      throw new SuscripcionHeladeraLlenaException("El espacio restante debe ser mayor o igual a "
          + "0 y menor a la capacidad máxima nueva la heladera");

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

  /**
   * Obtener las suscripciones de una heladera casi llena con n viandas.
   *
   * @param heladera heladera
   */
  public List<SuscripcionHeladeraLlena> obtenerPorHeladera(Heladera heladera) {
    return heladeraLlenaRepositoy.obtenerPorHeladera(heladera);
  }

  // TODO: Revisar
  /**
   * Notificar colaboradores suscriptos por heladera casi llena.
   *
   * @param suscripcion suscripcion
   */
  public void notificacionHeladeraLlena(SuscripcionHeladeraLlena suscripcion) {
    String asunto = "Heladera casi llena";
    String cuerpo = String.format(
        "Estimado/a %s,\n\n"
            + "La %s está a punto de llenarse, queda solo espacio para %d viandas más. "
            + "Por favor, redistribuir algunas viandas a otras heladeras.\n\n"
            + "Gracias por su colaboración.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        suscripcion.getUmbralEspacio()
    );

    try {
      Optional<Contacto> contacto = suscripcion.getColaborador()
          .getContacto(suscripcion.getMedioDeNotificacion());
      if (contacto.isPresent()) {
        Mensaje mensaje = Mensaje.con(
            contacto.get(),
            asunto,
            cuerpo);
        mensajeriaService.enviarMensaje(mensaje);
      } else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar "
            + "el mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
