package ar.edu.utn.frba.dds.models.usuario;

import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;

import java.util.List;
import lombok.Getter;
@Getter

public class TipoDePersona {

    private Sujeto tipo;

    private List<TipoColaboracion> colaboracionesPermitidas;

    public TipoDePersona(Sujeto tipo, List<TipoColaboracion> colaboracionesPermitidas){
        this.tipo = tipo;
        this.colaboracionesPermitidas = colaboracionesPermitidas;
    }
}
