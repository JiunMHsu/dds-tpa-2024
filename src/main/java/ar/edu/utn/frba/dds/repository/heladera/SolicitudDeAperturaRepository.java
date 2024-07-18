package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.heladera.SolicitudDeApertura;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDeAperturaRepository {
  private static final List<SolicitudDeApertura> db = new ArrayList<>();

  public static void agregar(SolicitudDeApertura solicitud) {
    db.add(0, solicitud);
  }

  /**
   * Retorna la última solicitud realizada luego de 'fechaHora'
   */
  public static List<SolicitudDeApertura> obtenerPorTarjeta(String codigoTarjeta, LocalDateTime fechaHora) {
    return db.stream()
        .filter(solicitud -> solicitud.getTarjeta().getCodigo().equals(codigoTarjeta))
        .filter(solicitud -> solicitud.getFechaHora().isAfter(fechaHora))
        .toList();
  }

  public static List<SolicitudDeApertura> obtenerPorTarjeta(String codigoTarjeta) {
    return db.stream()
        .filter(solicitud -> solicitud.getTarjeta().getCodigo().equals(codigoTarjeta))
        .toList();
  }
}
