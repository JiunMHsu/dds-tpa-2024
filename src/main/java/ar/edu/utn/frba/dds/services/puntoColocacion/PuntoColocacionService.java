package ar.edu.utn.frba.dds.services.puntoColocacion;

import ar.edu.utn.frba.dds.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion.IPuntoDeColocacionAdapter;
import java.util.List;

/**
 * Servicio de puntos de colocación.
 */
public class PuntoColocacionService {

  private final IPuntoDeColocacionAdapter puntoDeColocacionAdapter;

  /**
   * Constructor.
   *
   * @param puntoDeColocacionAdapter Adaptador de puntos de colocación.
   */
  public PuntoColocacionService(IPuntoDeColocacionAdapter puntoDeColocacionAdapter) {
    this.puntoDeColocacionAdapter = puntoDeColocacionAdapter;
  }

  /**
   * Obtiene los puntos de colocación ideales.
   *
   * @param latitud  Latitud.
   * @param longitud Longitud.
   * @param radio    Radio.
   * @return Lista DTOs de ubicación.
   */
  public List<UbicacionDTO> obtenerPuntosDeColocacion(double latitud,
                                                      double longitud,
                                                      double radio) {
    return puntoDeColocacionAdapter
        .solicitarRecomendacion(latitud, longitud, radio)
        .stream()
        .map(UbicacionDTO::fromUbicacion)
        .toList();
  }
}
