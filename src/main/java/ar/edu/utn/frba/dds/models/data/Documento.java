package ar.edu.utn.frba.dds.models.data;

import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Documento {

    private Integer numero;

    /**
     * 
     */
    private TipoDocumento tipo;

    public Documento() {
    }

}