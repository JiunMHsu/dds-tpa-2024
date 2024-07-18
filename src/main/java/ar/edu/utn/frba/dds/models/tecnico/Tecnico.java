package ar.edu.utn.frba.dds.models.tecnico;

import ar.edu.utn.frba.dds.mensajeria.INotificador;
import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.mensajeria.NotificadorFactory;
import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tecnico {

  private String nombre;
  private String apellido;
  private Documento documento;
  private Long cuit;
  private Contacto contacto;
  private MedioDeNotificacion medioDeNotificacion;
  private Area areaDeCobertura;

  public static Tecnico with(String nombre,
                             String apellido,
                             Documento documento,
                             Long cuit,
                             Contacto contacto,
                             MedioDeNotificacion medioDeNotificacion,
                             Area areaDeCobertura) {
    return Tecnico
        .builder()
        .nombre(nombre)
        .apellido(apellido)
        .documento(documento)
        .cuit(cuit)
        .contacto(contacto)
        .medioDeNotificacion(medioDeNotificacion)
        .areaDeCobertura(areaDeCobertura)
        .build();
  }

  public static Tecnico with(String nombre,
                             Contacto contacto,
                             MedioDeNotificacion medioDeNotificacion,
                             Area areaDeCobertura) {
    return Tecnico
        .builder()
        .nombre(nombre)
        .contacto(contacto)
        .medioDeNotificacion(medioDeNotificacion)
        .areaDeCobertura(areaDeCobertura)
        .build();
  }

  public static Tecnico with(String nombre,
                             Contacto contacto,
                             Area areaDeCobertura) {
    return Tecnico
        .builder()
        .nombre(nombre)
        .contacto(contacto)
        .areaDeCobertura(areaDeCobertura)
        .build();
  }

  private static String generadorMensajeTecnico(Incidente incidente) {
    return "Incidente reportado: " + incidente.getTipo() +
        " en la heladera: " + incidente.getHeladera().getNombre() + // Verno pl si esta bn que sea el "el nombre de la heladera"
        " a las: " + incidente.getFechaHora();
  }

  public void notificarPorIncidente(Incidente incidente) {
    String mensaje = generadorMensajeTecnico(incidente);

    INotificador notificador = NotificadorFactory.of(this.getMedioDeNotificacion());
    notificador.enviarMensaje(mensaje, contacto);
  }
}
