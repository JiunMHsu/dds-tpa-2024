package ar.edu.utn.frba.dds.services.puntoIdeal;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import java.util.ArrayList;
import java.util.List;

// TODO - es tipo el punto donación (es una api)
public class PuntoIdealService {

  public List<Ubicacion> obtenerPuntosIdeales(Double latitud, Double longitud, Integer radio) {

    System.out.println(latitud);
    System.out.println(longitud);
    System.out.println(radio);

    List<Ubicacion> puntosIdeales = new ArrayList<>();

    puntosIdeales.add(new Ubicacion(-30.12873, 35.08979));
    puntosIdeales.add(new Ubicacion(-34.98288, 37.12931));
    puntosIdeales.add(new Ubicacion(-29.92662, 31.23442));
    return puntosIdeales;
  }
}
