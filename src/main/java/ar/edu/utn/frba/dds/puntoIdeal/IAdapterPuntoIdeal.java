package ar.edu.utn.frba.dds.puntoIdeal;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import java.util.List;

public interface IAdapterPuntoIdeal {
  List<Ubicacion> puntoIdeal(Double latitud, Double longitud, Double radio);
}
