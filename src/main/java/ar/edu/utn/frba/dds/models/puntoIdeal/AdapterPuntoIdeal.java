package ar.edu.utn.frba.dds.models.puntoIdeal;

import ar.edu.utn.frba.dds.models.data.Ubicacion;
import java.util.List;

public interface AdapterPuntoIdeal {
  List<Ubicacion> puntoIdeal(Integer latitud, Integer longitud, Integer radio);
}
