package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.dtos.suscripcion.CreateSuscripcionHeladeraDTO;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
  private final ContactoRepository contactoRepository;

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
                              HeladeraService heladeraService, ContactoRepository contactoRepository) {
    this.fallaHeladeraRepository = fallaHeladeraRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
    this.tecnicoService = tecnicoService;
    this.heladeraService = heladeraService;
    this.contactoRepository = contactoRepository;
  }

  /**
   * Registrar una suscripción a una falla de heladera.
   *
   * @param suscripcion suscripción a la falla de heladera
   */
  public void registrar(CreateSuscripcionHeladeraDTO suscripcion) {

    Contacto nuevoContacto = Contacto.con(suscripcion.getMedioDeNotificacion(),
        suscripcion.getInfoContacto());

    suscripcion.getColaborador().agregarContacto(nuevoContacto);

    SuscripcionFallaHeladera nuevaSuscripcion = SuscripcionFallaHeladera.de(
        suscripcion.getColaborador(),
        suscripcion.getHeladera(),
        suscripcion.getMedioDeNotificacion());

    beginTransaction();
    //TODO ver si agrego chequeo si es que agrego un contacto que ya estaba (no seria necesario actualizar)
    contactoRepository.guardar(nuevoContacto);
    colaboradorRepository.actualizar(suscripcion.getColaborador());
    fallaHeladeraRepository.guardar(nuevaSuscripcion);
    commitTransaction();
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
    String cuerpo = String.format("""
            Estimado/a %s,
                        
            La %s ha sufrido un desperfecto.
            Ocurrio un/a %s
                        
            Por favor, dirigirse a la heladera situada en: %s lo antes posible.\s
                        
            Gracias por su rápida acción.""",
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

    String cuerpo = String.format("""
            Estimado/a %s,
                        
            La %s ha sufrido un desperfecto.
            Ocurrio un/a %s
                        
            Por favor, traslade las viandas a las siguientes heladeras sugeridas:
                        
            %s
            Gracias por su rápida acción.""",
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
    List<Tecnico> tecnicosCercanos = tecnicoService.obtenerPorBarrio(direccion.getBarrio())
        .stream()
        .sorted(Comparator.comparingDouble(tecnico -> tecnico.getAreaDeCobertura()
            .distanciaA(direccion.getUbicacion())))
        .toList();
    return tecnicosCercanos.get(0);
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
        .sorted(Comparator.comparingDouble(heladera1 -> heladera1.getDireccion().getUbicacion()
            .distanciaA(heladera.getDireccion().getUbicacion())))
        .toList();
  }

  /**
   * Obtener las heladeras recomendadas segun su disponibilidad de espacio para viandas y cercania.
   *
   * @param heladera Heladera
   * @return Lista de heladeras recomendadas
   */
  public List<Heladera> heladerasRecomendadas(Heladera heladera) {
    List<Heladera> listaHeladerasActivasMasCercanasConEspacio =
        this.heladerasActivasMasCercanas(heladera).stream()
            .filter(heladera1 -> !heladera1.estaLlena())
            .toList();
    List<Heladera> heladerasSeleccionadas = new ArrayList<>();
    Integer cantViandasTransportar = heladera.getViandas();
    for (int i = 0; cantViandasTransportar > 0; i++) {
      Heladera heladeraX = listaHeladerasActivasMasCercanasConEspacio.get(i);
      heladerasSeleccionadas.add(heladeraX);
      cantViandasTransportar -= heladeraX.espacioRestante();
    }
    return heladerasSeleccionadas;
  }
}