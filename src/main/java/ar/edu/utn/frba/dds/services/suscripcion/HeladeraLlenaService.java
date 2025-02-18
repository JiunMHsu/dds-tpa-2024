package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.dtos.suscripcion.CreateSuscripcionHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.HeladeraLlenaRepository;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio para manejar las suscripciones y notificaciones relacionadas con heladeras llenas.
 */
public class HeladeraLlenaService implements WithSimplePersistenceUnit {

  private final HeladeraLlenaRepository heladeraLlenaRepositoy;
  private final IColaboradorRepository colaboradorRepository;
  private final MensajeriaService mensajeriaService;
  private final ContactoRepository contactoRepository;

  /**
   * Constructor para inicializar los repositorios y servicios necesarios.
   *
   * @param heladeraLlenaRepositoy El repositorio de heladeras llenas.
   * @param colaboradorRepository  El repositorio de colaboradores.
   * @param mensajeriaService      El servicio de mensajería.
   * @param contactoRepository     El repositorio de contactos.
   */
  public HeladeraLlenaService(HeladeraLlenaRepository heladeraLlenaRepositoy,
                              ColaboradorRepository colaboradorRepository,
                              MensajeriaService mensajeriaService,
                              ContactoRepository contactoRepository) {
    this.heladeraLlenaRepositoy = heladeraLlenaRepositoy;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
    this.contactoRepository = contactoRepository;
  }

  /**
   * Registrar una suscripción a una falla de heladera.
   *
   * @param suscripcion suscripción a la falla de heladera
   */
  public void registrar(CreateSuscripcionHeladeraDTO suscripcion)
      throws SuscripcionHeladeraLlenaException {

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

    if (suscripcion.getEspacioRestante() < 0 || suscripcion.getEspacioRestante()
        > suscripcion.getHeladera().getCapacidad()) {
      throw new SuscripcionHeladeraLlenaException("El espacio restante debe ser mayor o igual a "
          + "0 y menor a la capacidad máxima nueva la heladera");
    }

    SuscripcionHeladeraLlena nuevaSuscripcion = SuscripcionHeladeraLlena.de(
        suscripcion.getColaborador(),
        suscripcion.getHeladera(),
        suscripcion.getMedioDeNotificacion(),
        suscripcion.getEspacioRestante());

    beginTransaction();
    if (contactoNuevoNoExiste) {
      contactoRepository.guardar(nuevoContacto);
      colaboradorRepository.actualizar(suscripcion.getColaborador());
    } else if (actualizarContacto) {
      colaboradorRepository.actualizar(suscripcion.getColaborador());
    }
    heladeraLlenaRepositoy.guardar(nuevaSuscripcion);
    commitTransaction();
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
  public void manejarHeladeraLlena(Heladera heladera) {
    this.obtenerPorHeladera(heladera).parallelStream()
        .filter(suscripcion -> heladera.getViandas() >= suscripcion.getUmbralEspacio())
        .forEach(this::notificacionHeladeraLlena);
  }
}
