package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
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

public class FallaHeladeraService implements WithSimplePersistenceUnit {

  private final FallaHeladeraRepository fallaHeladeraRepository;
  private final IColaboradorRepository colaboradorRepository;
  private final MensajeriaService mensajeriaService;
  private final TecnicoService tecnicoService;
  private final HeladeraService heladeraService;

  public FallaHeladeraService(FallaHeladeraRepository fallaHeladeraRepository,
                              ColaboradorRepository colaboradorRepository, MensajeriaService mensajeriaService, TecnicoService tecnicoService, HeladeraService heladeraService) {
    this.fallaHeladeraRepository = fallaHeladeraRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.mensajeriaService = mensajeriaService;
    this.tecnicoService = tecnicoService;
    this.heladeraService = heladeraService;
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

  public void notificacionFallaHeladera(SuscripcionFallaHeladera suscripcion, String falla) {
    this.notificacionColaboradorFallaHeladera(suscripcion, falla);
    this.notificacionTecnicoFallaHeladera(suscripcion.getHeladera(), falla);
  }

  public void notificacionTecnicoFallaHeladera(Heladera heladera, String falla) {
    Tecnico tecnico = this.tecnicoMasCercano(heladera.getDireccion());

    String asunto = "Falla en la heladera";
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s ha sufrido un desperfecto.\n" +
            "Ocurrio un/a %s\n\n" +
            "Por favor, dirigirse a la heladera situada en: %s lo antes posible. \n\n" +
            "Gracias nueva su rápida acción.",
        tecnico.getNombre(),
        heladera.getNombre(),
        falla,
        heladera.getDireccion().obtenerDireccion()
    );

    try {
      if (tecnico.getContacto() != null) {
        Mensaje mensaje = Mensaje.con(
            tecnico.getContacto(),
            asunto,
            cuerpo);
        mensajeriaService.enviarMensaje(mensaje);
      } else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void notificacionColaboradorFallaHeladera(SuscripcionFallaHeladera suscripcion, String falla) {
    String asunto = "Falla en la heladera";
    String sugerencias = this.heladerasActivasMasCercanas(suscripcion.getHeladera())
        .stream()
        .map(Heladera::getNombre)
        .collect(Collectors.joining("\n"));
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s ha sufrido un desperfecto.\n" +
            "Ocurrio un/a %s\n\n" +
            "Por favor, traslade las viandas a las siguientes heladeras sugeridas:\n\n" +
            "%s\n" +
            "Gracias nueva su rápida acción.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        falla,
        sugerencias
    );

    try {
      Optional<Contacto> contacto = suscripcion.getColaborador().getContacto(suscripcion.getMedioDeNotificacion());
      if (contacto.isPresent()) {
        Mensaje mensaje = Mensaje.con(
            contacto.get(),
            asunto,
            cuerpo);
        mensajeriaService.enviarMensaje(mensaje);
      } else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Tecnico tecnicoMasCercano(Direccion direccion) {
    List<Tecnico> tecnicosCercanos = tecnicoService.obtenerPorBarrio(direccion.getBarrio());
    Random random = new Random();
    int indiceAleatorio = random.nextInt(tecnicosCercanos.size());
    return tecnicosCercanos.get(indiceAleatorio);
  }

  public List<Heladera> heladerasActivasMasCercanas(Heladera heladera) {
    return heladeraService.buscarPorBarrio(heladera.getDireccion().getBarrio())
        .stream()
        .filter(Heladera::estaActiva)
        .toList();
  }

}