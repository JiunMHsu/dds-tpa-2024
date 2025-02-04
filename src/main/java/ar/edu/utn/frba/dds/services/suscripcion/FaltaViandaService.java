package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FaltaViandaRepository;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FaltaViandaService implements WithSimplePersistenceUnit {

  private final FaltaViandaRepository faltaViandaRepository;
  private final IColaboradorRepository colaboradorRepository;

  private final MensajeriaService mensajeriaService;

  public FaltaViandaService(FaltaViandaRepository faltaViandaRepository,
                            ColaboradorRepository colaboradorRepository,
                            MensajeriaService mensajeriaService) {
    this.faltaViandaRepository = faltaViandaRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
  }

  public void registrar(Colaborador colaborador, Heladera heladera, Integer viandasRestantes,
                        MedioDeNotificacion medioDeNotificacion, String infoContacto)
      throws SuscripcionFaltaViandaException {

    if (colaborador.getContactos().isEmpty()) {
      List<Contacto> contactos = new ArrayList<>(Arrays.asList(Contacto.vacio()));
      colaborador.setContactos(contactos);
    }

    boolean contactoActualizado = false;

    if (colaborador.getContacto(medioDeNotificacion).isEmpty()) {
      colaborador.agregarContacto(Contacto.con(medioDeNotificacion, infoContacto));
      contactoActualizado = true;
    }

    if (viandasRestantes <= 0 || viandasRestantes > heladera.getCapacidad()) {
      throw new SuscripcionFaltaViandaException("La cantidad de viandas restantes debe ser mayor "
          + "a 0 y menor o igual a la capacidad máxima de la heladera");
    }

    SuscripcionFaltaVianda nuevaSuscripcion = SuscripcionFaltaVianda.de(
        colaborador,
        heladera,
        medioDeNotificacion,
        viandasRestantes);

    if (contactoActualizado) {
      beginTransaction();
      colaboradorRepository.actualizar(colaborador);
      faltaViandaRepository.guardar(nuevaSuscripcion);
      commitTransaction();
    } else {
      beginTransaction();
      faltaViandaRepository.guardar(nuevaSuscripcion);
      commitTransaction();
    }
  }

  /**
   * Obtener las suscripciones de falta de n viandas por una heladera.
   *
   * @param heladera heladera
   */
  public List<SuscripcionFaltaVianda> obtenerPorHeladera(Heladera heladera) {
    return faltaViandaRepository.obtenerPorHeladera(heladera);
  }

  /**
   * Notificar a colaboradores por falta de viandas
   *
   * @param suscripcion suscripcion
   */
  public void notificacionFaltaVianda(SuscripcionFaltaVianda suscripcion) {
    String asunto = "Heladera por baja disponibilidad de viandas";
    String cuerpo = String.format(
        """
            Estimado/a %s,

            La %s tiene solo %d viandas restantes. Por favor, lleve más viandas para reabastecerla.

            Gracias por su colaboración.""",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        suscripcion.getUmbralViandas()
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
        System.out
            .println("Medio de contacto solicitado no disponible. No se puede enviar el mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
