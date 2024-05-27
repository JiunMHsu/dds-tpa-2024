package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ColaboradoresPrevios {

    private Documento documento;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDateTime fechaDeColaboracion;
    private TipoColaboracion formaDeColaboracion;
    private Integer cantidad;

    private Boolean resgistrado;

    public boolean estaRegistrado() {
        return resgistrado;
    }

}
