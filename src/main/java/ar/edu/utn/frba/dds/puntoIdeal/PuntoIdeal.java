package ar.edu.utn.frba.dds.puntoIdeal;

import ar.edu.utn.frba.dds.models.data.Ubicacion;
import java.util.List;

public class PuntoIdeal {
    private IAdapterPuntoIdeal adapterPuntoIdeal;

    public PuntoIdeal(IAdapterPuntoIdeal adapterPuntoIdeal) {
        this.adapterPuntoIdeal = adapterPuntoIdeal;
    }

    public List<Ubicacion> puntosIdeales(Ubicacion ubicacion, Double radio) {
        return this.adapterPuntoIdeal.puntoIdeal(ubicacion.getLatitud(), ubicacion.getLongitud(), radio);
    }
}
