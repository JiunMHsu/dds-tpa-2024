package ar.edu.utn.frba.dds.models.usuario;

import java.util.*;

/**
 * 
 */
public class PersonaJuridica {

    /**
     * Default constructor
     */
    public PersonaJuridica() {
    }

    /**
     * 
     */
    private String razonSocial;

    /**
     * 
     */
    private TipoRazonSocial tipoRazonSocial;

    /**
     * 
     */
    private String rubro;

    /**
     * 
     */
    private Contacto contacto;

    /**
     * 
     */
    private Direccion direccion;

    /**
     * 
     */
    private List<TipoColaboracion> formaDeColaborar;

    /**
     * 
     */
    private FormularioRespondido datosAdicionales;

    /**
     * 
     */
    private Usuario usuario;

}