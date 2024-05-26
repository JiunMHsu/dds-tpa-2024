package ar.edu.utn.frba.dds.models.puntoIdeal;

import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.Heladera;

import java.util.List;

public class PuntoIdeal {
    private  AdapterPuntoIdeal adapterPuntoIdeal;
    public List<Ubicacion> puntoIdeal(Ubicacion ubicacion, Integer radio)  {
        return adapterPuntoIdeal.puntoIdeal(ubicacion.getLatitud(), ubicacion.getLongitud(), radio);
    }
}
