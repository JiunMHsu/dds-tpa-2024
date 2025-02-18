package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.dtos.suscripcion.CreateSuscripcionHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FaltaViandaRepository;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio para manejar las suscripciones y notificaciones relacionadas con la falta de viandas.
 */
public class FaltaViandaService implements WithSimplePersistenceUnit {

  private final FaltaViandaRepository faltaViandaRepository;
  private final IColaboradorRepository colaboradorRepository;
  private final MensajeriaService mensajeriaService;
  private final ContactoRepository contactoRepository;

  /**
   * Constructor para inicializar los repositorios y servicios necesarios.
   *
   * @param faltaViandaRepository    El repositorio de falta de viandas.
   * @param colaboradorRepository    El repositorio de colaboradores.
   * @param mensajeriaService        El servicio de mensajería.
   * @param contactoRepository       El repositorio de contactos.
   */
  public FaltaViandaService(FaltaViandaRepository faltaViandaRepository,
                            ColaboradorRepository colaboradorRepository,
                            MensajeriaService mensajeriaService,
                            ContactoRepository contactoRepository) {
    this.faltaViandaRepository = faltaViandaRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
    this.contactoRepository = contactoRepository;
  }

  /**
   * Registrar una suscripción a una falla de heladera.
   *
   * @param suscripcion suscripción a falta viandas de heladera
   */
  public void registrar(CreateSuscripcionHeladeraDTO suscripcion)
      throws SuscripcionFaltaViandaException {

    boolean contactoNuevoNoExiste = false;
    boolean actualizarContacto = false;

    Contacto nuevoContacto = Contacto.con(suscripcion.getMedioDeNotificacion(),
        suscripcion.getInfoContacto());

    if (!suscripcion.getColaborador().contactoDuplicado(nuevoContacto)) {
      if (suscripcion.getColaborador()
          .medioContactoYaExiste(nuevoContacto.getMedioDeNotificacion())) {
        actualizarContacto = true;
      } else {
        contactoNuevoNoExiste = true;
      }
      suscripcion.getColaborador().agregarContacto(nuevoContacto);
    }

    if (suscripcion.getViandasRestantes() <= 0 || suscripcion.getViandasRestantes()
        > suscripcion.getHeladera().getCapacidad()) {
      throw new SuscripcionFaltaViandaException("La cantidad de viandas restantes debe ser mayor "
          + "a 0 y menor o igual a la capacidad máxima de la heladera");
    }

    SuscripcionFaltaVianda nuevaSuscripcion = SuscripcionFaltaVianda.de(
        suscripcion.getColaborador(),
        suscripcion.getHeladera(),
        suscripcion.getMedioDeNotificacion(),
        suscripcion.getViandasRestantes());

    beginTransaction();
    if (contactoNuevoNoExiste) {
      contactoRepository.guardar(nuevoContacto);
      colaboradorRepository.actualizar(suscripcion.getColaborador());
    } else if (actualizarContacto) {
      colaboradorRepository.actualizar(suscripcion.getColaborador());
    }
    faltaViandaRepository.guardar(nuevaSuscripcion);
    commitTransaction();
  }

  /**
   * Notificar a colaboradores por falta de viandas.
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

    Contacto contacto = null;
    try {
      contacto = suscripcion.getColaborador()
          .getContacto(suscripcion.getMedioDeNotificacion()).orElseThrow();
    } catch (NoSuchElementException e) {
      //noinspection OptionalGetWithoutIsPresent
      contacto = suscripcion.getColaborador().getContacto(MedioDeNotificacion.EMAIL).get();
    } finally {
      Mensaje mensaje = Mensaje.con(
          contacto,
          asunto,
          cuerpo);
      mensajeriaService.enviarMensaje(mensaje);
    }
  }

  /**
   * Chequea las suscripciones y notifica en caso de ser necesario.
   *
   * @param heladera heladera
   */
  public void manejarFaltaVianda(Heladera heladera) {
    this.faltaViandaRepository.obtenerPorHeladera(heladera).parallelStream()
        .filter(suscripcion -> heladera.getViandas() <= suscripcion.getUmbralViandas())
        .forEach(this::notificacionFaltaVianda);
  }
}
