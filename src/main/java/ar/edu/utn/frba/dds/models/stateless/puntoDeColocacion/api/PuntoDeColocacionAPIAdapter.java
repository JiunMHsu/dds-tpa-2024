package ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion.api;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion.IPuntoDeColocacionAdapter;
import java.util.List;

/**
 * Adapter concreto del API de recomendaciones de puntos de colocación.
 */
public class PuntoDeColocacionAPIAdapter implements IPuntoDeColocacionAdapter {

  private final PuntoDeColocacionAPI api;

  public PuntoDeColocacionAPIAdapter() {
    this.api = new PuntoDeColocacionAPI();
  }

  @Override
  public List<Ubicacion> solicitarRecomendacion(double latitud, double longitud, double radio) {
    String query = String.format("?latitud=%f&longitud=%f&radio=%f", latitud, longitud, radio);

    String jsonResponse = this.api.solicitarRecomendacion(query);

    // TODO: parsear JSON y devolver lista de ubicaciones

    return null;
  }
}
