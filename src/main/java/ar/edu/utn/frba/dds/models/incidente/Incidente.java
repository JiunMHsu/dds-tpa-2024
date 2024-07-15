package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.mensajeria.NotificadorFactory;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.reportes.RegistroIncidente;
import ar.edu.utn.frba.dds.mensajeria.INotificador;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Incidente {

  private TipoIncidente tipo;
  private Heladera heladera;
  private LocalDateTime fechaHora;

  public static void reportar(TipoIncidente tipo, Heladera heladera, LocalDateTime fechaHora) {
    Incidente incidente = Incidente
        .builder()
        .tipo(tipo)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();

    heladera.setEstadoDeFalla();
    RegistroIncidente.registrarIncidente(incidente);

    // notificar técnico

    Tecnico tecnicoMasCercano = Incidente.tecnicoMasCercanoA(heladera);

    String mensaje = generadorMensajeTecnico(incidente, heladera);
    //INotificador notificador = NotificadorFactory.of(tecnicoMasCercano.getMedioDeNotificacion());
    //notificador.enviarMensaje(mensaje);
  }

  private static String generadorMensajeTecnico(Incidente incidente, Heladera heladera) {
    return "Incidente reportado: " + incidente.getTipo() +
            " en la heladera: " + heladera.getNombre() + // Verno pl si esta bn que sea el "el nombre de la heladera"
            " a las: " + incidente.getFechaHora();
  }

  public static Tecnico tecnicoMasCercanoA(Heladera heladera) {
    List<Tecnico> listaTecnicos = TecnicoRepository.obtenerTodos();
    return listaTecnicos.stream()
            .min(Comparator.comparingDouble(tecnico -> tecnico.getAreaDeCobertura().calcularDistanciaAUbicacion(heladera.getDireccion().getUbicacion())))
            .orElseThrow(() -> new RuntimeException("No se encontró ningún técnico."));
  }
}
