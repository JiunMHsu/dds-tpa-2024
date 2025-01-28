package ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import java.util.List;

/**
 * Adapter de API de punto de colocación.
 */
public interface IPuntoDeColocacionAdapter {
  List<Ubicacion> solicitarRecomendacion(double latitud, double longitud, double radio);
}
