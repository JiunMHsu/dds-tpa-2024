package ar.edu.utn.frba.dds.models.puntoIdeal;

import ar.edu.utn.frba.dds.models.data.Ubicacion;
import java.util.List;

public class PuntoIdeal {
    private  AdapterPuntoIdeal adapterPuntoIdeal;

    public PuntoIdeal(AdapterPuntoIdeal adapterPuntoIdeal) {
        this.adapterPuntoIdeal = adapterPuntoIdeal;
    }

    public List<Ubicacion> puntosIdeales(Ubicacion ubicacion, Integer radio)  {
        return this.adapterPuntoIdeal.puntoIdeal(ubicacion.getLatitud(), ubicacion.getLongitud(), radio);
    }
}
