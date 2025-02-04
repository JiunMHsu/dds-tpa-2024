package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Servicio de falla de heladera.
 */
public class FallaHeladeraService implements WithSimplePersistenceUnit {

  private final FallaHeladeraRepository fallaHeladeraRepository;
  private final IColaboradorRepository colaboradorRepository;
  private final MensajeriaService mensajeriaService;
  private final TecnicoService tecnicoService;
  private final HeladeraService heladeraService;

  /**
   * Constructor.
   *
   * @param fallaHeladeraRepository Repositorio de falla de heladera
   * @param colaboradorRepository   Repositorio de colaborador
   * @param mensajeriaService       Servicio de mensajería
   * @param tecnicoService          Servicio de técnico
   * @param heladeraService         Servicio de heladera
   */
  public FallaHeladeraService(FallaHeladeraRepository fallaHeladeraRepository,
                              ColaboradorRepository colaboradorRepository,
                              MensajeriaService mensajeriaService,
                              TecnicoService tecnicoService,
                              HeladeraService heladeraService) {
    this.fallaHeladeraRepository = fallaHeladeraRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
    this.tecnicoService = tecnicoService;
    this.heladeraService = heladeraService;
  }

  /**
   * Registrar una suscripción a una falla de heladera.
   * TODO: Revisar
   *
   * @param colaborador         Colaborador
   * @param heladera            Heladera
   * @param medioDeNotificacion Medio de notificación
   * @param infoContacto        Información de contacto
   */
  public void registrar(Colaborador colaborador,
                        Heladera heladera,
                        MedioDeNotificacion medioDeNotificacion,
                        String infoContacto) {

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

  /**
   * Obtener todas las suscripciones a fallas según la heladera.
   *
   * @param heladera Heladera
   * @return Lista de suscripciones a fallas de heladera
   */
  public List<SuscripcionFallaHeladera> obtenerPorHeladera(Heladera heladera) {
    return fallaHeladeraRepository.obtenerPorHeladera(heladera);
  }

  /**
   * Notificar la falla de heladera a un técnico cercano.
   *
   * @param incidente Incidente
   */
  public void notificacionTecnico(Incidente incidente) {

    Tecnico tecnico = this.tecnicoMasCercano(incidente.getHeladera().getDireccion());

    String asunto = "Falla en la heladera";
    String cuerpo = String.format("Estimado/a %s,\n\n"
            + "La %s ha sufrido un desperfecto.\n"
            + "Ocurrio un/a %s\n\n"
            + "Por favor, dirigirse a la heladera situada en: %s lo antes posible. \n\n"
            + "Gracias por su rápida acción.",
        tecnico.getNombre(),
        incidente.getHeladera().getNombre(),
        incidente.getTipo().getDescription(),
        incidente.getHeladera().getDireccion().toString()
    );

    try {
      if (tecnico.getContacto() != null) {
        Mensaje mensaje = Mensaje.con(
            tecnico.getContacto(),
            asunto,
            cuerpo);
        mensajeriaService.enviarMensaje(mensaje);
      } else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el "
            + "mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Notificar la falla de heladera a un colaborador suscrito.
   * TODO: las sugerencias se instancian y se registran, no son simples mensajes
   * TODO: Revisar envío de mensajes
   *
   * @param suscripcion Suscripción a la falla de heladera
   * @param incidente   Incidente
   */
  public void notificacionColaborador(SuscripcionFallaHeladera suscripcion, Incidente incidente) {

    String asunto = "Falla en la heladera";

    String sugerencias = this.heladerasRecomendadas(incidente.getHeladera())
        .stream()
        .map(Heladera::getNombre)
        .collect(Collectors.joining("\n"));

    String cuerpo = String.format("Estimado/a %s,\n\n"
            + "La %s ha sufrido un desperfecto.\n"
            + "Ocurrio un/a %s\n\n"
            + "Por favor, traslade las viandas a las siguientes heladeras sugeridas:\n\n"
            + "%s\n"
            + "Gracias por su rápida acción.",
        incidente.getTipo().getDescription(),
        sugerencias
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
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el "
            + "mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Obtener el técnico más cercano a una dirección.
   *
   * @param direccion Dirección
   * @return Técnico
   */
  public Tecnico tecnicoMasCercano(Direccion direccion) {
    List<Tecnico> tecnicosCercanos = tecnicoService.obtenerPorBarrio(direccion.getBarrio());
    Random random = new Random();
    int indiceAleatorio = random.nextInt(tecnicosCercanos.size());
    return tecnicosCercanos.get(indiceAleatorio);
  }

  /**
   * Obtener las heladeras activas más cercanas a una heladera.
   *
   * @param heladera Heladera
   * @return Lista de heladeras activas más cercanas
   */
  public List<Heladera> heladerasActivasMasCercanas(Heladera heladera) {
    return heladeraService.buscarPorBarrio(heladera.getDireccion().getBarrio())
        .stream()
        .filter(Heladera::estaActiva)
        .toList();
  }

  public List<Heladera> heladerasRecomendadas(Heladera heladera) {
    List<Heladera> listaHeladerasActivasMasCercanasConEspacio =
        this.heladerasActivasMasCercanas(heladera).stream()
        .filter(heladera1 -> !heladera1.estaLlena())
        .toList();
    List<Heladera> heladerasSeleccionadas = new ArrayList<>();
    Integer cantViandasATransportar = heladera.getViandas();
    for (int i = 0; cantViandasATransportar > 0; i++) {
      Heladera heladeraX = listaHeladerasActivasMasCercanasConEspacio.get(i);
      heladerasSeleccionadas.add(heladeraX);
      cantViandasATransportar -= heladeraX.espacioRestante();
    }
    return heladerasSeleccionadas;
  }
}