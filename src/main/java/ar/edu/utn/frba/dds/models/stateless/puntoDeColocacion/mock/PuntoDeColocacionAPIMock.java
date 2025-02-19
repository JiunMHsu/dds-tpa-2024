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
        // Hospital Italiano, Juan D Perón 4190
        new Ubicacion(-34.60617, -58.42564),

        // McDonald's, Puerto Madero
        // Av. Alicia Moreau de Justo 1190
        new Ubicacion(-34.61120, -58.36592),

        // Alto Palermo Shopping
        // Av. Santa Fe 3253
        new Ubicacion(-34.58815, -58.41081)
    );
  }
}
