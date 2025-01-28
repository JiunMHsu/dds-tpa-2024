package ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion.mock;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion.IPuntoDeColocacionAdapter;
import java.util.List;

/**
 * Mock de API de punto de colocación.
 */
public class PuntoDeColocacionAPIMock implements IPuntoDeColocacionAdapter {

  @Override
  public List<Ubicacion> solicitarRecomendacion(double latitud, double longitud, double radio) {
    return List.of(
        new Ubicacion(-30.12873, 35.08979),
        new Ubicacion(-34.98288, 37.12931),
        new Ubicacion(-29.92662, 31.23442)
    );
  }
}
