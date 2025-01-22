package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.AperturaHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.services.heladera.AperturaHeladeraService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.heladera.RetiroDeViandaService;
import ar.edu.utn.frba.dds.services.heladera.SolicitudDeAperturaService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.mensajeria.MensajeriaService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;


public class BrokerMessageHandler implements IBrokerMessageHandler {

  private final HeladeraService heladeraService;
  private final IncidenteService incidenteService;
  private final FallaHeladeraService fallaHeladeraService;
  private final MensajeriaService mensajeriaService;
  private final SolicitudDeAperturaService solicitudDeAperturaService;
  private final TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;
  private final AperturaHeladeraService aperturaHeladeraService;
  private final RetiroDeViandaService retiroDeViandaService;

  public BrokerMessageHandler(HeladeraService heladeraService,
                              IncidenteService incidenteService,
                              FallaHeladeraService fallaHeladeraService,
                              MensajeriaService mensajeriaService,
                              SolicitudDeAperturaService solicitudDeAperturaService,
                              TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService,
                              AperturaHeladeraService aperturaHeladeraService,
                              RetiroDeViandaService retiroDeViandaService) {
    this.heladeraService = heladeraService;
    this.incidenteService = incidenteService;
    this.fallaHeladeraService = fallaHeladeraService;
    this.mensajeriaService = mensajeriaService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
    this.tarjetaPersonaVulnerableService = tarjetaPersonaVulnerableService;
    this.aperturaHeladeraService = aperturaHeladeraService;
    this.retiroDeViandaService = retiroDeViandaService;
  }

  @Override
  public void manejarTemperatura(double temperatura, UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString())
        .orElseThrow(ResourceNotFoundException::new);

