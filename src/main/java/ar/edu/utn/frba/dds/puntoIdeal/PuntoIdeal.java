package ar.edu.utn.frba.dds.puntoIdeal;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;

import java.util.List;

public class PuntoIdeal {
  private final IAdapterPuntoIdeal adapterPuntoIdeal;

  public PuntoIdeal(IAdapterPuntoIdeal adapterPuntoIdeal) {
    this.adapterPuntoIdeal = adapterPuntoIdeal;
  }

  public List<Ubicacion> puntosIdeales(Ubicacion ubicacion, Double radio) {
    return this.adapterPuntoIdeal.puntoIdeal(ubicacion.getLatitud(), ubicacion.getLongitud(), radio);
  }
}