    if (!heladera.admiteTemperatura(temperatura)) {
      Incidente incidente = Incidente.fallaTemperatura(heladera, LocalDateTime.now());
      this.incidenteService.registrarIncidente(incidente);

      // TODO: testear, las suscripciones deberían ser filtradas por tópico (usar una mensajería segura para el test)
       List<SuscripcionFallaHeladera> suscripcionesAHeladera = this.fallaHeladeraService.obtenerPorHeladera(heladera);
       suscripcionesAHeladera.forEach(suscripcion->this.notificacionFallaHeladera(suscripcion, "falla def temperatura"));
    } else {
      heladera.setUltimaTemperatura(temperatura);
      this.heladeraService.actualizarHeladera(heladera);
    }
  }

  @Override
  public void manejarFraude(UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString())
        .orElseThrow(ResourceNotFoundException::new);

    Incidente incidente = Incidente.fraude(heladera, LocalDateTime.now());
    this.incidenteService.registrarIncidente(incidente);

    // TODO: testear, las suscripciones deberían ser filtradas por tópico (usar una mensajería segura para el test)
     List<SuscripcionFallaHeladera> suscripcionesAHeladera = this.fallaHeladeraService.obtenerPorHeladera(heladera);
     suscripcionesAHeladera.forEach(suscripcion->this.notificacionFallaHeladera(suscripcion, "fraude"));
  }

  @Override
  public void manejarFallaConexion(UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString())
        .orElseThrow(ResourceNotFoundException::new);

    Incidente incidente = Incidente.fallaConexion(heladera, LocalDateTime.now());
    this.incidenteService.registrarIncidente(incidente);

    // TODO: testear, las suscripciones deberían ser filtradas por tópico (usar una mensajería segura para el test)
     List<SuscripcionFallaHeladera> suscripcionesAHeladera = this.fallaHeladeraService.obtenerPorHeladera(heladera);
     suscripcionesAHeladera.forEach(suscripcion->this.notificacionFallaHeladera(suscripcion, "falla de conexion"));
  }

  @Override
  public void manejarSolicitudDeApertura(String codigoTarjeta, UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString())
        .orElseThrow(ResourceNotFoundException::new);

    Optional<SolicitudDeApertura> solicitudDeApertura = solicitudDeAperturaService.buscarPorTarjetaHeladeraEnLasUltimas(codigoTarjeta, heladera)
        .stream()
        .filter(solicitud -> this.aperturaHeladeraService.buscarPorSolicitud(solicitud).isEmpty()) //solicitudes que no tienen aperturas
        .min(Comparator.comparing(SolicitudDeApertura::getFechaHora)); // obtengo la más vieja

    if (solicitudDeApertura.isPresent()) {
      AperturaHeladera aperturaHeladera = AperturaHeladera.por(solicitudDeApertura.get().getTarjeta(), solicitudDeApertura.get().getHeladera(), LocalDateTime.now(), solicitudDeApertura.get());
      this.aperturaHeladeraService.guardar(aperturaHeladera);
      System.out.println("no permito acceso");

      //TODO debería registrar movimientos
    } else {
      Optional<TarjetaPersonaVulnerable> tarjetaPersonaVulnerable = tarjetaPersonaVulnerableService.buscarTarjetaPorCodigo(codigoTarjeta);

      if (tarjetaPersonaVulnerable.isPresent()) {
        RetiroDeVianda retiroDeVianda = RetiroDeVianda.por(tarjetaPersonaVulnerable.get(), heladera, LocalDateTime.now());
        this.retiroDeViandaService.guardar(retiroDeVianda);
        System.out.println("no permito acceso");
      } else {
        //TODO no se le da acceso para que abra la heladera
        System.out.println("no permito acceso");
      }
    }
  }

  public void notificacionFallaHeladera(SuscripcionFallaHeladera suscripcion, String falla) {
    String asunto = "Falla en la heladera";
    String sugerencias = this.heladerasActivasMasCercanas(suscripcion.getHeladera())
            .stream()
            .map(heladera -> heladera.getNombre())
            .collect(Collectors.joining("\n"));
    String cuerpo = String.format(
            "Estimado/a %s,\n\n" +
                    "La %s ha sufrido un desperfecto.\n" +
                    "Ocurrio un/a %s\n\n" +
                    "Por favor, traslade las viandas a las siguientes heladeras sugeridas:\n\n" +
                    "%s\n" +
                    "Gracias por su rápida acción.",
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
      }
      else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el mensaje.");
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void notificacionFaltaVianda(SuscripcionFaltaVianda suscripcion) {
    String asunto = "Heladera con baja disponibilidad de viandas";
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s tiene solo %d viandas restantes. Por favor, lleve más viandas para reabastecerla.\n\n" +
            "Gracias por su colaboración.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        suscripcion.getViandasRestantes()
    );

    try {
      Optional<Contacto> contacto = suscripcion.getColaborador().getContacto(suscripcion.getMedioDeNotificacion());
      if (contacto.isPresent()) {
        Mensaje mensaje = Mensaje.con(
                contacto.get(),
                asunto,
                cuerpo);
        mensajeriaService.enviarMensaje(mensaje);
      }
      else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el mensaje.");
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void notificacionHeladeraLlena(SuscripcionHeladeraLlena suscripcion) {
    String asunto = "Heladera casi llena";
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s está a punto de llenarse, con solo espacio para %d viandas más. " +
            "Por favor, redistribuir algunas viandas a otras heladeras.\n\n" +
            "Gracias por su colaboración.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        suscripcion.getEspacioRestante()
    );

    try {
      Optional<Contacto> contacto = suscripcion.getColaborador().getContacto(suscripcion.getMedioDeNotificacion());
      if (contacto.isPresent()) {
        Mensaje mensaje = Mensaje.con(
                contacto.get(),
                asunto,
                cuerpo);
        mensajeriaService.enviarMensaje(mensaje);
      }
      else {
        System.out.println("Medio de contacto solicitado no disponible. No se puede enviar el mensaje.");
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  //TODO medio raro que este aca pero...
  public List<Heladera> heladerasActivasMasCercanas(Heladera heladera) {
    return heladeraService.buscarPorBarrio(heladera.getDireccion().getBarrio())
            .stream()
            .filter(Heladera::estaActiva)
            .toList();
  }


}
